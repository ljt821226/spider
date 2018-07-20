package com.jszx.spider.platform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * rest模板类:提供模板注册服务
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年2月28日 下午10:53:49
 * 
 */

@Configuration
public class RestTemplateConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
