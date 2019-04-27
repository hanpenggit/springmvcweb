package cn.hp.redis;

import cn.hp.vo.SessionVo;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RedisManager {

    private RedisTemplate<String, SessionVo> redisTemplate;

    private static final String KEY = "shareSessionMap";

    public void hset(String sessionId, SessionVo session){
        redisTemplate.boundHashOps(KEY).put(sessionId, session);
    }

    public void hdel(String sessionId){
        redisTemplate.boundHashOps(KEY).delete(sessionId);
    }

    public SessionVo hget(String sessionId){
        return (SessionVo) redisTemplate.boundHashOps(KEY).get(sessionId);
    }

    public List<Session> getSessions(){
        return redisTemplate.boundHashOps(KEY).values().stream().map(i -> ((SessionVo)i)).filter(i -> i.isOnline()).map(i -> i.getSession()).collect(Collectors.toList());
    }

    public void setRedisTemplate(RedisTemplate<String, SessionVo> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
