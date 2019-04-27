package cn.hp.shiro;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import cn.hp.redis.RedisManager;
import cn.hp.service.SessionDBService;
import cn.hp.vo.SessionVo;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisSessionDao extends MemorySessionDAO {
    private static final Logger log = LoggerFactory.getLogger(RedisSessionDao.class);
    @Autowired
    RedisManager redisManager;

    public RedisSessionDao() {
    }
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.storeSession(sessionId, session);
        return sessionId;
    }

    @Override
    protected Session storeSession(Serializable id, Session session) {
        if (id == null) {
            throw new NullPointerException("id argument cannot be null.");
        } else {
            SessionVo vo = new SessionVo();
            vo.setSession(session);
            Object usernameObj=session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            String username=null;
            if(usernameObj!=null){
                username=usernameObj.toString();
                vo.setName(username);
            }
            redisManager.hset(id.toString(),vo);
            return session;
        }
    }
    @Override
    protected Session doReadSession(Serializable sessionId) {
        return redisManager.hget(sessionId.toString()).getSession();
    }
    @Override
    public void update(Session session) throws UnknownSessionException {
        this.storeSession(session.getId(), session);
    }
    @Override
    public void delete(Session session) {
        if (session == null) {
            throw new NullPointerException("session argument cannot be null.");
        } else {
            redisManager.hdel(session.getId().toString());
        }
    }
    @Override
    public Collection<Session> getActiveSessions() {
        Collection<Session> values=redisManager.getSessions();
        return (Collection)(CollectionUtils.isEmpty(values) ? Collections.emptySet() : Collections.unmodifiableCollection(values));
    }


}

