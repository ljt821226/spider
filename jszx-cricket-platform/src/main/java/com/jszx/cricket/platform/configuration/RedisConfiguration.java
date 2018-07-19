package com.jszx.cricket.platform.configuration;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Redis配置类:配置Redis相关信息
 * 
 * @version   1.0    
 * @author   2724216806@qq.com
 * @date 2018年3月16日 下午5:48:29
 *
 */

@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
	
	// 过期时间
	private final long EXPIRATION_TIME = 3000;
	
	
	@Autowired
	private RedisConnectionFactory connectionFactory;
	

	/**
	 * 缓存Key生成方法
	 */
	@Bean
	public KeyGenerator commonKeyGenerator() {
		return new KeyGenerator() {
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(doEncode(obj.toString()));
				}
				return sb.toString();
			}
		};
	}
	
	
	/**
	 * 缓存管理器
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager() {
		RedisCacheManagerBuilder builder = RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory);
		Set<String> cacheNames = new HashSet<String>() {{
			add("codeNameCache");
		}};
		builder.initialCacheNames(cacheNames);
		return builder.build();

	}

	

	/**
	 * 
	 * RedisTemplate模板
	 * 
	 * @param factory
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月18日 下午3:58:00
	 */

	@Bean("redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=buildValueSerializer();
		
		//设置kv序列化
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		
		//设置hash序列化
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        
        redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	private Jackson2JsonRedisSerializer<Object> buildValueSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		return jackson2JsonRedisSerializer;
	}

	private String doEncode(String msg) {
		try {
			return URLEncoder.encode(msg, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return msg;
		}
	}

}
