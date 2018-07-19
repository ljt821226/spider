package com.jszx.cricket.platform.tool;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jszx.cricket.platform.code.ReturnCode;
import com.jszx.cricket.platform.code.SystemCode;
import com.jszx.cricket.platform.code.TransactionCode;
import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.module.entity.ResponseEntity;
import com.jszx.cricket.platform.module.entity.TransactionEntity;
import com.jszx.cricket.platform.module.entity.TransactionRequestEntity;

/**
 * 事务工具类:提供事务处理方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年9月25日 下午11:06:22
 * 
 */

public class TransactionTool {

	private static final Logger logger = LoggerFactory.getLogger(TransactionTool.class);

	private static Map<String, TransactionEntity> transactionMap = new HashMap<String, TransactionEntity>();

	private static PlatformTransactionManager transactionManager = null;

	public static PlatformTransactionManager getPlatformTransactionManager() {
		synchronized (transactionManager) {
			if (transactionManager == null) {
				return (PlatformTransactionManager) SpringTool.getBean(PlatformTransactionManager.class);
			} else {
				return transactionManager;
			}
		}
	}

	public static void put(TransactionRequestEntity requestEntity) {
		requestEntity.setStartTime(DateTool.getCurrentTimestamp());
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		if (requestEntity.getTimeout() > 0) {
			dtd.setTimeout(requestEntity.getTimeout());
		}
		dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		String dbLaunch = SpringTool.getProperty("jszx.cosmos.switcher.db", "false");
		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setTransactionId(requestEntity.getTransactionId());
		if (!"false".equals(dbLaunch)) {
			TransactionStatus status = TransactionTool.getPlatformTransactionManager().getTransaction(dtd);
			transactionEntity.setStatus(status);
		}
		transactionMap.put(requestEntity.getTransactionId(), transactionEntity);
		logger.info("当前事务数为：【" + transactionMap.size() + "】");
	}

	public static TransactionEntity get(String transactionId) {
		return transactionMap.get(transactionId);
	}

	public static void del(String transactionId) {
		transactionMap.remove(transactionId);
	}

	/**
	 * 
	 * 请求
	 * 
	 * @param requestEntity
	 * @param exceptionFlag
	 * @param headers
	 * @return
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月30日 上午11:05:53
	 */
	public static ResponseEntity request(TransactionRequestEntity requestEntity, boolean exceptionFlag)
			throws ServiceException {
		try {
			requestEntity.setResult(TransactionCode.STATUS.UNEXECUTED.value());
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			String transactionId = attributes.getRequest().getAttribute(TransactionCode.MARK.TOKEN.value()).toString();
			requestEntity.setTransactionId(transactionId + "-" + KeyTool.generate());
			String json = HttpTool.doPost(requestEntity.getDomain() + TransactionCode.ACTION.PREPARE.value(),
					TransactionCode.MARK.CONDITION.value() + "="
							+ URLEncoder.encode(requestEntity.toJson(), SystemCode.COMMON.ENCODING.value()),
					requestEntity.getHeader());
			if (json.startsWith("<html>")) {
				throw new ServiceException(ReturnCode.CODE.FAILURE.value(), "服务地址错误，请检查");
			}
			requestEntity.setResult(TransactionCode.STATUS.EXECUTED.value());
			if (transactionMap.containsKey(transactionId)) {
				transactionMap.get(transactionId).getChildren().add(requestEntity);
			} else {
				TransactionEntity transactionEntity = new TransactionEntity();
				transactionEntity.setTransactionId(transactionId);
				transactionEntity.getChildren().add(requestEntity);
				transactionMap.put(transactionId, transactionEntity);
			}
			ResponseEntity responseEntity = (ResponseEntity) JsonTool.toEntity(json, ResponseEntity.class);
			requestEntity.setLocalhost(responseEntity
					.getOrDefault(TransactionCode.MARK.LOCALHOST.name(), requestEntity.getDomain()).toString());
			if (exceptionFlag) {
				if (responseEntity.getCode() != 0) {
					requestEntity.setResult(TransactionCode.STATUS.FAILURE.value());
					throw new ServiceException(responseEntity.getCode(), responseEntity.getMessage());
				} else {
					requestEntity.setResult(TransactionCode.STATUS.SUCCESS.value());
				}
			}
			return responseEntity;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(ReturnCode.CODE.FAILURE.value(), ReturnCode.MESSAGE.FAILURE.value(), e);
		}
	}

