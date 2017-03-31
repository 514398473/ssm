/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.reids.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import com.xz.reids.service.RedisService;

@Service
public class RedisSingleServiceImpl implements RedisService {

	@Autowired
	private JedisPool jedisPool;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#set(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void set(String key, List<String> list) {
		Jedis jedis = jedisPool.getResource();
		jedis.rpush(key, (String[]) list.toArray());
		jedis.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#del(java.lang.String)
	 */
	@Override
	public void del(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.del(key);
		jedis.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#expire(java.lang.String, int)
	 */
	@Override
	public Long expire(String key, int seconds) {
		if (seconds <= 0) {
			return null;
		}
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#flushAll()
	 */
	@Override
	public String flushAll() {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.flushAll();
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rename(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String rename(String oldkey, String newkey) {
		return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#renamenx(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long renamenx(String oldkey, String newkey) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.renamenx(oldkey, newkey);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rename(byte[], byte[])
	 */
	@Override
	public String rename(byte[] oldkey, byte[] newkey) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.rename(oldkey, newkey);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#ttl(java.lang.String)
	 */
	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#persist(java.lang.String)
	 */
	@Override
	public long persist(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.persist(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#del(java.lang.String)
	 */
	@Override
	public long del(String... keys) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sort(java.lang.String)
	 */
	@Override
	public List<String> sort(String key) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.sort(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sort(java.lang.String,
	 *      redis.clients.jedis.SortingParams)
	 */
	@Override
	public List<String> sort(String key, SortingParams parame) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.sort(key, parame);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#type(java.lang.String)
	 */
	@Override
	public String type(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.type(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#scard(java.lang.String)
	 */
	@Override
	public long scard(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.scard(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sdiff(java.lang.String)
	 */
	@Override
	public Set<String> sdiff(String... keys) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.sdiff(keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sdiffstore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long sdiffstore(String newkey, String... keys) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.sdiffstore(newkey, keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sinter(java.lang.String)
	 */
	@Override
	public Set<String> sinter(String... keys) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.sinter(keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sinterstore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long sinterstore(String newkey, String... keys) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.sinterstore(newkey, keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sismember(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean sismember(String key, String member) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.sismember(key, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#smembers(java.lang.String)
	 */
	@Override
	public Set<String> smembers(String key) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.smembers(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#smove(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public long smove(String srckey, String dstkey, String member) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.smove(srckey, dstkey, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#spop(java.lang.String)
	 */
	@Override
	public String spop(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.spop(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#srem(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long srem(String key, String member) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.srem(key, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sunion(java.lang.String)
	 */
	@Override
	public Set<String> sunion(String... keys) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.sunion(keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sunionstore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long sunionstore(String newkey, String... keys) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.sunionstore(newkey, keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zadd(java.lang.String, double,
	 *      java.lang.String)
	 */
	@Override
	public long zadd(String key, double score, String member) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zadd(key, score, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zcard(java.lang.String)
	 */
	@Override
	public long zcard(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zcard(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zcount(java.lang.String, double,
	 *      double)
	 */
	@Override
	public long zcount(String key, double min, double max) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zcount(key, min, max);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zlength(java.lang.String)
	 */
	@Override
	public long zlength(String key) {
		long len = 0;
		Set<String> set = zrange(key, 0, -1);
		len = set.size();
		return len;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zincrby(java.lang.String, double,
	 *      java.lang.String)
	 */
	@Override
	public double zincrby(String key, double score, String member) {
		Jedis jedis = jedisPool.getResource();
		Double result = jedis.zincrby(key, score, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrange(java.lang.String, int, int)
	 */
	@Override
	public Set<String> zrange(String key, int start, int end) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.zrange(key, start, end);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrangeByScore(java.lang.String,
	 *      double, double)
	 */
	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.zrangeByScore(key, min, max);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrank(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long zrank(String key, String member) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zrank(key, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrevrank(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long zrevrank(String key, String member) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zrevrank(key, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrem(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long zrem(String key, String member) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zrem(key, member);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrem(java.lang.String)
	 */
	@Override
	public long zrem(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zrem(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zremrangeByRank(java.lang.String,
	 *      int, int)
	 */
	@Override
	public long zremrangeByRank(String key, int start, int end) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zremrangeByRank(key, start, end);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zremrangeByScore(java.lang.String,
	 *      double, double)
	 */
	@Override
	public long zremrangeByScore(String key, double min, double max) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.zremrangeByScore(key, min, max);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrevrange(java.lang.String, int,
	 *      int)
	 */
	@Override
	public Set<String> zrevrange(String key, int start, int end) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.zrevrange(key, start, end);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zscore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public double zscore(String key, String memebr) {
		Jedis jedis = jedisPool.getResource();
		Double score = jedis.zscore(key, memebr);
		jedis.close();
		if (score != null)
			return score;
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hdel(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long hdel(String key, String fieid) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key, fieid);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hdel(java.lang.String)
	 */
	@Override
	public long hdel(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hexists(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean hexists(String key, String fieid) {
		Jedis jedis = jedisPool.getResource();
		Boolean result = jedis.hexists(key, fieid);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hget(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String hget(String key, String fieid) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(key, fieid);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hget(byte[], byte[])
	 */
	@Override
	public byte[] hget(byte[] key, byte[] fieid) {
		Jedis jedis = jedisPool.getResource();
		byte[] result = jedis.hget(key, fieid);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hgetAll(java.lang.String)
	 */
	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = jedisPool.getResource();
		Map<String, String> result = jedis.hgetAll(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hset(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public long hset(String key, String fieid, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key, fieid, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hset(java.lang.String,
	 *      java.lang.String, byte[])
	 */
	@Override
	public long hset(String key, String fieid, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(key.getBytes(), fieid.getBytes(), value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hsetnx(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public long hsetnx(String key, String fieid, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hsetnx(key, fieid, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hvals(java.lang.String)
	 */
	@Override
	public List<String> hvals(String key) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.hvals(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hincrby(java.lang.String,
	 *      java.lang.String, long)
	 */
	@Override
	public long hincrby(String key, String fieid, long value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hincrBy(key, fieid, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hkeys(java.lang.String)
	 */
	@Override
	public Set<String> hkeys(String key) {
		Jedis jedis = jedisPool.getResource();
		Set<String> result = jedis.hkeys(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hlen(java.lang.String)
	 */
	@Override
	public long hlen(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hlen(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmget(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<String> hmget(String key, String... fieids) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.hmget(key, fieids);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmget(byte[], byte)
	 */
	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fieids) {
		Jedis jedis = jedisPool.getResource();
		List<byte[]> result = jedis.hmget(key, fieids);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmset(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public String hmset(String key, Map<String, String> map) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hmset(key, map);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmset(byte[], java.util.Map)
	 */
	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> map) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hmset(key, map);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#get(java.lang.String)
	 */
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#get(byte[])
	 */
	@Override
	public byte[] get(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		byte[] result = jedis.get(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setEx(java.lang.String, int,
	 *      java.lang.String)
	 */
	@Override
	public String setEx(String key, int seconds, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.setex(key, seconds, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setEx(byte[], int, byte[])
	 */
	@Override
	public String setEx(byte[] key, int seconds, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.setex(key, seconds, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setnx(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long setnx(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.setnx(key, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#set(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String set(String key, String value) {
		return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#set(java.lang.String, byte[])
	 */
	@Override
	public String set(String key, byte[] value) {
		return set(SafeEncoder.encode(key), value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#set(byte[], byte[])
	 */
	@Override
	public String set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setRange(java.lang.String, long,
	 *      java.lang.String)
	 */
	@Override
	public long setRange(String key, long offset, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.setrange(key, offset, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#append(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long append(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.append(key, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#decrBy(java.lang.String, long)
	 */
	@Override
	public long decrBy(String key, long number) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.decrBy(key, number);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#incrBy(java.lang.String, long)
	 */
	@Override
	public long incrBy(String key, long number) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incrBy(key, number);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#getrange(java.lang.String, long,
	 *      long)
	 */
	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.getrange(key, startOffset, endOffset);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#getSet(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.getSet(key, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#mget(java.lang.String)
	 */
	@Override
	public List<String> mget(String... keys) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.mget(keys);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#mset(java.lang.String)
	 */
	@Override
	public String mset(String... keysvalues) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.mset(keysvalues);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#strlen(java.lang.String)
	 */
	@Override
	public long strlen(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.strlen(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#llen(java.lang.String)
	 */
	@Override
	public long llen(String key) {
		return llen(SafeEncoder.encode(key));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#llen(byte[])
	 */
	@Override
	public long llen(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.llen(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lset(byte[], int, byte[])
	 */
	@Override
	public String lset(byte[] key, int index, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.lset(key, index, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lset(java.lang.String, int,
	 *      java.lang.String)
	 */
	@Override
	public String lset(String key, int index, String value) {
		return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#linsert(java.lang.String,
	 *      redis.clients.jedis.BinaryClient.LIST_POSITION, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long linsert(String key, LIST_POSITION where, String pivot, String value) {
		return linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#linsert(byte[],
	 *      redis.clients.jedis.BinaryClient.LIST_POSITION, byte[], byte[])
	 */
	@Override
	public long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.linsert(key, where, pivot, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lindex(java.lang.String, int)
	 */
	@Override
	public String lindex(String key, int index) {
		return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lindex(byte[], int)
	 */
	@Override
	public byte[] lindex(byte[] key, int index) {
		Jedis jedis = jedisPool.getResource();
		byte[] result = jedis.lindex(key, index);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lpop(java.lang.String)
	 */
	@Override
	public String lpop(String key) {
		return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lpop(byte[])
	 */
	@Override
	public byte[] lpop(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		byte[] result = jedis.lpop(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rpop(java.lang.String)
	 */
	@Override
	public String rpop(String key) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.rpop(key);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lpush(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long lpush(String key, String value) {
		return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rpush(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long rpush(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.rpush(key, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rpush(byte[], byte[])
	 */
	@Override
	public long rpush(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.rpush(key, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lpush(byte[], byte[])
	 */
	@Override
	public long lpush(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.lpush(key, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lrange(java.lang.String, long,
	 *      long)
	 */
	@Override
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.lrange(key, start, end);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lrange(byte[], int, int)
	 */
	@Override
	public List<byte[]> lrange(byte[] key, int start, int end) {
		Jedis jedis = jedisPool.getResource();
		List<byte[]> result = jedis.lrange(key, start, end);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lrem(byte[], int, byte[])
	 */
	@Override
	public long lrem(byte[] key, int c, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.lrem(key, c, value);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lrem(java.lang.String, int,
	 *      java.lang.String)
	 */
	@Override
	public long lrem(String key, int c, String value) {
		return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#ltrim(byte[], int, int)
	 */
	@Override
	public String ltrim(byte[] key, int start, int end) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.ltrim(key, start, end);
		jedis.close();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#ltrim(java.lang.String, int, int)
	 */
	@Override
	public String ltrim(String key, int start, int end) {
		return ltrim(SafeEncoder.encode(key), start, end);
	}

}
