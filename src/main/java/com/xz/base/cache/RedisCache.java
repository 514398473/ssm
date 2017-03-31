/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.cache;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
			String configFile = "redis.properties";
			// 以URL形式获取工程的资源文件 classpath 路径, 得到以file:/为开头的URL
			// 例如返回: file:/D:/workspace/myproject01/WEB-INF/classes/
			URL classPath = Thread.currentThread().getContextClassLoader().getResource("");
			String proFilePath = classPath.toString();

			// 移除开通的file:/六个字符
			proFilePath = proFilePath.substring(6);

			// 如果为window系统下,则把路径中的路径分隔符替换为window系统的文件路径分隔符
			proFilePath = proFilePath.replace("/", java.io.File.separator);

			// 兼容处理最后一个字符是否为 window系统的文件路径分隔符,同时建立 properties 文件路径
			// 例如返回: D:\workspace\myproject01\WEB-INF\classes\config.properties
			if (!proFilePath.endsWith(java.io.File.separator)) {
				proFilePath = proFilePath + java.io.File.separator + configFile;
			} else {
				proFilePath = proFilePath + configFile;
			}
			String redis_host1 = PropertiesUtil.getValue(proFilePath, "redis_host1");
			String redis_host2 = PropertiesUtil.getValue(proFilePath, "redis_host2");
			String redis_host3 = PropertiesUtil.getValue(proFilePath, "redis_host3");
			String redis_host4 = PropertiesUtil.getValue(proFilePath, "redis_host4");
			String redis_host5 = PropertiesUtil.getValue(proFilePath, "redis_host5");
			String redis_host6 = PropertiesUtil.getValue(proFilePath, "redis_host6");
			String redis_port1 = PropertiesUtil.getValue(proFilePath, "redis_port1");
			String redis_port2 = PropertiesUtil.getValue(proFilePath, "redis_port2");
			String redis_port3 = PropertiesUtil.getValue(proFilePath, "redis_port3");
			String redis_port4 = PropertiesUtil.getValue(proFilePath, "redis_port4");
			String redis_port5 = PropertiesUtil.getValue(proFilePath, "redis_port5");
			String redis_port6 = PropertiesUtil.getValue(proFilePath, "redis_port6");
			// 创建一个JedisCluster对象
			Set<HostAndPort> nodes;
			nodes = new HashSet<>();
			nodes.add(new HostAndPort(redis_host1, Integer.parseInt(redis_port1)));
			nodes.add(new HostAndPort(redis_host2, Integer.parseInt(redis_port2)));
			nodes.add(new HostAndPort(redis_host3, Integer.parseInt(redis_port3)));
			nodes.add(new HostAndPort(redis_host4, Integer.parseInt(redis_port4)));
			nodes.add(new HostAndPort(redis_host5, Integer.parseInt(redis_port5)));
			nodes.add(new HostAndPort(redis_host6, Integer.parseInt(redis_port6)));
			// jedisCluster在系统中是单例的。
			return new JedisCluster(nodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
