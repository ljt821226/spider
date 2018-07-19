package com.jszx.cricket.platform.module.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 
 * 生命周期实体:获取声明周期相关属性
 * 
 * @version   1.0    
 * @author   2724216806@qq.com
 * @date 2018年3月16日 下午5:11:11
 *
 */

public class LifecycleEntity {

	// 开始启动，第一
	public static final int APPLICATION_START = 1;

	// 准备环境，第二
	public static final int ENVIRONMENT_PREPARED = 2;

	// 应用准备，第三
	public static final int APPLICATION_PREPARED = 3;

	// 容器准备，第四
	public static final int CONTEXT_REFRESHED = 4;

	// 应用启动，第五
	public static final int APPLICATION_READY = 5;

	// 容器关闭，第六
	public static final int CONTEXT_CLOSED = 6;

	public LifecycleEntity() {
	}

	private int type;

	private SpringApplication springApplication;

	private ConfigurableEnvironment configurableEnvironment;

	private ConfigurableApplicationContext configurableApplicationContext;

	private ApplicationContext applicationContext;

	private String[] args;

	/**
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            要设置的 type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return springApplication
	 */
	public SpringApplication getSpringApplication() {
		return springApplication;
	}

	/**
	 * @param springApplication
	 *            要设置的 springApplication
	 */
	public void setSpringApplication(SpringApplication springApplication) {
		this.springApplication = springApplication;
	}

	/**
	 * @return configurableEnvironment
	 */
	public ConfigurableEnvironment getConfigurableEnvironment() {
		return configurableEnvironment;
	}

	/**
	 * @param configurableEnvironment
	 *            要设置的 configurableEnvironment
	 */
	public void setConfigurableEnvironment(ConfigurableEnvironment configurableEnvironment) {
		this.configurableEnvironment = configurableEnvironment;
	}

	/**
	 * @return configurableApplicationContext
	 */
	public ConfigurableApplicationContext getConfigurableApplicationContext() {
		return configurableApplicationContext;
	}

	/**
	 * @param configurableApplicationContext
	 *            要设置的 configurableApplicationContext
	 */
	public void setConfigurableApplicationContext(ConfigurableApplicationContext configurableApplicationContext) {
		this.configurableApplicationContext = configurableApplicationContext;
	}

	/**
	 * @return applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext
	 *            要设置的 applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @return args
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @param args
	 *            要设置的 args
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}

}
