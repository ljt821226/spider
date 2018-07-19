package com.jszx.cricket.platform.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jszx.cricket.switcher")
public class SwitcherProperties {

	private boolean swagger = true;

	private boolean database = true;

	private boolean mybatisCache = false;

	private boolean register = false;

	public boolean isSwagger() {
		return swagger;
	}

	public void setSwagger(boolean swagger) {
		this.swagger = swagger;
	}

	public boolean isDatabase() {
		return database;
	}

	public void setDatabase(boolean database) {
		this.database = database;
	}

	public boolean isMybatisCache() {
		return mybatisCache;
	}

	public void setMybatisCache(boolean mybatisCache) {
		this.mybatisCache = mybatisCache;
	}

	public boolean isRegister() {
		return register;
	}

	public void setRegister(boolean register) {
		this.register = register;
	}

}
