package com.hrl.blog.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterCeptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果session为空，表示没有登陆，重定向到登录页面
        if(request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;

        }
        return true;
    }
}
