package com.mousika.security.beans;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * login failure handler
 * @author xiaojf 294825811@qq.com
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        System.out.println("login failure -->");
        System.out.println("j_username : "+request.getParameter("j_username"));
        System.out.println("j_password : "+request.getParameter("j_password"));
    }

}