	/**
	 * 
	 * 提交
	 * 
	 * @param transactionId
	 * @param headers
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月30日 上午11:05:41
	 */
	public static void submit(String transactionId) throws Exception {
		log(null, "{提交}开始");
		TransactionEntity transactionEntity = transactionMap.remove(transactionId);
		List<TransactionRequestEntity> children = transactionEntity.getChildren();

		// 提交被调事务
		for (TransactionRequestEntity requestEntity : children) {
			log(requestEntity.getTransactionId(), "{提交}开始");
			String msg = HttpTool.doPost(requestEntity.getLocalhost() + TransactionCode.ACTION.SUBMIT.value(),
					TransactionCode.MARK.CONDITION.value() + "=" + URLEncoder.encode(requestEntity.toJson(), "UTF-8"),
					requestEntity.getHeader());
			ResponseEntity responseEntity = (ResponseEntity) JsonTool.toEntity(msg, ResponseEntity.class);
			if (responseEntity.getCode() != 0) {
				log(requestEntity.getTransactionId(), "{提交}异常");
				throw new ServiceException(responseEntity.getCode(), responseEntity.getMessage());
			}
			log(requestEntity.getTransactionId(), "{提交}完成");
		}

		// 提交主调事务
		if (transactionEntity.getStatus() != null && !transactionEntity.getStatus().isCompleted()) {
			log(transactionEntity.getTransactionId(), "{提交}开始");
			getPlatformTransactionManager().commit(transactionEntity.getStatus());
			log(transactionEntity.getTransactionId(), "{提交}完成");
		}
		log(null, "{提交}完成");
		log(null, "当前事务数为{" + transactionMap.size() + "}");
	}

	/**
	 * 
	 * 回滚
	 * 
	 * @param transactionId
	 * @param headers
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年9月30日 上午11:05:26
	 */
	public static void rollback(String transactionId) throws Exception {
		log(null, "{回滚}开始");
		TransactionEntity transactionEntity = transactionMap.remove(transactionId);
		List<TransactionRequestEntity> children = transactionEntity.getChildren();

		// 回滚主调事务
		if (transactionEntity.getStatus() != null && !transactionEntity.getStatus().isCompleted()) {
			log(transactionEntity.getTransactionId(), "{回滚}开始");
			getPlatformTransactionManager().rollback(transactionEntity.getStatus());
			log(transactionEntity.getTransactionId(), "{回滚}完成");
		}

		// 回滚被调事务
		for (TransactionRequestEntity requestEntity : children) {
			if (TransactionCode.STATUS.UNEXECUTED.value().equals(requestEntity.getResult())) {
				continue;
			}
			log(requestEntity.getTransactionId(), "{回滚}开始");
			String msg = HttpTool.doPost(requestEntity.getLocalhost() + TransactionCode.ACTION.ROLLBACK.value(),
					TransactionCode.MARK.CONDITION.value() + "=" + URLEncoder.encode(requestEntity.toJson(), "UTF-8"),
					requestEntity.getHeader());
			ResponseEntity responseEntity = (ResponseEntity) JsonTool.toEntity(msg, ResponseEntity.class);
			if (responseEntity.getCode() != 0) {
				log(requestEntity.getTransactionId(), "{回滚}失败");
			}
			log(requestEntity.getTransactionId(), "{回滚}完成");
		}
		log(null, "{回滚}完成");
		log(null, "当前事务数为{" + transactionMap.size() + "}");
	}

	private static void log(String transactionId, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("【事务】：");
		if (!StringTool.isEmpty(transactionId)) {
			sb.append("{");
			sb.append(transactionId);
			sb.append("}");
		}
		sb.append(message);
		logger.info(sb.toString());
	}

}
