package com.jszx.spider.platform.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jszx.spider.platform.code.ReturnCode;
import com.jszx.spider.platform.module.entity.ResponseEntity;

/**
 * 
 * 异常处理类:处理公共异常
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午3:57:31
 *
 */

// @ControllerAdvice
// @RestController
public class GlobalExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error("请求失败，请检查...", e);
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setCode(ReturnCode.CODE.FAILURE.value());
		responseEntity.setMessage(ReturnCode.MESSAGE.FAILURE.value());
		responseEntity.setData(e.getMessage());
		return responseEntity.toJson();
	}

}
