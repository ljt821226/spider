package com.jszx.cricket.platform.exception;

/**
 * 
 * 实体处理异常类:提供实体操作异常记录功能
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年9月8日 下午1:18:12
 * 
 */

public class EntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityException() {
		super();
	}

	public EntityException(String message) {
		super(message);
	}

	public EntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityException(Throwable cause) {
		super(cause);
	}

}
