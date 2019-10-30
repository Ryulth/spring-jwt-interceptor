package com.ryulth.jwtexample;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @GetMapping("/")
    public ResponseEntity<String> getTest(){
        return new ResponseEntity<>("test", httpHeaders, HttpStatus.OK);
    }

}
