package com.cydeo.day04;

import com.cydeo.utilities.ZipcodeBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_ZipcodeTest extends ZipcodeBase {
    //https://www.zippopotam.us/#


    /*
     Given accept type is json
     and country path param value is "us"
     and postal code path param value is 22102
     When I send get request to http://api.zippopotam.us/{country}/{postal-code}
     Then status code is 200
     Then "post code" is "22102"
     And  "country" is "United States"
     And "place name" is "Mc Lean"
     And  "state" is "Virginia"
     */

    @DisplayName("GET /{country}/{postal-code}")
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("country", "US")
                .and().pathParam("postal-code", 22102)
                .when().get("/{country}/{postal-code}");

        assertEquals(200,response.statusCode());

        JsonPath jsonPath = response.jsonPath();

        assertEquals(22102, jsonPath.getInt("'post code'"));
        assertEquals("United States", jsonPath.getString("country"));
        assertEquals("Mc Lean", jsonPath.getString("places[0].'place name'"));
        assertEquals("Virginia",jsonPath.getString("places[0].state"));

    }




}
