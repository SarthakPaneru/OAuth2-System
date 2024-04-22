Welcome to the OAuth2-System wiki!

# OAuth2 System

Server Types:
Authorization Server
Resource Server
Simple Server which uses Google OAuth SSO

![OAuth2 System Overview](https://github.com/SarthakPaneru/OAuth2-System/blob/main/Screenshots/OAuth2%20System.drawio.png)

Here is a simple system workflow of OAuth2

## Authorization Server

* Auth Server runs in port 9000.
* Dependency used to make auth server: **spring-boot-starter-oauth2-authorization-server**
* A dynamic auth server which lets you create users and login using mysql as a database.
* Authentication method used: CLIENT_SECRET_BASIC
> * In this method, the system/resource server has to provide client-id and client-secret in addition to the user's username and password.
> * It is a more secure and modern way of communication between two servers.
* A strong RSA key is used of key size 2048 bits to initialize JWT token.
* The passwords are safely stored in database by encrypting it with bcrytp password encoder.


## Resource Server

* Resource server runs in port 8080.
* Dependency used to make resource server: **spring-boot-starter-oauth2-resource-server**
* It contains only a single API/endpoint/controller which simply returns a string if the request is successful.
* It points its jwt issuer to auth-server i.e. above server. So it only authenticates and provides access to those users which have valid access tokens available from the Auth server.
* It declines every other request if the token is invalidated by auth server.

## OAuth2 using google
* Used google API to implement SSO using google.
* Use client-id and client-secret which is available in application-dev.properties that is not available in github as github strictly prohibited publicly uploading confidential keys.
* The application provides access token but not refresh token as to get it  the users has to provide consent every time which feels unnecessary.
> * We can use access_type=offline&prompt=consent instead to generate refresh token.
![Google OAuth2](https://github.com/SarthakPaneru/OAuth2-System/blob/main/Screenshots/Get%20login%20username%20from%20google.png)

## TEST CASE
I have written tests in Resource server which get access token from auth-server and use the token to access protected resource in resource server. As seen in screenshots provided the test case has both passed with a valid access token that gets 200 status code while invalid access token gets 401 status code.

The test case can be viewed in: 
`{RESOURCE_SERVER}/src/test/java/com/example/oauth2systemresourceserver/OAuth2SystemResourceServerApplicationTests.java`

To run the test case you must first get authorization code.

Step to get Authorization code:
* First run authorization server then resource server.
* Hit the following link in a browser:
> `http://127.0.0.1:9000/oauth2/authorize?response_type=code&client_id=oidc-client&redirect_uri=http://127.0.0.1:8080/authorized&scope=openid`

* The browser then redirects you to the login page, where you can provide your username and password to get a code.
> `http://127.0.0.1:8080/authorized?code=6GSA7gfTJfEfA4y-Wut1dLQFh2e1CSo5GFMXo11xSnOQHDuYuVtHfWjSadbp4YOMhfwSsPhm9Wuf-1I78VeGpBvVEDQncfLYW0vhKAdv8jDSVn1_lqwlYF3DRTHOR8bp`

* Paste the code after ?code= to the authorization code string.
* Then run the test cases and validate the code/server.
![Test Case](https://github.com/SarthakPaneru/OAuth2-System/blob/main/Screenshots/test_cases.png)

## FLOW
* Run both Auth-server and resource server
* Use the following code and run it in browser to get authorization code after the server redirects you to login page in auth-server
  `127.0.0.1:9000/oauth2/authorize?response_type=code&client_id=oidc-client&redirect_uri=http://127.0.0.1:8080/authorized&scope=openid`
* Using the provided authorization_code by hitting POST request to "localhost:9000/oauth2/token" and passing client-id and client-secret as Basic Auth.
![Get Token from authorization code](https://github.com/SarthakPaneru/OAuth2-System/blob/main/Screenshots/Get%20Access%20token%20and%20refresh%20token%20from%20authorization%20code.png)

* You may now pass the token to the resource server to get access to the server.
![Get Access to resource server after valid access token](https://github.com/SarthakPaneru/OAuth2-System/blob/main/Screenshots/Get%20access%20to%20resource%20server%20using%20valid%20access%20token.png)

* If the access token gets expired you can generate new access token from refresh token from the same url but the only difference is grant_type is refresh token now.
![Get access and refresh token from refresh token](https://github.com/SarthakPaneru/OAuth2-System/blob/main/Screenshots/Get%20Access%20token%20and%20refresh%20token%20from%20refresh%20token.png)
