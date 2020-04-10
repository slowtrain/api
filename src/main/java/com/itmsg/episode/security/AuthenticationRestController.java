package com.itmsg.episode.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itmsg.episode.app.system.application.AuthorityMapper;
import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserMapper;
import com.itmsg.episode.app.system.user.UserRepository;
import com.itmsg.episode.config.WebSecurityConfig;
import com.itmsg.episode.security.JwtAuthenticationRequest;
import com.itmsg.episode.security.JwtTokenUtil;
import com.itmsg.episode.security.JwtUser;
import com.itmsg.episode.system.ESConstants;
import com.itmsg.episode.util.CookieUtil;
import com.itmsg.episode.security.JwtAuthenticationResponse;

@RestController
public class AuthenticationRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TextEncryptor textEncryptor;

    @RequestMapping(value = WebSecurityConfig.JWT_ROUTE_AUTHENTICATION_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletRequest req, HttpServletResponse response) throws AuthenticationException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        String username = authenticationRequest.getUsername();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Cookie tokenCookie = CookieUtil.createCookie(WebSecurityConfig.JWT_HEADER, jwtTokenUtil.generateToken(userDetails), null, "/", WebSecurityConfig.JWT_EXPIRATION, true);
        Cookie authUserCookie = CookieUtil.createCookie(WebSecurityConfig.AUTHORIZATION_USER, username, null, "/", WebSecurityConfig.JWT_EXPIRATION, false);
        
        response.addCookie(tokenCookie);
        response.addCookie(authUserCookie);
        
        Map<String,Object> map = new HashMap<String,Object>();
        User user = userRepository.findByUsername(username);
        user.setFirstname(textEncryptor.decrypt(user.getFirstname()));
        user.setEmail(textEncryptor.decrypt(user.getEmail()));
        user.setMobile(textEncryptor.decrypt(user.getMobile()));
        map.put("user",user);
        map.put("apps",authorityMapper.userAuthApp(user));
        map.put("auth",userMapper.getUserAuthorities(user));
        return ResponseEntity.ok().body(map);
    }

    @RequestMapping(value = WebSecurityConfig.JWT_ROUTE_AUTHENTICATION_SIGNOUT, method = RequestMethod.POST)
    public ResponseEntity<?> signout(HttpServletRequest request,HttpServletResponse response){
        request.setAttribute(ESConstants.AUTH_TOKEN,null);
        request.setAttribute(ESConstants.USER_DETAILS_KEY,null);
        Cookie tokenCookie = CookieUtil.createCookie(WebSecurityConfig.JWT_HEADER,"", null, "/", 0, true);
        Cookie authUserCookie = CookieUtil.createCookie(WebSecurityConfig.AUTHORIZATION_USER,"", null, "/", 0, true);         
        response.addCookie(tokenCookie);
        response.addCookie(authUserCookie);         
        return ResponseEntity.ok().build();
    }

    
    @RequestMapping(value = WebSecurityConfig.JWT_ROUTE_AUTHENTICATION_REFRESH, method = RequestMethod.POST)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        
        String authToken = null;
        Cookie[] cookies=request.getCookies();
        if(cookies!=null) {
            Optional<Cookie> option = Arrays.stream(cookies).filter(cookie -> {
                return WebSecurityConfig.JWT_HEADER.equals(cookie.getName());
            }).findFirst();
            if (option.isPresent()) {
                authToken = option.get().getValue();
            }
        }

        if(authToken==null){
            logger.debug("token not exists");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username=jwtTokenUtil.getUsernameFromToken(authToken);
        if(username==null){
            logger.debug("can not find user from token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            logger.debug("can not find user by username");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if( !jwtTokenUtil.validateToken(authToken, userDetails)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Cookie tokenCookie = CookieUtil.createCookie(WebSecurityConfig.JWT_HEADER, jwtTokenUtil.refreshToken(authToken), null, "/", WebSecurityConfig.JWT_EXPIRATION, true);
        Cookie authUserCookie = CookieUtil.createCookie(WebSecurityConfig.AUTHORIZATION_USER, username, null, "/", WebSecurityConfig.JWT_EXPIRATION, false);
        
        response.addCookie(tokenCookie);
        response.addCookie(authUserCookie);
        
        Map<String,Object> map = new HashMap<String,Object>();
        User user = userRepository.findByUsername(username);
        user.setFirstname(textEncryptor.decrypt(user.getFirstname()));
        user.setEmail(textEncryptor.decrypt(user.getEmail()));
        user.setMobile(textEncryptor.decrypt(user.getMobile()));
        map.put("user",user);
        map.put("apps",authorityMapper.userAuthApp(user));
        map.put("auth",userMapper.getUserAuthorities(user));
        return ResponseEntity.ok().body(map);
        
        
    }

    
    
    /*
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    */


    @GetMapping(value = WebSecurityConfig.JWT_ROUTE_AUTHENTICATION_USER)
    public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request) {
        String authToken = null;

        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(WebSecurityConfig.JWT_HEADER.equals(cookie.getName())){
                    authToken=cookie.getValue();
                    break;
                }
            }
        }

        if(authToken==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You do not have token");
        }

        JwtUser user = (JwtUser)userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(authToken));
        return ResponseEntity.ok().body(user);
    }
    

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
}
