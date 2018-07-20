package com.jszx.spider.platform.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jszx.cricket.mybatis")
public class MybatisProperties {

	private String[] dao;

	private String[] xml;

	private String[] entity;

	private boolean cache = false;

	public String[] getDao() {
		return dao;
	}

	public void setDao(String[] dao) {
		this.dao = dao;
	}

	public String[] getXml() {
		return xml;
	}

	public void setXml(String[] xml) {
		this.xml = xml;
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
