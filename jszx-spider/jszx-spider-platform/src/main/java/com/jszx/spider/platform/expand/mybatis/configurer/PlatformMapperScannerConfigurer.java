package com.jszx.spider.platform.expand.mybatis.configurer;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapperScannerConfigurer extends MapperScannerConfigurer{
	
	public PlatformMapperScannerConfigurer(){
		super();
		this.setBasePackage("com.jszx.**.dao");
	}

}
