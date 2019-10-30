package com.ryulth.jwtexample.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginDto {
    protected LoginDto(){}
    private String email;
    private String password;
}

