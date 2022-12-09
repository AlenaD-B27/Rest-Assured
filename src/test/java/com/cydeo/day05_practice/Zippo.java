package com.cydeo.day05_practice;

import com.cydeo.utilities.ZipcodeBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class Zippo extends ZipcodeBase {


    /*
    Given Accept application/json
And path zipcode is 22031
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And Server header is cloudflare
And Report-To header exists
And body should contains following information
post code is 22031 country is United States country abbreviation is US place name is Fairfax state is Virginia
latitude is 38.8604
     */

    @Test
    public void task1(){

        given().log().uri().accept(ContentType.JSON)
                .and().pathParam("zip", 22031)
                .when().get("/us/{zip}").prettyPeek()

                .then()

                .statusCode(200)
                .contentType("application/json")
                .header("Server", "cloudflare")
                .header("Report-To", notNullValue())
                .body("'post code'", equalTo("22031"))
                .body("country",equalTo("United States"))
                .body("'country abbreviation'",equalTo("US"))
                .body("places[0].'place name'", equalTo("Fairfax"))
                .body("places[0].state", equalTo("Virginia"))
                .body("places[0].latitude", equalTo("38.8604"));


    }

    /*
    Given Accept application/json
And path zipcode is 50000
When I send a GET request to /us endpoint
Then status code must be 404
And content type must be application/json
     */

    @Test
    public void task2(){

        given().log().uri().accept(ContentType.JSON)
                .and().pathParam("zip",50000)
                .when().get("/us/{zip}").prettyPeek()

                .then()

                .statusCode(404)
                .contentType("application/json");

    }

    /*
    Given Accept application/json
And path state is va
And path city is fairfax
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And payload should contain following information
country abbreviation is US
country United States
place name Fairfax
each places must contains fairfax as a value
each post code must start with 22
     */

    @Test
    public void task3(){

        given().log().uri().accept(ContentType.JSON)
                .and().pathParam("state", "VA")
                .and().pathParam("city", "Fairfax")
                .when().get("/us/{state}/{city}").prettyPeek()

                .then()

                .statusCode(200)
                .contentType("application/json")
                .body("'country abbreviation'", equalTo("US"))
                .body("country",equalTo("United States"))
                .body("'place name'",equalTo("Fairfax"))
                .body("places.'place name'", everyItem(containsString("Fairfax")))
                .body("places.'post code'", everyItem(startsWith("22")));


    }



}
