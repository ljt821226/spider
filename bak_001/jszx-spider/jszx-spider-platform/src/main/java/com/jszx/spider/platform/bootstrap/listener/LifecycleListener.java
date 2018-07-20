package com.jszx.spider.platform.bootstrap.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;

import com.jszx.spider.platform.expand.swagger.EntityExtension;
import com.jszx.spider.platform.module.entity.LifecycleEntity;
import com.jszx.spider.platform.tool.SpringTool;

import io.swagger.v3.jaxrs2.ext.OpenAPIExtensions;

/**
 * 应用生命周期监听类:在不同周期增加业务处理 s
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月8日 上午10:17:44
 * 
 */

public class LifecycleListener implements ApplicationListener<ApplicationEvent> {

	private static final Logger logger = LoggerFactory.getLogger(LifecycleListener.class);

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ApplicationStartingEvent) {// 启动
			ApplicationStartingEvent evt = (ApplicationStartingEvent) event;
			log("应用开始启动...");
		} else if (event instanceof ApplicationEnvironmentPreparedEvent) {// 初始化环境变量
			ApplicationEnvironmentPreparedEvent evt = (ApplicationEnvironmentPreparedEvent) event;
			applicationEnvironmentPreparedHandle(evt);
			log("应用环境准备完成...");
		} else if (event instanceof ApplicationPreparedEvent) {// 初始化完成
			ApplicationPreparedEvent evt = (ApplicationPreparedEvent) event;
			SpringTool.setApplicationContext(evt.getApplicationContext());
			log("应用准备完成...");
		} else if (event instanceof ContextRefreshedEvent) {// 应用刷新
			ContextRefreshedEvent evt = (ContextRefreshedEvent) event;
			log("容器刷新完成...");
		} else if (event instanceof ApplicationReadyEvent) {// 应用已启动完成
			ApplicationReadyEvent evt = (ApplicationReadyEvent) event;
			log("应用已启动...");
		} else if (event instanceof ContextClosedEvent) {// 应用关闭
			ContextClosedEvent evt = (ContextClosedEvent) event;
			log("容器已关闭...");
		} else {
			// logger.info("[platform]:容器和应用已停止...");
		}
	}

	private void applicationEnvironmentPreparedHandle(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment env = event.getEnvironment();
		String dbSwitcher = env.getProperty("jszx.cricket.switcher.database", "true");
		if ("false".equals(dbSwitcher)) {
			System.setProperty("spring.datasource.url", env.getProperty("jszx.cricket.db.url"));
			System.setProperty("spring.datasource.driver-class-name",
					env.getProperty("jszx.cosmos.db.driver-class-name"));
			System.setProperty("spring.datasource.username", env.getProperty("jszx.cricket.db.username"));
			System.setProperty("spring.datasource.password", env.getProperty("jszx.cricket.db.password"));
			logger.info("[platform]:设置默认数据库...");
		}

		// 设置不启动gateway功能
		String gatewayLaunch = env.getProperty("jszx.cricket.switcher.gateway", "false");
		if ("false".equals(gatewayLaunch)) {
			System.setProperty("eureka.client.register-with-eureka", "false");
			System.setProperty("eureka.client.fetch-registry", "false");
		}

	}

	/**
	 * 
	 * 日志
	 * 
	 * @param msg
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月9日 上午9:28:01
	 */
	private void log(String msg) {
		String logMsg = String.format("[platform]:%s", msg);
		if (logger.isInfoEnabled()) {
			logger.info(logMsg);
		} else {
			System.out.println(logMsg);
		}
	}

}
