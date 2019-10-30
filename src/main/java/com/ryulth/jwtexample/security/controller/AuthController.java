package com.ryulth.jwtexample.security.controller;

import com.ryulth.jwtexample.security.dto.LoginDto;
import com.ryulth.jwtexample.security.dto.TokenDto;
import com.ryulth.jwtexample.security.dto.UserDto;
import com.ryulth.jwtexample.security.service.TokenService;
import com.ryulth.jwtexample.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        log.info("login payload :" + loginDto.getEmail());

        return new ResponseEntity<>(tokenService.publishToken(loginDto.getEmail()), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody UserDto userDto) {
        log.info("register payload :" + userDto.getEmail());
        if(userService.registerUser(userDto)) {
            return new ResponseEntity<>(tokenService.publishToken(userDto.getEmail()), httpHeaders, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(TokenDto.builder().build(), httpHeaders, HttpStatus.FORBIDDEN);
        }
    }
}
