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

import cn.hp.service.SessionDBService;
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

public class MemoryDBSessionDao extends MemorySessionDAO {
    private static final Logger log = LoggerFactory.getLogger(MemoryDBSessionDao.class);
    @Autowired
    SessionDBService sessionService;

    public MemoryDBSessionDao() {
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
            Object usernameObj=session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            String username=null;
            if(usernameObj!=null){
                username=usernameObj.toString();
            }
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("sessionid",id.toString());
            params.put("username",username);
            byte[] bytes=convertToBinary(session);
            params.put("sessionobj",bytes);
            Map<String,Object> sessionInfo=sessionService.get(params);
            if(sessionInfo==null){
                sessionService.insert(params);
            }else{
                sessionService.update(params);
            }
            return session;
        }
    }
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Map<String,Object> params=new HashMap<>();
        params.put("sessionid",sessionId.toString());
        Map<String,Object> result=sessionService.get(params);
        if(result!=null){
            Object sessionobj=result.get("sessionobj");
            if(sessionobj!=null){
                byte[] bytes= (byte[])sessionobj;
                return convertToObject(bytes);
            }
        }
        return null;
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
            Serializable id = session.getId();
            if (id != null) {
                sessionService.delete(id.toString());
            }

        }
    }
    @Override
    public Collection<Session> getActiveSessions() {
        List<Map<String,Object>> sessions = sessionService.list();
        Collection<Session> values=sessions.stream().map(i -> (byte[])i.get("sessionobj")).map(i -> convertToObject(i)).collect(Collectors.toList());
        return (Collection)(CollectionUtils.isEmpty(values) ? Collections.emptySet() : Collections.unmodifiableCollection(values));
    }

    public Session convertToObject(byte[]bytes){
        try {
            ByteInputStream bis=new ByteInputStream();
            bis.setBuf(bytes);
            ObjectInputStream ois=new ObjectInputStream(bis);
            Object obj=ois.readObject();
            ois.close();
            bis.close();
            return (Session)obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] convertToBinary(Object obj){
        byte[]bytes=null;
        try {
            ByteOutputStream byteOS=new ByteOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(byteOS);
            oos.writeObject(obj);
            oos.close();
            bytes=byteOS.getBytes();
            byteOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}

