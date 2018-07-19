package com.jszx.cricket.platform.module.entity;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年4月19日 上午11:25:06
 * 
 */

public class OperationEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String method;

	private boolean hidden;

	private String summary;

	private String description;

	private String[] tags;

	private ParameterEntity parameterEntity;

	public OperationEntity() {
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

	/**
	 * @return summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            要设置的 summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
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
	 * @return tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            要设置的 tags
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * @return parameterEntity
	 */
	public ParameterEntity getParameterEntity() {
		return parameterEntity;
	}

	/**
	 * @param parameterEntity
	 *            要设置的 parameterEntity
	 */
	public void setParameterEntity(ParameterEntity parameterEntity) {
		this.parameterEntity = parameterEntity;
	}

}
