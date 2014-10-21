/**
 * 
 */
package com.mousika.security.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @author xiaojf 294825811@qq.com
 */
public class IPTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    private static final ThreadLocal<HttpServletRequest> requestContext = new ThreadLocal<HttpServletRequest>();
    
    /**
     * @deprecated Use with-args constructor
     */
    @Deprecated
    public IPTokenBasedRememberMeServices() {
    }
    
    public IPTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices#onLoginSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication successfulAuthentication) {
        try {
            this.setRequestcontext(request);
            super.onLoginSuccess(request, response, successfulAuthentication);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.setRequestcontext(null);
        }
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices#makeTokenSignature(long, java.lang.String, java.lang.String)
     */
    @Override
    protected String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        String data = username + ":" + tokenExpiryTime + ":" + password + ":" + getKey()+":"+this.getIPAddress(requestContext.get());
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(data.getBytes())));
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices#setCookie(java.lang.String[], int, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        String[] ipTokens = Arrays.copyOf(tokens, tokens.length+1);
        ipTokens[ipTokens.length - 1] = this.getIPAddress(requestContext.get());
        super.setCookie(ipTokens, maxAge, request, response);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices#processAutoLoginCookie(java.lang.String[], javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
            HttpServletResponse response) {
        this.setRequestcontext(request);
        String localIpAddress = cookieTokens[cookieTokens.length - 1];
        
        if(! localIpAddress.equals(this.getIPAddress(requestContext.get()))){
            throw new InvalidCookieException("Cookie IP Address did not contain a matching IP (contained '" + localIpAddress + "')");
        }
        
        return super.processAutoLoginCookie(Arrays.copyOf(cookieTokens, cookieTokens.length - 1), request, response);
    }

    public static ThreadLocal<HttpServletRequest> getRequestContext() {
        return requestContext;
    }
    
    public void setRequestcontext(HttpServletRequest requestContext) {
        this.requestContext.set(requestContext);
    }
    
    public String getIPAddress(HttpServletRequest request) {
        return request.getLocalAddr();
    }
}
