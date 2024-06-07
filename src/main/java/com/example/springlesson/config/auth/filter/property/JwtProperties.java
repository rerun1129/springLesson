package com.example.springlesson.config.auth.filter.property;

import org.apache.tomcat.util.http.parser.Authorization;

public enum JwtProperties {
    SECRET("salt"), EXPIRATION_TIME(86400000), TOKEN_PREFIX("Bearer "), HEADER_STRING("Authorization");
    private String str;
    private long milliseconds;

    JwtProperties ( String str ) {
        this.str = str;
    }

    JwtProperties ( long milliseconds ) {
        this.milliseconds = milliseconds;
    }

    public String getStr ( ) {
        return str;
    }

    public long getMilliseconds ( ) {
        return milliseconds;
    }
}
