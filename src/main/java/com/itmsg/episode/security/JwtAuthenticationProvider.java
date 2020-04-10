package com.itmsg.episode.security;

import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.security.JwtUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        String userId = (String)authentication.getPrincipal();   
        String userPw = (String)authentication.getCredentials();

        UserDetails userDetails = (UserDetails)userDetailsService.loadUserByUsername(userId);

        if(! passwordEncoder.matches(passwordEncoder.encode(userPw), userDetails.getPassword())){
            throw new BadCredentialsException("Bad credentials !!!!");
        }
        return new UsernamePasswordAuthenticationToken(userId, userPw);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}