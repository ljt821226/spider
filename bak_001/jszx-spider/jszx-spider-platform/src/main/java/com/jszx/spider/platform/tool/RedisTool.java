package com.jszx.spider.platform.tool;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis工具类:提供基本操作方法
 * 
 * @version 1.0
 * @author lv_juntao@uisftech.com
 * @date 2017年8月18日 下午4:48:42
 * 
 */

public class RedisTool {

	private static RedisTemplate<String, Object> redisTemplate;

	public static RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		RedisTool.redisTemplate = redisTemplate;
	}

	/**
	 * 
	 * 删除
	 * 
	 * @param key
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月23日 下午4:06:57
	 */
	public static void delete(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(Arrays.asList(key));
			}
		}
	}

	/**
	 * 
	 * 获取
	 * 
	 * @param key
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月23日 下午4:07:06
	 */
	public static Object get(String key) {
		return redisTemplate.boundValueOps(key).get();
	}

	/**
	 * 
	 * 添加
	 * 
	 * @param key
	 * @param value
	 * @param date
	 * @throws Exception
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月23日 下午4:07:16
	 */
	public static void set(String key, Object value, Date date) throws Exception {
		redisTemplate.opsForValue().set(key, JsonTool.toJson(value));
		if (date != null) {
			redisTemplate.expire(key, date.getTime(), TimeUnit.SECONDS);
		}
	}

	/**
	 * 
	 * 添加
	 * 
	 * @param key
	 * @param value
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月23日 下午4:07:32
	 */
	public static void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 
	 * 清除
	 * 
	 * @return
	 * @author lv_juntao@uisftech.com
	 * @date 2017年8月23日 下午4:07:42
	 */
	public static Boolean flush() {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return true;
			}
		});
	}

}
