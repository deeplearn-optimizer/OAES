package com.example.org.controller.test;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AuthenticationTest {
    private final String username = "mock_username";
    private final String password = "mock_password";

    public String get_response_from(String url) throws Exception {
        URL location = new URL(url);
        URLConnection yc = location.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        String response = "";
        while ((inputLine = in.readLine()) != null)
            response = response + inputLine;
        in.close();
        return response;
    }

    @Test
    public void testRegisterWithValidParameters() throws Exception {

        /* register a mock user */
        String url = "http://localhost:8080/REST_example/api/users/register?";
        String params = "username=" + username;
        params += "&password=" + password;
        params += "&role=" + "student";
        url += params;
        String response = get_response_from(url);
        Assert.assertEquals(response, "200 OK");

        /* register with same credentials again */
        url = "http://localhost:8080/REST_example/api/users/register?";
        params = "username=" + username;
        params += "&password=" + password;
        params += "&role=" + "student";
        url += params;
        try {
            response = get_response_from(url);
            Assert.assertNotEquals(response, "200 OK");
        }catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("403"));
        }

        /* delete the registered user */
        url = "http://localhost:8080/REST_example/api/users/delete?";
        params = "username=" + username;
        params += "&password=" + password;
        url += params;
        response = get_response_from(url);
        Assert.assertEquals(response, "200 OK");
    }

    @Test
    public void testRegisterWithInvalidParameters() throws Exception {

        String url = "http://localhost:8080/REST_example/api/users/register?";
        String params = "username=" + "mock";
        params += "&password=" + password;
        params += "&role=" + "authority";
        url += params;
        try {
            String response = get_response_from(url);
            Assert.assertNotEquals(response, "200 Ok");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("403"));
        }

    }

    @Test
    public void testAuthentication() throws Exception {

        String url = "http://localhost:8080/REST_example/api/users/register?";
        String params = "username=" + username;
        params += "&password=" + password;
        params += "&role=" + "authority";
        url += params;
        String response = get_response_from(url);
        Assert.assertEquals(response, "200 OK");

        url = "http://localhost:8080/REST_example/api/users/authToken?";
        params = "username=" + username;
        params += "&password=" + password;
        url += params;
        String token = get_response_from(url);

        String encodedToken = URLEncoder.encode(token);
        url = "http://localhost:8080/REST_example/api/users/auth?";
        params = "token=" + encodedToken;
        url += params;
        response = get_response_from(url);
        Assert.assertEquals(response, "200 OK");

        url = "http://localhost:8080/REST_example/api/users/delete?";
        params = "username=" + username;
        params += "&password=" + password;
        url += params;
        response = get_response_from(url);
        Assert.assertEquals(response, "200 OK");

    }

    @Test
    public void testWithUsernameEmpty() throws Exception {

        /* register a mock user */
        String url = "http://localhost:8080/REST_example/api/users/register?";
        String params = "username=" + username;
        params += "&password=" + password;
        params += "&role=" + "student";
        url += params;
        String response = get_response_from(url);
        Assert.assertEquals(response, "200 OK");



    }
}
