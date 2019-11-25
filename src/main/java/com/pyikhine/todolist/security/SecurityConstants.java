package com.pyikhine.todolist.security;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/api/users/**";
    public static final String SECRET = "TODOBoeingJwt";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final int TOKEN_EXPIRATION_TIME = 1200000; // 20 minutes
}
