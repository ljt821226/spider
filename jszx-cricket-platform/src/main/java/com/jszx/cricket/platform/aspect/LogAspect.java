package com.jszx.cricket.platform.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jszx.cricket.platform.exception.ServiceException;
import com.jszx.cricket.platform.exception.EntityException;
import com.jszx.cricket.platform.module.entity.SystemEntity;
import com.jszx.cricket.platform.runner.executor.RunnerExecutor;
import com.jszx.cricket.platform.runner.log.ErrorCollectRunner;
import com.jszx.cricket.platform.tool.NetworkTool;
import com.jszx.cricket.platform.tool.PlatformTool;
import com.jszx.cricket.platform.tool.SystemTool;

/**
 * 
 * 日志拦截器类:记录Service执行日志、时长
 * 
 * @version   1.0    
 * @author   2724216806@qq.com
 * @date 2018年3月16日 下午3:58:58
 *
 */

@Aspect
@Component
@Order
public class LogAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${jszx.log.launch:false}")
	private boolean logLaunch;

	@Value("${jszx.log.service:null}")
	private String logService;

	/**
	 * 
	 * 拦截器配置
	 * 
	 * @author lv_juntao@uisftech.com
	 * @date 2016年9月30日 下午3:49:19
	 */
	@Pointcut("execution(public * com..module.service.impl..*ServiceImpl.*(..))")
	public void log() {
	}

	@Around("log()")
	public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = jp.proceed();
		long endTime = System.currentTimeMillis();
		logger.info("@request@:" + buildLog(jp, startTime, endTime));
		return result;
	}

	@AfterThrowing(value = "log()", throwing = "ex")
	public void afterThrowingAdvice(JoinPoint joinPoint, ServiceException ex) {
		try {
			if (logLaunch) {
				if ("null".equals(logService)) {
					logger.error("属性{kasaya.log.service}未配置，请检查...");
				} else {
					HttpServletRequest request = PlatformTool.getRequest();
					if (request != null) {
						RunnerExecutor.execute(
								new ErrorCollectRunner(joinPoint, PlatformTool.getServiceException(ex), request));
					}
				}
			}
		} catch (Exception e) {
			logger.error("日志记录异常，请检查...", e);
		}
	}

	/**
	 * 
	 * 构建日志信息
	 * 
	 * @param jp
	 * @param time
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @throws EntityException
	 * @date 2017年8月18日 上午10:14:47
	 */
	private String buildLog(ProceedingJoinPoint jp, long startTime, long endTime) throws EntityException {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		SystemEntity systemEntity = new SystemEntity();
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			systemEntity.setService(request.getRequestURI());
			systemEntity.setClientHost(NetworkTool.getClientIp(request));
		} else {
			systemEntity.setService(jp.getSignature().getName());
			systemEntity.setClientHost("localhost");
		}
		systemEntity.setMethod(jp.getSignature().toString());
		systemEntity.setTotalTime((endTime - startTime));
		systemEntity.setStartTime(parseTime(startTime));
		systemEntity.setEndTime(parseTime(endTime));
		systemEntity.setTotalMemory(SystemTool.getJvmTotalMemory());
		systemEntity.setFreeMemory(SystemTool.getJvmFreeMemory());
		systemEntity.setMaxMemory(SystemTool.getJvmMaxMemory());
		return systemEntity.toJson();
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
