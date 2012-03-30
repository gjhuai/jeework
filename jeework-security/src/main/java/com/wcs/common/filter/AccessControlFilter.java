package com.wcs.common.filter;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wcs.common.model.User;

public class AccessControlFilter implements Filter
{
    public void destroy()
    {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String requestUrl = request.getServletPath();

        String contextPath = request.getContextPath();

        HttpSession session = request.getSession(false);

        // mock user_name
        session = request.getSession();
        session.setAttribute("USER", "1234");
       // initSystem(session);
        /*
         * new ContextualHttpServletRequest(){ public void process() { // Do you work }
         * 
         * }.run();
         */
        /*if (session.getAttribute("USER") == null) {
            if (!requestUrl.contains("/login.xhtml")) {
                response.sendRedirect(contextPath + "/login.xhtml");
                return;
            }
        } else {
            if (requestUrl.contains("/login.xhtml")) {
                // 显示登录用户信息
                LoginBean loginBean = this.getBeanObject();
                String str = loginBean.userLogin();
                response.sendRedirect(contextPath + "/login.xhtml");
                return;
            }
        }

        chain.doFilter(request, response);*/
        session.setAttribute("LOGINED_USER_NAME","qq");

        if (session == null || session.getAttribute("LOGINED_USER_NAME") == null) {
            if (!requestUrl.contains("/login.xhtml")){
                response.sendRedirect(contextPath +  "/login.xhtml");
                return;
            }
        }else{
            if (requestUrl.contains("/login.xhtml")) {
                // 显示登录用户信息
                //response.sendRedirect(contextPath +  "/faces/user/view.xhtml");
                //return;
            }
        }

        chain.doFilter(request, response);


    }

    public void initSystem(HttpSession session)
    {
        String sql = "select * from USER where NAME='chen' and USER_NAME='123' ";
        EntityManagerFactory entityfactory = Persistence.createEntityManagerFactory("pu");
        EntityManager entityManger = entityfactory.createEntityManager();
        entityManger.getTransaction().begin();
        List userlist = entityManger.createNativeQuery(sql).getResultList();
        System.out.println(userlist);
        System.out.println(userlist.get(0));
        Object[] obj = (Object[]) userlist.get(0);
        User u = new User();
        if (obj != null) {

            u.setId(Long.parseLong(String.valueOf(obj[0])));
            u.setUserName(String.valueOf(obj[10]));
            u.setUserName(String.valueOf(obj[13]));
        }
        entityManger.getTransaction().commit();
        entityManger.close();
        entityfactory.close();
        session.setAttribute("USER", u);

    }

    

    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

}
