package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanWithPathParam extends SpartanTestBase {

    /*   Given accept type is Json
          And Id parameter value is 24
          When user sends GET request to /api/spartans/{id}
          Then response status code should be 200
          And response content-type: application/json
          And "Julio" should be in response payload(body)
       */

    @DisplayName("GET Spartan /api/spartans/{id} path param with {id} 24")
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 24)
                .when().get("/api/spartans/{id}");

        response.prettyPrint();

        assertEquals(200,response.statusCode());
        assertEquals("application/json",response.contentType());
        assertTrue(response.body().asString().contains("Julio"));


    }

    /*
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json
        And "Not Found" message should be in response payload
     */

    @DisplayName("GET Spartan /api/spartans/{id} path param with invalid id")
    @Test
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get("/api/spartans/{id}");

        assertEquals(404, response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        assertTrue(response.body().asString().contains("Not Found"));

    }







}
