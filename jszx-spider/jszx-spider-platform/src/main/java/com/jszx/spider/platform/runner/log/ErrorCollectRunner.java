package com.jszx.spider.platform.runner.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jszx.spider.platform.exception.EntityException;
import com.jszx.spider.platform.exception.ServiceException;
import com.jszx.spider.platform.module.entity.ErrorEntity;
import com.jszx.spider.platform.runner.BaseRunner;
import com.jszx.spider.platform.tool.HttpTool;
import com.jszx.spider.platform.tool.JsonTool;
import com.jszx.spider.platform.tool.SpringTool;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年12月29日 下午2:27:34
 * 
 */

public class ErrorCollectRunner extends BaseRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private JoinPoint joinPoint;

	private ServiceException se;

	private HttpServletRequest request;

	public ErrorCollectRunner(JoinPoint joinPoint, ServiceException se, HttpServletRequest request) {
		super(false);
		this.joinPoint = joinPoint;
		this.se = se;
		this.request = request;
	}

	@Override
	public void handle() {
		try {
			if (se.getErrorEntity() == null) {
				return;
			}
			String logService = SpringTool.getProperty("kasaya.log.service", "null");
			String param = SpringTool.getProperty("kasaya.log.param", "condition");
			HttpTool.HttpParam httpParam = HttpTool.createHttpParam();
			httpParam.insert(param, buildLog());
			String result = HttpTool.doPost(logService, httpParam.toString(), 3000, 3000);
			Map<String, Object> map = JsonTool.toMap(result);
			int code = (int) map.get("code");
			if (code == 0) {
				logger.info("日志收集成功!!!");
			} else {
				logger.error("日志收集失败，错误为：" + map.get("message"));
			}
		} catch (IOException | EntityException e) {
			logger.error("日志收集失败，请检查...", e);
		}
	}

	private String buildLog() throws EntityException {
		ErrorEntity errorEntity = se.getErrorEntity();
		errorEntity.setService(request.getRequestURI());
		errorEntity.setMethod(joinPoint.getSignature().toString());
		errorEntity.setCreateTime(parseTime(System.currentTimeMillis()));
		return errorEntity.toJson();
	}

	/**
	 * 
	 * 解析日期格式
	 * 
	 * @param time
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月18日 上午10:59:38
	 */
	private String parseTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		date.setTime(time);
		return sdf.format(date);
	}

}
