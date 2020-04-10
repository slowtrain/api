package com.itmsg.episode.security;

import io.jsonwebtoken.ExpiredJwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itmsg.episode.config.WebSecurityConfig;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.util.CookieUtil;

import java.io.IOException;

@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;


    public JwtAuthorizationTokenFilter(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, @Value("${jwt.header}") String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());
        
        Cookie authToken = CookieUtil.getCookie(request,WebSecurityConfig.JWT_HEADER);
        String username = (authToken!=null)?jwtTokenUtil.getUsernameFromToken(authToken.getValue()):null;
      
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("security context was null, so authorizing user '{}'",username);

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)            
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }

            boolean tokenAvailable =  jwtTokenUtil.validateToken(authToken.getValue(), userDetails);
            logger.info("checking token avaliable '{}' ", tokenAvailable);
            
            if (tokenAvailable) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authorized user '{}', setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute(ESConstants.USER_DETAILS_KEY, userDetails);
                request.setAttribute(ESConstants.AUTH_TOKEN, authToken);

                extendToken(request, response, authToken.getValue(), userDetails);
            }
        }

        chain.doFilter(request, response);
    }

    private void extendToken(HttpServletRequest request, HttpServletResponse response, String authToken, UserDetails userDetails){
        String newToken = jwtTokenUtil.refreshToken(authToken);
        Cookie tokenCookie = CookieUtil.createCookie(WebSecurityConfig.JWT_HEADER, newToken, null, "/", WebSecurityConfig.JWT_EXPIRATION, true);
        Cookie authUserCookie = CookieUtil.createCookie(WebSecurityConfig.AUTHORIZATION_USER, userDetails.getUsername(), null, "/", WebSecurityConfig.JWT_EXPIRATION, false);
        
        response.addCookie(tokenCookie);
        response.addCookie(authUserCookie);

        logger.debug("Authentication token has been extended to "+jwtTokenUtil.getExpirationDateFromToken(newToken));
        
    }
}
