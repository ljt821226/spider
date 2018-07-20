package com.jszx.spider.platform.bootstrap.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ResourceLoader;

import com.jszx.spider.platform.bootstrap.listener.LifecycleListener;

/**
 * 应用构建类:构建平台基本信息
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年4月26日 上午11:22:43
 * 
 */
public class ApplicationBuilder {

	private ApplicationBuilder() {
	}

	public static SpringApplication build(ResourceLoader resourceLoader, Class<?>... primarySources) {
		SpringApplication sa = new SpringApplication(resourceLoader, primarySources);
		sa.addListeners(new LifecycleListener());
		return sa;
	}

	public static SpringApplication build(Class<?>... primarySources) {
		SpringApplication sa = new SpringApplication(primarySources);
		sa.addListeners(new LifecycleListener());
		return sa;
	}

}
