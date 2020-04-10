package com.itmsg.episode.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /*
     * public JwtAuthenticationSuccessHandler(String defaultTargetUrl) {
     * setDefaultTargetUrl(defaultTargetUrl); }
     */

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;




    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {
        System.out.println("@@@@@@ JwtAuthenticationSuccessHandler.onAuthenticationSuccess()");
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        String token =jwtTokenUtil.generateToken(userDetails);
       

        
              
    }
}