package com.jszx.cricket.platform.configuration;

import java.util.Properties;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 
 * 事务配置类型:提供事务拦截过滤功能
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午4:34:36
 *
 */

@Configuration
public class TransactionConfiguration {

	// 是否执行事务拦截
	@Value("${kasaya.db.launch:true}")
	private boolean dbLaunch;

	@Autowired
	private TransactionInterceptor transactionInterceptor;

	@Bean
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		Properties prop = new Properties();
		prop.setProperty("*", "PROPAGATION_REQUIRED,-ServiceException");
		transactionInterceptor.setTransactionAttributes(prop);
		BeanNameAutoProxyCreator bean = new BeanNameAutoProxyCreator();
		bean.setProxyTargetClass(true);
		if (dbLaunch) {
			bean.setBeanNames("*Service", "*ServiceImpl");
		}
		bean.setInterceptorNames("transactionInterceptor");
		return bean;
	}

}
