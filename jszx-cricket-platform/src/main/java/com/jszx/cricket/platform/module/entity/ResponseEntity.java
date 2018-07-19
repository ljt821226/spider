package com.jszx.cricket.platform.module.entity;

/**
 * 
 * 响应实体类
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:05:12
 *
 */

public class ResponseEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;

	private String message;

	private Object data;

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
	 * @return data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            要设置的 data
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
