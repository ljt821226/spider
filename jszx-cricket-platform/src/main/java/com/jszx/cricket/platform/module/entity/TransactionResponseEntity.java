package com.jszx.cricket.platform.module.entity;

/**
 * 
 * 事务响应实体
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:03:20
 *
 */

public class TransactionResponseEntity extends ResponseEntity {

	private static final long serialVersionUID = 1L;

	private String callbackAction;

	/**
	 * @return callbackAction
	 */
	public String getCallbackAction() {
		return callbackAction;
	}

	/**
	 * @param callbackAction
	 *            要设置的 callbackAction
	 */
	public void setCallbackAction(String callbackAction) {
		this.callbackAction = callbackAction;
	}

}
