package com.itmsg.episode.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.itmsg.episode.app.system.user.User;
import com.itmsg.episode.app.system.user.UserAuthority;
import com.itmsg.episode.app.system.user.UserMapper;
import com.itmsg.episode.security.JwtUserFactory;
import com.itmsg.episode.app.system.user.UserRepository;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else if (!"ACTIVE".equals(user.getStatus())) {
            throw new UsernameNotFoundException(String.format("UnauthorizedUser with username '%s'.", username));
        } else {
            // return JwtUserFactory.create(user);
            return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getFirstname(),
                    user.getLastname(), user.getOrgId(), user.getEmail(), mapToGrantedAuthorities(user),
                    user.getStatus(), user.getPasswordResetDt());
        }
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(User user) {
        List<UserAuthority> userAuthorities = userMapper.getUserAuthorities(user);
        return userAuthorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthorityId()))
                .collect(Collectors.toList());
    }
}
