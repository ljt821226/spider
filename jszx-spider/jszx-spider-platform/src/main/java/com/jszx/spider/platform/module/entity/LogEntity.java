package com.jszx.spider.platform.module.entity;

import com.jszx.spider.platform.tool.SpringTool;

/**
 * 
 * 日志实体类
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:10:35
 *
 */

public class LogEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String module;

	private String service;

	private String method;

	public LogEntity() {
		this.module = SpringTool.getProperty("spring.application.name");
	}

	/**
	 * @return module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module
	 *            要设置的 module
	 */
	public void setModule(String module) {
		this.module = module;
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

}
