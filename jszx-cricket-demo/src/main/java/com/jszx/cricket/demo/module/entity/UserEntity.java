package com.jszx.cricket.demo.module.entity;

import javax.validation.constraints.NotBlank;

import com.jszx.cricket.platform.module.entity.BaseEntity;

public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "用户名不能为空")
	private String id;
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
