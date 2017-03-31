package com.xz.reids.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import com.xz.reids.service.RedisService;

//@Service
public class RedisClusterServiceImpl implements RedisService {

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#set(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void set(String key, List<String> list) {
		jedisCluster.rpush(key, (String[]) list.toArray());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#del(java.lang.String)
	 */
	@Override
	public void del(String key) {
		jedisCluster.del(key);
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
		return jedisCluster.expire(key, seconds);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#flushAll()
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String flushAll() {
		return jedisCluster.flushAll();
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
		return jedisCluster.renamenx(oldkey, newkey);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rename(byte[], byte[])
	 */
	@Override
	public String rename(byte[] oldkey, byte[] newkey) {
		return jedisCluster.rename(oldkey, newkey);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#ttl(java.lang.String)
	 */
	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#persist(java.lang.String)
	 */
	@Override
	public long persist(String key) {
		return jedisCluster.persist(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#del(java.lang.String)
	 */
	@Override
	public long del(String... keys) {
		return jedisCluster.del(keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sort(java.lang.String)
	 */
	@Override
	public List<String> sort(String key) {
		return jedisCluster.sort(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sort(java.lang.String,
	 *      redis.clients.jedis.SortingParams)
	 */
	@Override
	public List<String> sort(String key, SortingParams parame) {
		return jedisCluster.sort(key, parame);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#type(java.lang.String)
	 */
	@Override
	public String type(String key) {
		return jedisCluster.type(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#scard(java.lang.String)
	 */
	@Override
	public long scard(String key) {
		return jedisCluster.scard(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sdiff(java.lang.String)
	 */
	@Override
	public Set<String> sdiff(String... keys) {
		return jedisCluster.sdiff(keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sdiffstore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long sdiffstore(String newkey, String... keys) {
		return jedisCluster.sdiffstore(newkey, keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sinter(java.lang.String)
	 */
	@Override
	public Set<String> sinter(String... keys) {
		return jedisCluster.sinter(keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sinterstore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long sinterstore(String newkey, String... keys) {
		return jedisCluster.sinterstore(newkey, keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sismember(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean sismember(String key, String member) {
		return jedisCluster.sismember(key, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#smembers(java.lang.String)
	 */
	@Override
	public Set<String> smembers(String key) {
		return jedisCluster.smembers(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#smove(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public long smove(String srckey, String dstkey, String member) {
		return jedisCluster.smove(srckey, dstkey, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#spop(java.lang.String)
	 */
	@Override
	public String spop(String key) {
		return jedisCluster.spop(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#srem(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long srem(String key, String member) {
		return jedisCluster.srem(key, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sunion(java.lang.String)
	 */
	@Override
	public Set<String> sunion(String... keys) {
		return jedisCluster.sunion(keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#sunionstore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long sunionstore(String newkey, String... keys) {
		return jedisCluster.sunionstore(newkey, keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zadd(java.lang.String, double,
	 *      java.lang.String)
	 */
	@Override
	public long zadd(String key, double score, String member) {
		return jedisCluster.zadd(key, score, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zcard(java.lang.String)
	 */
	@Override
	public long zcard(String key) {
		return jedisCluster.zcard(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zcount(java.lang.String, double,
	 *      double)
	 */
	@Override
	public long zcount(String key, double min, double max) {
		return jedisCluster.zcount(key, min, max);
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
		return jedisCluster.zincrby(key, score, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrange(java.lang.String, int, int)
	 */
	@Override
	public Set<String> zrange(String key, int start, int end) {
		return jedisCluster.zrange(key, start, end);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrangeByScore(java.lang.String,
	 *      double, double)
	 */
	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return jedisCluster.zrangeByScore(key, min, max);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrank(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long zrank(String key, String member) {
		return jedisCluster.zrank(key, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrevrank(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long zrevrank(String key, String member) {
		return jedisCluster.zrevrank(key, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrem(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long zrem(String key, String member) {
		return jedisCluster.zrem(key, member);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrem(java.lang.String)
	 */
	@Override
	public long zrem(String key) {
		return jedisCluster.zrem(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zremrangeByRank(java.lang.String,
	 *      int, int)
	 */
	@Override
	public long zremrangeByRank(String key, int start, int end) {
		return jedisCluster.zremrangeByRank(key, start, end);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zremrangeByScore(java.lang.String,
	 *      double, double)
	 */
	@Override
	public long zremrangeByScore(String key, double min, double max) {
		return jedisCluster.zremrangeByScore(key, min, max);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zrevrange(java.lang.String, int,
	 *      int)
	 */
	@Override
	public Set<String> zrevrange(String key, int start, int end) {
		return jedisCluster.zrevrange(key, start, end);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#zscore(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public double zscore(String key, String memebr) {
		Double score = jedisCluster.zscore(key, memebr);
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
		return jedisCluster.hdel(key, fieid);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hdel(java.lang.String)
	 */
	@Override
	public long hdel(String key) {
		return jedisCluster.hdel(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hexists(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean hexists(String key, String fieid) {
		return jedisCluster.hexists(key, fieid);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hget(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String hget(String key, String fieid) {
		return jedisCluster.hget(key, fieid);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hget(byte[], byte[])
	 */
	@Override
	public byte[] hget(byte[] key, byte[] fieid) {
		return jedisCluster.hget(key, fieid);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hgetAll(java.lang.String)
	 */
	@Override
	public Map<String, String> hgetAll(String key) {
		return jedisCluster.hgetAll(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hset(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public long hset(String key, String fieid, String value) {
		return jedisCluster.hset(key, fieid, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hset(java.lang.String,
	 *      java.lang.String, byte[])
	 */
	@Override
	public long hset(String key, String fieid, byte[] value) {
		return jedisCluster.hset(key.getBytes(), fieid.getBytes(), value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hsetnx(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public long hsetnx(String key, String fieid, String value) {
		return jedisCluster.hsetnx(key, fieid, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hvals(java.lang.String)
	 */
	@Override
	public List<String> hvals(String key) {
		return jedisCluster.hvals(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hincrby(java.lang.String,
	 *      java.lang.String, long)
	 */
	@Override
	public long hincrby(String key, String fieid, long value) {
		return jedisCluster.hincrBy(key, fieid, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hkeys(java.lang.String)
	 */
	@Override
	public Set<String> hkeys(String key) {
		return jedisCluster.hkeys(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hlen(java.lang.String)
	 */
	@Override
	public long hlen(String key) {
		return jedisCluster.hlen(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmget(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<String> hmget(String key, String... fieids) {
		return jedisCluster.hmget(key, fieids);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmget(byte[], byte)
	 */
	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fieids) {
		return jedisCluster.hmget(key, fieids);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmset(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public String hmset(String key, Map<String, String> map) {
		return jedisCluster.hmset(key, map);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#hmset(byte[], java.util.Map)
	 */
	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> map) {
		return jedisCluster.hmset(key, map);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#get(java.lang.String)
	 */
	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#get(byte[])
	 */
	@Override
	public byte[] get(byte[] key) {
		return jedisCluster.get(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setEx(java.lang.String, int,
	 *      java.lang.String)
	 */
	@Override
	public String setEx(String key, int seconds, String value) {
		return jedisCluster.setex(key, seconds, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setEx(byte[], int, byte[])
	 */
	@Override
	public String setEx(byte[] key, int seconds, byte[] value) {
		return jedisCluster.setex(key, seconds, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setnx(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long setnx(String key, String value) {
		return jedisCluster.setnx(key, value);
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
		return jedisCluster.set(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#setRange(java.lang.String, long,
	 *      java.lang.String)
	 */
	@Override
	public long setRange(String key, long offset, String value) {
		return jedisCluster.setrange(key, offset, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#append(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long append(String key, String value) {
		return jedisCluster.append(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#decrBy(java.lang.String, long)
	 */
	@Override
	public long decrBy(String key, long number) {
		return jedisCluster.decrBy(key, number);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#incrBy(java.lang.String, long)
	 */
	@Override
	public long incrBy(String key, long number) {
		return jedisCluster.incrBy(key, number);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#getrange(java.lang.String, long,
	 *      long)
	 */
	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return jedisCluster.getrange(key, startOffset, endOffset);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#getSet(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getSet(String key, String value) {
		return jedisCluster.getSet(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#mget(java.lang.String)
	 */
	@Override
	public List<String> mget(String... keys) {
		return jedisCluster.mget(keys);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#mset(java.lang.String)
	 */
	@Override
	public String mset(String... keysvalues) {
		return jedisCluster.mset(keysvalues);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#strlen(java.lang.String)
	 */
	@Override
	public long strlen(String key) {
		return jedisCluster.strlen(key);
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
		return jedisCluster.llen(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lset(byte[], int, byte[])
	 */
	@Override
	public String lset(byte[] key, int index, byte[] value) {
		return jedisCluster.lset(key, index, value);
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
		return jedisCluster.linsert(key, where, pivot, value);
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
		return jedisCluster.lindex(key, index);
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
		return jedisCluster.lpop(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rpop(java.lang.String)
	 */
	@Override
	public String rpop(String key) {
		return jedisCluster.rpop(key);
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
		return jedisCluster.rpush(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#rpush(byte[], byte[])
	 */
	@Override
	public long rpush(byte[] key, byte[] value) {
		return jedisCluster.rpush(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lpush(byte[], byte[])
	 */
	@Override
	public long lpush(byte[] key, byte[] value) {
		return jedisCluster.lpush(key, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lrange(java.lang.String, long,
	 *      long)
	 */
	@Override
	public List<String> lrange(String key, long start, long end) {
		return jedisCluster.lrange(key, start, end);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lrange(byte[], int, int)
	 */
	@Override
	public List<byte[]> lrange(byte[] key, int start, int end) {
		return jedisCluster.lrange(key, start, end);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.reids.service.RedisService#lrem(byte[], int, byte[])
	 */
	@Override
	public long lrem(byte[] key, int c, byte[] value) {
		return jedisCluster.lrem(key, c, value);
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
		return jedisCluster.ltrim(key, start, end);
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