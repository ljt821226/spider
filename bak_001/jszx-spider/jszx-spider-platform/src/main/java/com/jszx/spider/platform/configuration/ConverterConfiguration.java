package com.jszx.spider.platform.configuration;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;

/**
 * [程序名称]:[程序功能描述]
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年3月29日 下午5:48:50
 * 
 */
@Configuration
public class ConverterConfiguration {

	@Bean
	public FormHttpMessageConverter formHttpMessageConverter() {
		FormHttpMessageConverter mc = new FormHttpMessageConverter();
		mc.setCharset(Charset.forName("UTF-8"));
		mc.setMultipartCharset(Charset.forName("UTF-8"));
		return mc;
	}
	
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter mc = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return mc;
	}
	
	@Bean
	public ResourceHttpMessageConverter resourceHttpMessageConverter() {
		ResourceHttpMessageConverter mc = new ResourceHttpMessageConverter();
		mc.setDefaultCharset(Charset.forName("UTF-8"));
		return mc;
	}

}
