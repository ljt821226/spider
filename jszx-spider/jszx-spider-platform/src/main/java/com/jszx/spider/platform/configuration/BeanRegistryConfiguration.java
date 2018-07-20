package com.jszx.spider.platform.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;

import com.jszx.spider.platform.code.SystemCode;

/**
 * 
 * bean注册类:自定义bean注册
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午4:05:51
 *
 */
// @Configuration
public class BeanRegistryConfiguration implements BeanDefinitionRegistryPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(BeanRegistryConfiguration.class);

	// bean 的名称生成器.

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.info("MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory()");

	}

	/**
	 * 先执行：postProcessBeanDefinitionRegistry()方法，
	 * 在执行：postProcessBeanFactory()方法。
	 *
	 */
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		logger.info("MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry()");
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		scanner.scan(new String[] { SystemCode.COMMON.PACKAGE.value() });
		// for (String basePackage : ApplicationContainer.getBasePackages()) {
		// scanner.scan(basePackage);
		// }

	}

	@Bean
	public BeanNameGenerator beanNameGenerator() {
		return new AnnotationBeanNameGenerator() {
			@Override
			public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
				// 全限定类名
				String beanName = definition.getBeanClassName();
				return beanName;
			}
		};
	}

}
