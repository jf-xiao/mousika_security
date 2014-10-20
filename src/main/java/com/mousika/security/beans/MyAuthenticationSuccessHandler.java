package com.mousika.security.beans;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * loging success handler
 * @author xiaojf 294825811@qq.com
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        System.out.println("login success -->");
        System.out.println("j_username : "+request.getParameter("j_username"));
        System.out.println("j_password : "+request.getParameter("j_password"));
    }

}
