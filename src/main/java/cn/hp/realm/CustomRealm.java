package cn.hp.realm;

import cn.hp.service.SessionDBService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(CustomRealm.class);
    @Autowired
    SessionDBService sessionService;
    /**
     * 用户授权认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("======用户授权认证======");
        String userName = principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
//        simpleAuthorizationInfo.setRoles(userService.queryRolesByName(userName));
        return simpleAuthorizationInfo;
    }

    /**
     * 用户登陆认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("======用户登陆认证======");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username=token.getUsername();
        String password=new String(token.getPassword());
        int result=sessionService.login(username,password);
        if (result>0) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
            clearOtherLoginInfo(username);
            return authenticationInfo;
        }
        return null;
    }

    /**
     *清除该用户以前登录时保存的session
     * @param username
     */
    public void clearOtherLoginInfo(String username){
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultSessionManager sessionManager = (DefaultSessionManager)securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
        for(Session session:sessions){
            //清除该用户以前登录时保存的session
            String sessionKey=String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
            if(username.equals(sessionKey)) {
                sessionManager.getSessionDAO().delete(session);
            }
        }
    }

}
