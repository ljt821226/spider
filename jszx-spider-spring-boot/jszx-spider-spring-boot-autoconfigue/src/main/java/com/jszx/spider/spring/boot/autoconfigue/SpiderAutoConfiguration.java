package com.jszx.spider.spring.boot.autoconfigue;

import javax.annotation.PostConstruct;

import org.apache.cxf.Bus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;

import com.jszx.spider.platform.properties.MybatisProperties;

@Configuration
public class SpiderAutoConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(SpiderAutoConfiguration.class);

	public SpiderAutoConfiguration() {
	}

	@PostConstruct
	public void checkConfigFileExists() {

	}

}