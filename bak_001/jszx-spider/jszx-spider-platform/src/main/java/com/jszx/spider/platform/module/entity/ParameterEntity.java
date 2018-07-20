package com.jszx.spider.platform.module.entity;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月19日 上午11:27:57
 * 
 */

public class ParameterEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private boolean hidden;

	public ParameterEntity() {
	}

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
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            要设置的 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return hidden
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *            要设置的 hidden
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
