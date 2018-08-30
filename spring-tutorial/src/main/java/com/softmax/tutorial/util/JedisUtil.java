package com.softmax.tutorial.util;

import com.softmax.tutorial.config.JedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Jarvis
 * @date 2018/8/29
 */
public class JedisUtil {
    private final static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    private static volatile JedisPool jedisPool;

    /**
     * 对某个键的值自增wocai
     *
     * @param key
     * @param expireDate
     * @return
     */
    public static long setIncr(String key, int expireDate) {
        long result = 0;
        Jedis jedis;
        try {
            jedisPool = JedisConfig.getJedisPoolInstance();
            jedis = jedisPool.getResource();
            result = jedis.incr(key);
            if (expireDate != 0) {
                jedis.expire(key, expireDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("set " + key + " = " + result);
        }

        return result;
    }

    /**
     * 释放资源
     */
    public static void release() {
        JedisConfig.release();
    }


}
