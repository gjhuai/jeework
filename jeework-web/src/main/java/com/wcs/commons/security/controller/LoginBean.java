package com.wcs.commons.security.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.util.JSFUtils;
import com.wcs.base.util.MessageUtils;
import com.wcs.commons.conf.WebappConfig;

/**
 * 
 * @author Chris Guan
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(LoginBean.class);

	private final String LOGIN_SUCCESS = "/faces/main.xhtml";
	private final String LOGIN_PAGE = "/faces/login.xhtml";

	
	public String login() {
		// 用户认证
		String adAccount = JSFUtils.getRequestParam("adAccount");
		
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(adAccount, "");  //?密码为空？
		token.setRememberMe(true);
		try {
			currentUser.login(token);
		} catch (AuthenticationException ae) {
			logger.debug(ae.getMessage());
			currentUser.getSession().removeAttribute(WebappConfig.SESSION_CURRENT_USER); // remove 掉user session 信息
			MessageUtils.addErrorMessage("longmessgeId", "用户或密码无效！");
			return LOGIN_PAGE;
		}

		// 设置登录后默认的选中菜单为"Demos"
		JSFUtils.getSession().put(WebappConfig.SESSION_NEXT_DISPLAY_RES_CODE, "base:demos"); 

		return LOGIN_SUCCESS;
	}

    /**
	 * 注销用户
	 */
	public String logout() {
		SecurityUtils.getSubject().logout();	// 关闭Session
		return LOGIN_PAGE;
	}

	/**
	 * 改变导航菜单
	 * @param selectId
	 * @return
	 */
	public String select(String selectedResCode, String uri) {
		logger.debug("select({},{})",selectedResCode,uri);
		JSFUtils.getSession().put(WebappConfig.SESSION_NEXT_DISPLAY_RES_CODE, selectedResCode);
		return uri;
	}
}
