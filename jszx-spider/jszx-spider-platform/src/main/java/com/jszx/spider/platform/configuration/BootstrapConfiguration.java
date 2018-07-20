package com.jszx.spider.platform.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import com.jszx.spider.platform.properties.HttpProperties;
import com.jszx.spider.platform.properties.MybatisProperties;
import com.jszx.spider.platform.properties.OpenapiProperties;
import com.jszx.spider.platform.properties.SwitcherProperties;

/**
 * 平台配置类 :提供公共功能的配置
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月5日 下午11:52:52
 * 
 */
//@Configuration
//@EnableConfigurationProperties(value={HttpProperties.class,MybatisProperties.class,OpenapiProperties.class,SwitcherProperties.class})
//@Import({ MybatisProperties.class })
//@AutoConfigureOrder(1)
public class BootstrapConfiguration implements EnvironmentAware {

	private final Logger logger = LoggerFactory.getLogger(BootstrapConfiguration.class);
	
	@Autowired
	private  MybatisProperties mybatisProperties;


	public void setEnvironment(Environment env) {
		// propertyResolver = new RelaxedPropertyResolver(env,
		// "spring.datasource.");
		System.out.println(mybatisProperties.getDao());
	}

}
