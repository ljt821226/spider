package com.jszx.spider.platform.module.entity;

/**
 * 
 * 错误实体类
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:12:31
 *
 */

public class ErrorEntity extends LogEntity {

	private static final long serialVersionUID = 1L;

	private int code;

	private String message;

	private String createTime;

	public ErrorEntity() {
	}

	/**
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            要设置的 code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            要设置的 message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            要设置的 createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
