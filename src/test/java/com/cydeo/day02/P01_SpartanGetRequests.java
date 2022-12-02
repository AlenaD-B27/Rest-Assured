package com.cydeo.day02;



import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class P01_SpartanGetRequests {
    String url = "http://34.204.7.162:8000";

    /*
     * Given accept is application/json
     * When user sends GET request /api/spartans endpoint
     * Then status code should be 200
     * And Content type should be application/json
     */

    @Test
    public void getAllSpartans(){
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when().get(url + "/api/spartans");

        // print response
        // response.prettyPrint();

        // get status code
        int actualStatusCode = response.statusCode();

        Assertions.assertEquals(200, actualStatusCode);

        // get content type
        String actualContentType = response.contentType();

        Assertions.assertEquals("application/json", actualContentType);

        // get header info
        String connection = response.header("Connection");
        System.out.println("connection = " + connection);

        // get content type with header
        System.out.println("response.header(\"Content-Type\") = " + response.header("Content-Type"));


        // can we get connection() same as contentType() insteading of using header?
        // A --> Rest Assured created couple of method for common usage.
        // statusCode() contentType() methods are specificly created by them.So there is connection method


        // get date header
        System.out.println("response.header(\"Date\") = " + response.header("Date"));


        //how can we verify date is exist ?
        boolean isDateExist = response.headers().hasHeaderWithName("Date");


        Assertions.assertTrue(isDateExist);

    }


    /*
     * Given accept  content type is application/json
     * When user sends GET request /api/spartans/3 endpoint
     * Then status code should be 200
     * And Content type should be application/json
     * And response body needs to contains Fidole
     */

    @DisplayName("GET ALL SPARTANS")
    @Test
    public void getSpartan(){

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when().get(url + "/api/spartans/3");

        // Verify status code
        Assertions.assertEquals(200, response.statusCode());

        // Verify contentType is application json
        Assertions.assertEquals(ContentType.JSON.toString(), response.header("Content-Type"));

        // Verify body contains Fidole
        Assertions.assertTrue(response.body().asString().contains("Fidole"));

    }

    @DisplayName("Get Hello Spartan")
    @Test
    public void helloSpartan(){
        /*
        Given no headers provided
        When Users send GET request to /api/hello
        Then response status code should be 200
        And Content type header should be "text/plain;charset=UTF-8"
        And header should contain Date
        And Content-Length should be 17
        And body should be "Hello from Sparta"
    */

        Response response = RestAssured.when().get(url + "/api/hello");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("text/plain;charset=UTF-8", response.contentType());
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));
        Assertions.assertEquals("17", response.header("Content-Length"));
        Assertions.assertTrue(response.body().asString().contains("Hello from Sparta"));


    }








}
