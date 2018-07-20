package com.jszx.spider.platform.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jszx.platform.mybatis")
public class MybatisProperties {

	private String[] dao;

	private String[] mapper;

	private String[] entity;

	private boolean cache = false;

	public String[] getDao() {
		return dao;
	}

	public void setDao(String[] dao) {
		this.dao = dao;
	}

	public String[] getMapper() {
		return mapper;
	}

	public void setMapper(String[] mapper) {
		this.mapper = mapper;
	}

	public String[] getEntity() {
		return entity;
	}

	public void setEntity(String[] entity) {
		this.entity = entity;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

}
