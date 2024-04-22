package com.example.oauth2systemresourceserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/")
    public ResponseEntity<?> hello() {
        return new ResponseEntity<>("Works fine", HttpStatus.OK);
    }
}
