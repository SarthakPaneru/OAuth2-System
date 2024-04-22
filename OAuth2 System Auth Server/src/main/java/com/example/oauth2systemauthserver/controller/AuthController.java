package com.example.oauth2systemauthserver.controller;

import com.example.oauth2systemauthserver.helper.UserRegistrationDto;
import com.example.oauth2systemauthserver.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDto userDto) {
        return new ResponseEntity<>(authService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("secret"));
        return bCryptPasswordEncoder.encode("secret");
    }
}
