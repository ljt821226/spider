package com.jszx.spider.platform.module.entity;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 
 * 事务请求实体
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:02:55
 *
 */

public class TransactionRequestEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 事务标识
	private String transactionId;

	// 服务地址
	private String domain;

	// 服务名称
	private String service;

	// 服务对应的方法
	private String method;

	// 服务方法的参数
	private BaseEntity parameter = new BaseEntity();

	// 被调服务本机地址
	private String localhost;

	private String type;

	private Timestamp startTime;

	private Timestamp endTime;

	// http请求头
	private Map<String, String> header;

	// 单位为秒
	private int timeout = 0;

	private String result;

	public TransactionRequestEntity() {
	}

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
	 * @return service
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service
	 *            要设置的 service
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            要设置的 method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return parameter
	 */
	public BaseEntity getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            要设置的 parameter
	 */
	public void setParameter(BaseEntity parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return startTime
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            要设置的 startTime
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return endTime
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            要设置的 endTime
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return header
	 */
	public Map<String, String> getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            要设置的 header
	 */
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	/**
	 * @return timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            要设置的 timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            要设置的 domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return localhost
	 */
	public String getLocalhost() {
		return localhost;
	}

	/**
	 * @param localhost
	 *            要设置的 localhost
	 */
	public void setLocalhost(String localhost) {
		this.localhost = localhost;
	}

	/**
	 * @return result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            要设置的 result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 
	 * 动态添加参数
	 * 
	 * @param key
	 * @param value
	 * @author lv_juntao@uisftech.com
	 * @date 2017年10月9日 下午5:46:32
	 */
	public void addParameter(String key, Object value) {
		parameter.set(key, value);
	}

}
