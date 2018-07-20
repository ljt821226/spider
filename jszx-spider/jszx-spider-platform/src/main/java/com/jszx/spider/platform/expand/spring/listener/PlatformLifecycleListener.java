package com.jszx.spider.platform.expand.spring.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.jszx.spider.platform.code.SystemCode;
import com.jszx.spider.platform.expand.mybatis.configurer.PlatformMapperScannerConfigurer;
import com.jszx.spider.platform.expand.spring.generator.PlatformBeanNameGenerator;
import com.jszx.spider.platform.tool.SpringTool;

/**
 * 应用生命周期监听类:在不同周期增加业务处理 s
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月8日 上午10:17:44
 * 
 */

public class PlatformLifecycleListener implements ApplicationListener<ApplicationEvent> {

	private static final Logger logger = LoggerFactory.getLogger(PlatformLifecycleListener.class);

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
			buildScanner(evt.getApplicationContext());
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
		String dbSwitcher = env.getProperty("jszx.platform.switcher.database", "true");
		if ("false".equals(dbSwitcher)) {
			System.setProperty("spring.datasource.url", env.getProperty("jszx.platform.db.url"));
			System.setProperty("spring.datasource.driver-class-name", env.getProperty(
						"jszx.cosmos.db.driver-class-name"));
			System.setProperty("spring.datasource.username", env.getProperty("jszx.platform.db.username"));
			System.setProperty("spring.datasource.password", env.getProperty("jszx.platform.db.password"));
			logger.info("[platform]:设置默认数据库...");
		}

		// 设置不启动gateway功能
		String gatewayLaunch = env.getProperty("jszx.platform.switcher.gateway", "false");
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

	/**
	 * 
	 * 构建扫描器
	 * 
	 * @param ac
	 * @author 2724216806@qq.com
	 * @date 2018年7月24日 下午3:52:16
	 */
	private void buildScanner(ApplicationContext ac) {
		BeanDefinitionRegistry registry = getBeanDefinitionRegistry(ac);
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		BeanDefinitionBuilder b = BeanDefinitionBuilder.rootBeanDefinition(PlatformMapperScannerConfigurer.class);
		registry.registerBeanDefinition(PlatformMapperScannerConfigurer.class.getName(), b.getBeanDefinition());
		scanner.setBeanNameGenerator(new PlatformBeanNameGenerator());
		scanner.scan(new String[] { SystemCode.COMMON.PACKAGE.value() });
	}

	/**
	 * 
	 * 获取Bean注册器
	 * 
	 * @param context
	 * @return
	 * @author 2724216806@qq.com
	 * @date 2018年7月24日 下午3:52:47
	 */
	private BeanDefinitionRegistry getBeanDefinitionRegistry(ApplicationContext context) {
		if ((context instanceof BeanDefinitionRegistry)) {
			return (BeanDefinitionRegistry) context;
		}
		if ((context instanceof AbstractApplicationContext)) {
			return (BeanDefinitionRegistry) ((AbstractApplicationContext) context).getBeanFactory();
		}
		throw new IllegalStateException("Could not locate BeanDefinitionRegistry");
	}

}
