package com.jszx.spider.platform.configuration;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;

/**
 * 
 * 缓存配置类:提供缓存配置功能
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2016年10月5日 下午10:01:29
 *
 */
//@EnableCaching
//@Configuration
public class CacheConfiguration {

	/**
	 * 
	 * 管理器
	 * 
	 * @param bean
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2016年10月5日 下午10:07:37
	 */
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
		return new EhCacheCacheManager(bean.getObject());
	}

	/*
	 * 据shared与否的设置, Spring分别通过CacheManager.create() 或new
	 * CacheManager()方式来创建一个ehcache基地.
	 *
	 * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
	 *
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(Code.CONFIG.EHCACHE.value()));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

}
