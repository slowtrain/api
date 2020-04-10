package com.itmsg.episode.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.itmsg.episode.app.system.user.UserAuthority;
import com.itmsg.episode.app.system.user.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }
    /*
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getStatus(),
                user.getPasswordResetDt()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserAuthority> userAuthorities) {
        return userAuthorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityId()))
                .collect(Collectors.toList());
    }
    */
}
