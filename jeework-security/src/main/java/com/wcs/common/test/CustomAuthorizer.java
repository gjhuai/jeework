/** * ShiroRealm.java 
* Created on 2011-11-28 上午9:44:12 
*/

package com.wcs.common.test;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.wcs.common.model.Permission;
import com.wcs.common.model.Role;
import com.wcs.common.model.User;
import com.wcs.common.service.permissions.LoginService;

/** 
* <p>Project: btcbase</p> 
* <p>Title: ShiroRealm.java</p> 
* <p>Description: </p> 
* <p>Copyright: Copyright All rights reserved.</p> 
* <p>Company: wcs.com</p> 
* @author <a href="mailto:yujingu@wcs-global.com">Yu JinGu</a> 
*/
@SuppressWarnings("serial")
public class CustomAuthorizer extends AuthorizingRealm implements Serializable {   
    private BeanManager beanManager;
    
    private LoginService loginService;
    
    public CustomAuthorizer() throws NamingException {
       this.beanManager = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
    }
    
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = principals.fromRealm(getName()).iterator().next().toString();
        loginService = this.getBean(LoginService.class);
        User user = loginService.findUniqueUser(loginName);
        if (user != null) {
           SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
           List<Role> roles = loginService.findAllRoleOfUser(user);
           for (Role role : roles) {
               List<Permission> permissions = loginService.findAllPermissionByRole(role);
               for (Permission permission : permissions) {
                   info.addStringPermission(permission.getPermission().toString());
               }
           }
           
           return info;
        } else {
            return null;
        }
    }

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        loginService = this.getBean(LoginService.class);
        User user = loginService.findUniqueUser(token.getUsername());
        if (user != null) {
            return new SimpleAuthenticationInfo(user.getLoginName(), user.getUserPass(), getName());
        } else {
            return null;
        }
    }
    
    private <T> T getBean(Class<? extends T> clazz) {
    	  @SuppressWarnings("unchecked")
    	  Bean<T> bean = (Bean<T>) beanManager.getBeans(clazz).iterator().next();
    	  CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
    	  @SuppressWarnings("unchecked")
    	  T obj = (T) beanManager.getReference(bean, clazz, ctx);
    	  return obj;
   }

}
