package com.jszx.cricket.platform.configuration;

import java.nio.charset.Charset;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * tomcat参数配置:设置tomcat参数功能
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午4:12:25
 *
 */

@Configuration
//@AutoConfigureBefore(name = {
//        "org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration", // Spring Boot 1.x
//        "org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration" // Spring Boot 2.x
//})
public class TomcatConfiguration {

	@Value("${server.ssl.enabled:false}")
	private boolean sslEnabled;

	@Value("${server.port:8443}")
	private int httpsPort;

	@Value("${kasaya.http.port:80}")
	private int httpPort;

	@Value("${kasaya.http.launch:false}")
	private boolean httpLaunch;

	@Value("${kasaya.http.switch:false}")
	private boolean httpSwitch;

	@Bean
	public ServletWebServerFactory createEmbeddedServletContainerFactory() {
		TomcatServletWebServerFactory tomcatFactory = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				if (sslEnabled) {
					SecurityConstraint constraint = new SecurityConstraint();
					constraint.setUserConstraint("CONFIDENTIAL");
					SecurityCollection collection = new SecurityCollection();
					collection.addPattern("/*");
					constraint.addCollection(collection);
					context.addConstraint(constraint);
				}
			}
		};
		tomcatFactory.setUriEncoding(Charset.forName("UTF-8"));
		tomcatFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			public void customize(Connector connector) {
				Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
				connector.setMaxPostSize(0);
				protocol.setMaxHttpHeaderSize(512000);
			}

		});
		// 是否启动http访问
		if (sslEnabled && httpLaunch) {
			tomcatFactory.addAdditionalTomcatConnectors(httpConnector());
		}
		return tomcatFactory;
	}

	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		// http端口号
		connector.setPort(httpPort);
		if (httpSwitch) {
			// 跳转到https
			connector.setSecure(false);
		} else {
			// 不跳转到https
			connector.setSecure(true);
		}
		// https的端口号
		connector.setRedirectPort(httpsPort);
		return connector;
	}
}
