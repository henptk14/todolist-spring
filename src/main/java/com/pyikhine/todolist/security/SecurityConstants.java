package com.pyikhine.todolist.security;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/api/users/**";
    public static final String SECRET = "TODOBoeingJwt";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 600_000; // 10 minutes
}
