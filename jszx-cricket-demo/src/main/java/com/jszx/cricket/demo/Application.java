package com.jszx.cricket.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.jszx.cricket.platform.properties.HttpProperties;
import com.jszx.cricket.platform.properties.MybatisProperties;
import com.jszx.cricket.platform.properties.OpenapiProperties;
import com.jszx.cricket.platform.properties.SwitcherProperties;

@SpringBootApplication
//@ComponentScan(basePackages={"com.jszx","com.jszx.cricket.platform"})
//@EnableConfigurationProperties(value={HttpProperties.class,MybatisProperties.class,OpenapiProperties.class,SwitcherProperties.class})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}