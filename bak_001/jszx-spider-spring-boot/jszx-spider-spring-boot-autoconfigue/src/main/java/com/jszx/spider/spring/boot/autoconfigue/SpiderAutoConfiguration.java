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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;

import com.jszx.spider.platform.properties.MybatisProperties;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class SpiderAutoConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(SpiderAutoConfiguration.class);

	// bean 的名称生成器.
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

	private final String CXF_ADDRESS = "/";

	@Value("${server.context-path:null}")
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
	private MybatisProperties mybatisProperties;

	public SpiderAutoConfiguration() {
	}

	@PostConstruct
	public void checkConfigFileExists() {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
	}

	@Bean
	public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor() {
		return new BeanDefinitionRegistryPostProcessor() {

			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
				// TODO Auto-generated method stub
				logger.info("ConfigurableListableBeanFactory");
			}

			@Override
			public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
				ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + mybatisProperties.getDao());

				scanner.scan(new String[] { "com.jszx" });
				// for (String basePackage :
				// ApplicationContainer.getBasePackages()) {
				// scanner.scan(basePackage);
				// }

			}
		};
	}

}