package com.jszx.spider.platform.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jszx.spider.platform.code.TransactionCode;
import com.jszx.spider.platform.module.entity.TransactionRequestEntity;
import com.jszx.spider.platform.tool.KeyTool;
import com.jszx.spider.platform.tool.PlatformTool;
import com.jszx.spider.platform.tool.TransactionTool;

/**
 * 
 * 事务切面类:对事务进行拦截
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午3:59:23
 *
 */

@Aspect
@Component
public class TransactionAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String TOKEN=TransactionCode.MARK.TOKEN.value();
	
	private final String ROOT=TransactionCode.MARK.ROOT.value();

	/**
	 * 
	 * 拦截器配置
	 * 
	 * @author 2724216806@qq.com
	 * @date 2018年3月16日 下午3:59:53
	 */
	@Pointcut("execution(public * com..module.service.impl..*ServiceImpl.*(..)) && @annotation(com.jszx.spider.platform.annotation.TransactionLauncher)")
	public void transaction() {
	}

	@Around("transaction()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean rootFlag = false;
		String transactionId = "";
		HttpServletRequest request = PlatformTool.getRequest();
		if (request != null) {
			if (request.getAttribute(TOKEN) == null) {
				rootFlag = true;
				request.setAttribute(ROOT, "1");
				transactionId = KeyTool.generate(TOKEN);
				request.setAttribute(TOKEN, transactionId);
				TransactionRequestEntity requestEntity = new TransactionRequestEntity();
				requestEntity.setTransactionId(transactionId);
				TransactionTool.put(requestEntity);
			}
		}

		Object result = joinPoint.proceed();

		// 当事务根节点时候执行
		if (rootFlag) {
			TransactionTool.submit(transactionId);
		}
		return result;
	}

	/**
	 * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
	 * 
	 * 注意：执行顺序在Around Advice之后
	 * 
	 * @param joinPoint
	 * @param ex
	 */
	@AfterThrowing(
		value = "transaction()",
		throwing = "ex"
	)
	public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
		try {
			HttpServletRequest request = PlatformTool.getRequest();
			if (request != null) {
				if (request.getAttribute(ROOT) != null) {
					String transactionId = request.getAttribute(TOKEN).toString();
					TransactionTool.rollback(transactionId);
				}
			}
		} catch (Exception e) {
			logger.error("回滚失败，请检查...", e);
		}
	}

	/**
	 * 
	 * 获取参数类型
	 * 
	 * @param clazz
	 * @param methodName
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年3月16日 下午4:02:17
	 */
	public Class<?>[] getMethodParamTypes(Class<?> clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		Class<?>[] paramTypes = null;
		for (Method m : methods) {
			if (methodName.equals(m.getName())) {
				paramTypes = m.getParameterTypes();
				break;
			}
		}
		return paramTypes;
	}

}
