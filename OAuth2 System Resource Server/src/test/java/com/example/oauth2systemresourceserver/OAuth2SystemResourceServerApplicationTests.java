package com.example.oauth2systemresourceserver;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.restassured.RestAssured;

//@SpringBootTest
class OAuth2SystemResourceServerApplicationTests {

    private static final String authorizationServerUrl = "http://localhost:9000/";
    private static final String resourceServerUrl = "http://localhost:8080/";
    private static final String tokenEndpointUrl = "http://localhost:9000/oauth2/token";
    private static final String clientId = "oidc-client";
    private static final String clientSecret = "secret";
    private static final String username = "hello@example.com";
    private static final String password = "password";
    private static final String redirectUri = "http://127.0.0.1:8080/authorized";
    private static String authorizationCode;
    private static String accessToken;

    @BeforeAll
    public static void getAccessToken() throws Exception {
        authorizationCode = "6GSA7gfTJfEfA4y-Wut1dLQFh2e1CSo5GFMXo11xSnOQHDuYuVtHfWjSadbp4YOMhfwSsPhm9Wuf-1I78VeGpBvVEDQncfLYW0vhKAdv8jDSVn1_lqwlYF3DRTHOR8bp";

        // Exchange authorization code for access token
        accessToken = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "authorization_code")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("code", authorizationCode)
                .formParam("redirect_uri", redirectUri)
                .and()
                .auth()
                .preemptive()
                .basic(clientId, clientSecret)
                .post(tokenEndpointUrl)
                .then()
                .statusCode(200)
                .extract().path("access_token");

        System.out.println("Access token: " + accessToken);

    }

    @Test
    public void testValidAccessToken() {
        System.out.println("Access token: " + accessToken);
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .get(resourceServerUrl)
                .then()
                .statusCode(200)
                .and()
                .extract().body().asString();
    }

    @Test
    public void testInvalidAccessToken() {
        RestAssured.given()
                .header("Authorization", "Invalid Bearer Token")
                .get(resourceServerUrl)
                .then()
                .statusCode(401)
                .and()
                .extract().body().asString();
    }
}
