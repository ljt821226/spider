package com.jszx.spider.platform.expand.mybatis.cache;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jszx.spider.platform.tool.JsonTool;
import com.jszx.spider.platform.tool.RedisTool;

/**
 * 
 * mybatis二级缓存:提供mybatis二级缓存方案
 * 
 * @version 1.0
 * @author 2724216806@qq.com
 * @date 2018年3月16日 下午5:14:59
 *
 */

public class RedisCache implements Cache {

	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private final String id;

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.debug("MybatisRedisCache:id=" + id);
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void putObject(Object key, Object value) {
		RedisTool.getRedisTemplate().opsForValue().set(doEncode(key.toString()), value.toString());
	}

	public Object getObject(Object key) {
		String value = RedisTool.getRedisTemplate().opsForValue().get(doEncode(key.toString())).toString();
		try {
			return JsonTool.toObject(value, List.class);
		} catch (Exception e) {
			return value;
		}
	}

	public Object removeObject(Object key) {
		return null;
	}

	public void clear() {
	}

	public int getSize() {
		return 0;
	}

	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	private String doEncode(String msg) {
		try {
			msg += "MYBATIS_";
			return URLEncoder.encode(msg, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return msg;
		}
	}

}
