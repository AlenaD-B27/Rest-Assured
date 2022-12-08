package com.cydeo.day04_practice;

import com.cydeo.utilities.ZipcodeBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Zippo extends ZipcodeBase {

    @DisplayName("GET /us/{zip}")
    @Test
    public void test1(){
//        Given Accept application/json
//        And path zipcode is 22031
//        When I send a GET request to /us endpoint
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("zip", 22031)
                .when().get("/us/{zip}");
//        Then status code must be 200
        assertEquals(200,response.statusCode());
//        And content type must be application/json
        assertEquals(ContentType.JSON.toString(),response.contentType());
//        And Server header is cloudflare
        assertEquals("cloudflare", response.header("Server"));
//        And Report-To header exists
        assertTrue(response.headers().hasHeaderWithName("Report-To"));
//        And body should contain following information
//        post code is 22031 country is United States country abbreviation is US place name is Fairfax state is Virginia and latitude is 38.8604

        JsonPath jsonPath = response.jsonPath();

        assertEquals(22031, jsonPath.getInt("'post code'"));
        assertEquals("United States",jsonPath.getString("country"));
        assertEquals("US",jsonPath.getString("'country abbreviation'"));
        assertEquals("Fairfax",jsonPath.getString("places[0].'place name'"));
        assertEquals("Virginia",jsonPath.getString("places[0].state"));
        assertEquals(38.8604,jsonPath.getDouble("places[0].latitude"));
    }

    @DisplayName("GET /us/{zip} NEGATIVE TEST")
    @Test
    public void test2(){
//        Given Accept application/json
        Response response = given().accept(ContentType.JSON)
//        And path zipcode is 50000
                .and().pathParam("zip", 50000)
//        When I send a GET request to /us endpoint
                .when().get("/us/{zip}");
//        Then status code must be 404
        assertEquals(404,response.statusCode());
//        And content type must be application/json
        assertEquals(ContentType.JSON.toString(),response.contentType());
    }


    @DisplayName("GET /us/{state}/{city}")
    @Test
    public void test3(){
//        Given Accept application/json
//        And path state is va
//        And path city is fairfax
//        When I send a GET request to /us endpoint
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("state", "VA")
                .and().pathParam("city", "Fairfax")
                .when().get("/us/{state}/{city}");
//        Then status code must be 200
        assertEquals(200,response.statusCode());
//        And content type must be application/json
        assertEquals(ContentType.JSON.toString(),response.contentType());
//        And payload should contain following information
        JsonPath jsonPath = response.jsonPath();
//        country abbreviation is US
        assertEquals("US",jsonPath.getString("'country abbreviation'"));
//        country United States
        assertEquals("United States",jsonPath.getString("country"));
//        place name Fairfax
        assertEquals("Fairfax", jsonPath.getString("'place name'"));
//        each places must contain fairfax as a value
        List<String> placeNameList = jsonPath.getList("places.'place name'");
        assertTrue(placeNameList.stream().allMatch(p -> p.contains("Fairfax")));
//        each post code must start with 22
        List<String> listOfPostCodes = jsonPath.getList("places.'post code'");
        assertTrue(listOfPostCodes.stream().allMatch(p -> p.startsWith("22")));
    }


}
