package cn.hp.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisJava {
    private static final Logger logger = LoggerFactory.getLogger(RedisJava.class);
    private String hostname="192.168.239.128";
    private Integer port=6379;
    private JedisConnectionFactory factory;
    private RedisTemplate<Serializable, Object> redisTemplate;

    public RedisJava(){
        RedisStandaloneConfiguration config=new RedisStandaloneConfiguration();
        config.setHostName(hostname);
        config.setPort(port);
        factory=new JedisConnectionFactory(config);
        StringRedisSerializer k=new StringRedisSerializer();
        JdkSerializationRedisSerializer v=new JdkSerializationRedisSerializer();
        redisTemplate=new RedisTemplate<Serializable, Object>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(k);
        redisTemplate.setValueSerializer(v);
        /**必须执行这个函数,初始化RedisTemplate*/
        redisTemplate.afterPropertiesSet();
    }
    public static void main(String[] args) {
        RedisJava redis=new RedisJava();
        //redis.redisTemplate.boundHashOps("A").put("1","2");
        //redis.redisTemplate.boundHashOps("A").keys().forEach(i -> System.out.println(i));
        System.out.println(redis.redisTemplate.boundHashOps("A").delete(1));
    }


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error("set cache error", e);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("set cache error", e);
        }
        return result;
    }
}
