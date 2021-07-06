package com.example.demo.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ClassName:LoginInterceptor
 * Package:com.example.demo.config
 * Description:
 *
 * @Author:HP
 * @date:2021/7/6 8:39
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session != null && session.getAttribute(AppFinal.USERINFO_SESSIONKEY) != null) {
            return true;
        }
        return false;
     }
}
