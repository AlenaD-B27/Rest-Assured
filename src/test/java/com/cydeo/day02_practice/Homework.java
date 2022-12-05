package com.cydeo.day02_practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Homework {
    /*
Q1:
- Given accept type is Json
- When users send request to /countries/US
- Then status code is 200
- And Content - Type is application/json
- And response contains United States of America


Q2:
- Given accept type is Json
- When users sends request to /employees/1
- Then status code is 404

Q3:
- Given accept type is Json
- When users sends request to /regions/1
- Then status code is 200
- And Content - Type is application/json
- And response contains Europe
- And header should contains Date
- And Transfer-Encoding should be chunked
     */

    @BeforeAll
    public static void init(){
        RestAssured.baseURI="http://34.204.7.162:1000/ords/hr";
    }


    @Test
    public void Q1(){

//        - Given accept type is Json
//        - When users send request to /countries/US
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("/countries/US");

//        - Then status code is 200
        Assertions.assertEquals(200, response.statusCode());

//        - And Content - Type is application/json
        Assertions.assertEquals("application/json",response.getContentType());

//        - And response contains United States of America
        Assertions.assertTrue(response.body().asString().contains("United States of America"));
    }

    @Test
    public void Q2(){
//        - Given accept type is Json
//        - When users send request to /employees/1
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("/employees/1");

//        - Then status code is 404
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void Q3(){
//        - Given accept type is Json
//        - When users send request to /regions/1
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("/regions/1");

//                - Then status code is 200
        Assertions.assertEquals(200, response.statusCode());

//                - And Content - Type is application/json
        Assertions.assertEquals("application/json",response.getContentType());

//                - And response contains Europe
        Assertions.assertTrue(response.body().asString().contains("Europe"));

//                - And header should contain Date
        Assertions.assertTrue(response.headers().hasHeaderWithName("Date"));

//        - And Transfer-Encoding should be chunked
        Assertions.assertEquals("chunked",response.header("Transfer-Encoding"));
    }




}
