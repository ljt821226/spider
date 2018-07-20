package com.jszx.spider.platform.module.entity;

/**
 * 
 * 数据库信息实体:记录数据库信息
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:12:55
 *
 */

public class DatabaseEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 名称
	private String name;

	// 版本
	private String version;

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            要设置的 version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
