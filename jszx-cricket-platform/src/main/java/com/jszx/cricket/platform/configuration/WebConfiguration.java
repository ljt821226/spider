package com.jszx.cricket.platform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jszx.cricket.platform.code.HttpCode;

@Configuration
public class WebConfiguration {

	@Bean
	public WebMvcConfigurer webConfigurer() {
		return new WebMvcConfigurer() {

			public void addCorsMappings(CorsRegistry registry) {
				CorsRegistration cors = registry.addMapping("/**");
				cors.allowedOrigins(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_ORIGIN.value());
				cors.allowCredentials(Boolean.valueOf(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_CREDENTIALS.value()));
				cors.allowedHeaders(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_HEADERS.value());
				cors.allowedMethods(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_METHODS.value());
				cors.exposedHeaders(HttpCode.HEADER.ACCESS_CONTROL_EXPOSE_HEADERS.value());
				cors.maxAge(Long.valueOf(HttpCode.HEADER.ACCESS_CONTROL_MAX_AGE.value()));
			}
		};
	}

}
