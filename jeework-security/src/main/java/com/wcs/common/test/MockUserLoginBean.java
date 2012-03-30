package com.wcs.common.test;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wcs.common.controller.permissions.LoginBean;

@Named
@ConversationScoped
public class MockUserLoginBean implements Serializable {
    //@Inject
    //private UserService userService;
    @Inject
    private LoginBean loginBean;

    /**
     *  设置登录用户方法：  修改MockUserLoginBean代码，  User user = userService.findUniqueUser("你的用户名");
     * @param session
     */
//    public void observeSessionInitialized(@Observes @Initialized HttpSession session) {
//        System.out.println("LoginBean ==> observeSessionInitialized() ");
//        User user = userService.findUniqueUser("admin");    // 修改成你的登录用户名
//        try {
//			loginBean.initUserResource(user);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}