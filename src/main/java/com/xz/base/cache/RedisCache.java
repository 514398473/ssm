/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.xz.base.utils.PropertiesUtil;
import com.xz.base.utils.SerializeUtil;

/**
 * mybatis缓存的redis实现
 * <p>
 * mybatis缓存的redis实现
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月31日
 */
public class RedisCache implements Cache {

	private static Log logger = LogFactory.getLog(RedisCache.class);

	private JedisCluster jedisCluster = createClient();

	/** The ReadWriteLock. */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private String id;

	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		logger.debug(">>>MybatisRedisCache:id=" + id);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getSize() {
		try {
			return Integer.valueOf(jedisCluster.dbSize().toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void putObject(Object key, Object value) {
		try {
			jedisCluster.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
			logger.debug(">>>putObject:" + key + "=" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getObject(Object key) {
		try {
			Object value = SerializeUtil.unserialize(jedisCluster.get(SerializeUtil.serialize(key.toString())));
			logger.debug(">>>getObject:" + key + "=" + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object removeObject(Object key) {
		try {
			logger.debug(">>>removeObject:" + key);
			return jedisCluster.expire(SerializeUtil.serialize(key.toString()), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void clear() {
		try {
			jedisCluster.flushDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	private JedisCluster createClient() {
		try {
			String configFile = "properties/redis.properties";
			String redis_host1 = PropertiesUtil.getValue(configFile, "redis_host1");
			String redis_host2 = PropertiesUtil.getValue(configFile, "redis_host2");
			String redis_host3 = PropertiesUtil.getValue(configFile, "redis_host3");
			String redis_host4 = PropertiesUtil.getValue(configFile, "redis_host4");
			String redis_host5 = PropertiesUtil.getValue(configFile, "redis_host5");
			String redis_host6 = PropertiesUtil.getValue(configFile, "redis_host6");
			String redis_port1 = PropertiesUtil.getValue(configFile, "redis_port1");
			String redis_port2 = PropertiesUtil.getValue(configFile, "redis_port2");
			String redis_port3 = PropertiesUtil.getValue(configFile, "redis_port3");
			String redis_port4 = PropertiesUtil.getValue(configFile, "redis_port4");
			String redis_port5 = PropertiesUtil.getValue(configFile, "redis_port5");
			String redis_port6 = PropertiesUtil.getValue(configFile, "redis_port6");
			// 创建一个JedisCluster对象
			Set<HostAndPort> nodes = new HashSet<>();
			nodes.add(new HostAndPort(StringUtils.isNotBlank(redis_host1) ? redis_host1 : "", StringUtils.isNotBlank(redis_port1) ? Integer.parseInt(redis_port1) : 0));
			nodes.add(new HostAndPort(StringUtils.isNotBlank(redis_host2) ? redis_host2 : "", StringUtils.isNotBlank(redis_port2) ? Integer.parseInt(redis_port2) : 0));
			nodes.add(new HostAndPort(StringUtils.isNotBlank(redis_host3) ? redis_host3 : "", StringUtils.isNotBlank(redis_port3) ? Integer.parseInt(redis_port3) : 0));
			nodes.add(new HostAndPort(StringUtils.isNotBlank(redis_host4) ? redis_host4 : "", StringUtils.isNotBlank(redis_port4) ? Integer.parseInt(redis_port4) : 0));
			nodes.add(new HostAndPort(StringUtils.isNotBlank(redis_host5) ? redis_host5 : "", StringUtils.isNotBlank(redis_port5) ? Integer.parseInt(redis_port5) : 0));
			nodes.add(new HostAndPort(StringUtils.isNotBlank(redis_host6) ? redis_host6 : "", StringUtils.isNotBlank(redis_port6) ? Integer.parseInt(redis_port6) : 0));
			// jedisCluster在系统中是单例的。
			return new JedisCluster(nodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
