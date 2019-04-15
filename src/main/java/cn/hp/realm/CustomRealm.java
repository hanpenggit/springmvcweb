package cn.hp.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

public class CustomRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(CustomRealm.class);

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
        if (token!=null&&username.equals("hanpeng")&&password.equals("hanpeng")) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "hanpeng");
            return authenticationInfo;
        }
        return null;
    }
}
