package com.example.oauth2system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        System.out.println("Hello User: " + principal.getName());
        return new ResponseEntity<>("Hello User: " + principal.getAttributes().get("name"), HttpStatus.OK);
    }

    @GetMapping("/tokens")
    public String getTokens(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                            @AuthenticationPrincipal OAuth2AuthenticationToken authentication) {
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();

        String accessTokenValue = accessToken.getTokenValue();
        String refreshTokenValue = refreshToken != null ? refreshToken.getTokenValue() : "No Refresh Token";

        return "Access Token: " + accessTokenValue + "<br>Refresh Token: " + refreshTokenValue;
    }
}
