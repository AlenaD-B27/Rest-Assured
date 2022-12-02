package com.cydeo.day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_NegativeSpartanTests {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI="http://34.204.7.162:8000";
    }

    /*
     * Given accept  content type is application/json
     * When user sends GET request /api/spartans endpoint
     * Then status code should be 200
     */
    @DisplayName("GET All Spartans")
    @Test
    public void getAllSpartans() {

        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/spartans");

        // how to get status code
        int actualStatusCode = response.statusCode();

        assertEquals(200, actualStatusCode);

    }

    @Test
    public void test2(){
        /*
        Given Accept type application/xml
        When user send GET request to /api/spartans/10 end point
        Then status code must be 406
        And response Content Type must be application/xml;charset=UTF-8;
    */

        Response response = given().accept(ContentType.XML).when().get("/api/spartans/10");
        assertEquals(406, response.statusCode());
        assertEquals("application/xml;charset=UTF-8", response.contentType());




    }




}
