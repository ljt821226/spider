package com.jszx.cricket.platform.module.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.TransactionStatus;

/**
 * 
 * 事务实体类
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:04:07
 *
 */

public class TransactionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String transactionId;

	private String parentId;

	private TransactionStatus status;

	private List<TransactionRequestEntity> children = new ArrayList<TransactionRequestEntity>();

	/**
	 * @return transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            要设置的 transactionId
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            要设置的 parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return children
	 */
	public List<TransactionRequestEntity> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            要设置的 children
	 */
	public void setChildren(List<TransactionRequestEntity> children) {
		this.children = children;
	}

	/**
	 * @return status
	 */
	public TransactionStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            要设置的 status
	 */
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

}
