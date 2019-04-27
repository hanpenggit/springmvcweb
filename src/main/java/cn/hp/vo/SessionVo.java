package cn.hp.vo;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;

import java.io.Serializable;

public class SessionVo implements Serializable {
    private String name;
    private Session session;

    public boolean isOnline(){
        if(StringUtils.isNoneBlank(name)){
            return true;
        }else{
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
