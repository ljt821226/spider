package com.jszx.spider.platform.exception;

import com.jszx.spider.platform.code.ReturnCode;
import com.jszx.spider.platform.module.entity.ErrorEntity;

/**
 * 
 * 服务异常类:提供异常记录功能
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午4:58:18
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	// 状态
	private int code = ReturnCode.CODE.FAILURE.value();

	private ErrorEntity errorEntity = null;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ServiceException(ErrorEntity errorEntity) {
		super(errorEntity.getMessage());
		this.errorEntity = errorEntity;
		if (errorEntity != null) {
			this.code = errorEntity.getCode();
		}
	}

	public ServiceException(ErrorEntity errorEntity, Throwable cause) {
		super(errorEntity.getMessage(), cause);
		this.errorEntity = errorEntity;
		if (errorEntity != null) {
			this.code = errorEntity.getCode();
		}
	}

	/**
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 
	 * 获取日志实体
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2018年1月3日 上午11:45:18
	 */
	public ErrorEntity getErrorEntity() {
		return this.errorEntity;
	}

}
