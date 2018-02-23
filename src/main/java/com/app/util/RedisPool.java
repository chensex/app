package com.app.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
	private static JedisPool pool = null;
	private RedisPool() {
	}

	static{
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(32);
        config.setMaxIdle(6);  
        config.setMinIdle(0);  
        config.setMaxWaitMillis(15000);
        config.setMinEvictableIdleTimeMillis(300000);  
        config.setSoftMinEvictableIdleTimeMillis(-1);  
        config.setNumTestsPerEvictionRun(3);  
        config.setTestOnBorrow(false);  
        config.setTestOnReturn(false);  
        config.setTestWhileIdle(false);
        pool = new JedisPool(config,"127.0.0.1",6379,15000);
	}
	private static class InnerClass {
		private static final RedisPool SINGLE_TON = new RedisPool();
	}

	public static final RedisPool getInstance() {
		return InnerClass.SINGLE_TON;
	}
	
	public static Jedis getJedis(){
		Jedis client = null;
       if(pool!=null){
    	   client = pool.getResource();//从pool中获取资源
           return client;
       }else{
    	   client = null;
       }
        return client;
	}
	public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
        	jedis.close();
        }
    }
}
