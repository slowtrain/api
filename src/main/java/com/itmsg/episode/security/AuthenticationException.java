package com.itmsg.episode.security;

public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = -2936171366301475971L;

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
