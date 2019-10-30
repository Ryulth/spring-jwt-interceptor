package com.ryulth.jwtexample.security;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message){
        super(message);
    }
}
