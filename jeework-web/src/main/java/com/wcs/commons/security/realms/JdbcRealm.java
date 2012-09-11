package com.wcs.commons.security.realms;

import java.io.Serializable;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.wcs.base.util.ContainerUtils;
import com.wcs.commons.conf.WebappConfig;
import com.wcs.commons.security.model.Role;
import com.wcs.commons.security.model.RoleResource;
import com.wcs.commons.security.model.User;
import com.wcs.commons.security.service.UserService;

/**
 * <p>Project: btcbase-web</p> 
 * <p>Title: </p> 
 * <p>Description: </p> 
 * <p>Copyright: Copyright 2011-2020.All rights reserved.</p> 
 * <p>Company: wcs.com</p> 
 * @author guanjianghuai
 */
@SuppressWarnings("serial")
public class JdbcRealm extends AuthorizingRealm implements Serializable {
	
	private UserService userService;

	/**
	 * 认证回调函数, 登录时调用
	 * 1.解析得到用户名/密码
	 * 2.校验数据库中是否存在此用户/密码
	 * 3.将用户信息存入session中
	 */
	@Override
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// 获取界面传递过来的认证信息（用户名/密码）
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 校验认证信息的合法性
		userService = ContainerUtils.getBean(UserService.class);
		User user = userService.findUser(token.getUsername()); //adAccount
		
		if (user==null) return null;
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute(WebappConfig.SESSION_CURRENT_USER, user);	// 包含User,Person两个实体的信息
		
		return new SimpleAuthenticationInfo(token.getUsername(), "", getName()); //密码为空;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String adAccount = principals.fromRealm(getName()).iterator().next().toString();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		// 查询登录用户的授权资源列表
		userService = ContainerUtils.getBean(UserService.class);
		
		List<Role> roles = userService.findRoles(adAccount);
		
		for (Role role : roles){
			info.addRole(role.getCode());    // 定义 Role check
			List<RoleResource> permissions = userService.findResources(role.getId());
			for (RoleResource p : permissions){
				info.addStringPermission(p.getCode());  // 定义 Permission check
			}
		}
		
		return info;
	}

}
