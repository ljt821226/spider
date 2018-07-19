package com.jszx.cricket.platform.exception;

/**
 * 平台异常类:提供平台异常信息封装
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月14日 下午2:35:59
 * 
 */

public class PlatformException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlatformException(String message) {
		super(message);
	}

	public PlatformException(String message, Throwable cause) {
		super(message, cause);
	}

}
