package com.cydeo.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SimpleGetRequest {

    String url = "http://34.204.7.162:8000/api/spartans";

    /**
     * When users end request to /api/spartans endpoint
     * Then user should be able see status code is 200
     * And Print out response body into screen
     */

    @Test
    public void simpleGetRequest() {

        Response response = RestAssured.get(url);

        System.out.println("response.getStatusCode() = " + response.getStatusCode());
        System.out.println("response.statusCode() = " + response.statusCode());

        System.out.println("response.getStatusLine() = " + response.getStatusLine());

        int actualStatusCode = response.statusCode();

        Assertions.assertEquals(200, actualStatusCode);

        response.prettyPrint();

    }

}
