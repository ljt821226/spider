package com.jszx.spider.platform.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.openapi.OpenApiCustomizer;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.apache.cxf.validation.BeanValidationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jszx.spider.platform.code.HttpCode;
import com.jszx.spider.platform.expand.cxf.provider.ContainerRequestFilterProvider;
import com.jszx.spider.platform.expand.cxf.provider.ContainerResponseFilterProvider;
import com.jszx.spider.platform.expand.cxf.provider.ExceptionMapperProvider;
import com.jszx.spider.platform.expand.cxf.provider.MessageBodyReaderProvider;
import com.jszx.spider.platform.expand.cxf.provider.MessageBodyWriterProvider;
import com.jszx.spider.platform.expand.swagger.EntityExtension;
import com.jszx.spider.platform.properties.OpenapiProperties;

import io.swagger.v3.jaxrs2.ext.OpenAPIExtensions;

/**
 * 
 * CXF配置类:提供CXF的servlet配置、Restful基本配置
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午4:07:28
 *
 */
@Configuration
public class CxfConfiguration {

	private final String CXF_ADDRESS = "/";

	@Value("${server.servlet.context-path:null}")
	private String contextPath = null;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${spring.application.version:1.0}")
	private String applicationVersion;

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private Bus bus;

	@Autowired
	private OpenapiProperties openapiProperties;

	@Bean
	public Server cxfServer() {
		// 获取@Path注解的类
		List<Object> serviceBeans = new ArrayList<>();
		Map<String, Object> map = ctx.getBeansWithAnnotation(Path.class);
		serviceBeans.addAll(map.values());

		// 获取 @Providers注解的类
		List<Object> providers = new ArrayList<>();
		providers.add(new MessageBodyReaderProvider());
		providers.add(new MessageBodyWriterProvider<Object>());
		providers.add(new ExceptionMapperProvider());
		providers.add(new ContainerResponseFilterProvider());
		providers.add(new ContainerRequestFilterProvider());
		providers.add(new BeanValidationProvider());
		providers.add(new CrossOriginResourceSharingFilter());

		// providers.add(new ValidationExceptionMapper());
		providers.addAll(ctx.getBeansWithAnnotation(Provider.class).values());

		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setBus(bus);
		factory.setAddress(CXF_ADDRESS);
		factory.setServiceBeans(serviceBeans);
		factory.setProviders(providers);
		if (true) {
			factory.getFeatures().add(createOpenApiFeature());
		}
		factory.getFeatures().add(new LoggingFeature());
		factory.getInInterceptors().add(new JAXRSBeanValidationInInterceptor());
		factory.getOutInterceptors().add(new JAXRSBeanValidationOutInterceptor());
		return factory.create();
	}

	private OpenApiFeature createOpenApiFeature() {
		OpenAPIExtensions.getExtensions().add(0, new EntityExtension());
		OpenApiFeature openApiFeature = new OpenApiFeature();
		OpenApiCustomizer openApiCustomizer = new OpenApiCustomizer();
		openApiCustomizer.setDynamicBasePath(true);
		openApiFeature.setCustomizer(openApiCustomizer);
		openApiFeature.setPrettyPrint(true);
		openApiFeature.setTitle(openapiProperties.getTitle());
		openApiFeature.setContactName(openapiProperties.getContactName());
		openApiFeature.setDescription(openapiProperties.getDescription());
		openApiFeature.setVersion(openapiProperties.getVersion());
		openApiFeature.setLicense(openapiProperties.getLicense());
		openApiFeature.setLicenseUrl(openapiProperties.getLicenseUrl());
		return openApiFeature;
	}

	private CrossOriginResourceSharingFilter buildCors() {
		CrossOriginResourceSharingFilter cors = new CrossOriginResourceSharingFilter();
		cors.getAllowHeaders().addAll(Arrays.asList(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_HEADERS.value().split(",")));
		cors.getAllowOrigins().addAll(Arrays.asList(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_ORIGIN.value().split(",")));
		cors.getExposeHeaders().addAll(Arrays.asList(HttpCode.HEADER.ACCESS_CONTROL_EXPOSE_HEADERS.value().split(",")));
		cors.setMaxAge(Integer.valueOf(HttpCode.HEADER.ACCESS_CONTROL_MAX_AGE.value()));

		// cors.setAllowHeaders(Arrays.asList(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_HEADERS.value().split(",")));
		// cors.setAllowOrigins(Arrays.asList(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_ORIGIN.value().split(",")));
		// cors.setAllowCredentials(Boolean.valueOf(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_CREDENTIALS.value()));
		// cors.setDefaultOptionsMethodsHandlePreflight(true);
		// cors.allowedHeaders(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_HEADERS.value());
		// cors.allowedMethods(HttpCode.HEADER.ACCESS_CONTROL_ALLOW_METHODS.value());
		// cors.exposedHeaders(HttpCode.HEADER.ACCESS_CONTROL_EXPOSE_HEADERS.value());
		// cors.maxAge(Long.valueOf(HttpCode.HEADER.ACCESS_CONTROL_MAX_AGE.value()));
		return cors;
	}

}
