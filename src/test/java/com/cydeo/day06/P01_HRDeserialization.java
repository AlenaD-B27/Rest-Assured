package com.cydeo.day06;

import com.cydeo.utilities.HrTestBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P01_HRDeserialization extends HrTestBase {
    /**
     * Create a test called getLocation
     * 1. Send request to GET /locations
     * 2. log uri to see
     * 3. Get all Json as a map and print out screen all the things with the help of  map
     * System.out.println("====== GET FIRST LOCATION  ======");
     * System.out.println("====== GET FIRST LOCATION LINKS  ======");
     * System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
     * System.out.println("====== FIRST LOCATION ======");
     * System.out.println("====== FIRST LOCATION ID ======");
     * System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
     * System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
     * System.out.println("====== LAST LOCATION ID ======");
     */

    @Test
    public void getLocation(){

        JsonPath jsonPath = given().log().uri()
                .when().get("/locations")
                .then().statusCode(200)
                .extract().response().jsonPath();

        System.out.println("====== GET FIRST LOCATION  ======");
        Map<String,Object> firstMap = jsonPath.getMap("items[0]");
        System.out.println(firstMap);

        System.out.println("====== GET FIRST LOCATION LINKS  ======");
        Map<String, Object> firstMapLinks = jsonPath.getMap("items[0].links[0]");
        System.out.println(firstMapLinks.get("href"));

        System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
        List<Map<String,Object>> allLocationsMap = jsonPath.getList("items");
        for (Map<String,Object> eachMap : allLocationsMap)
            System.out.println(eachMap);

        System.out.println("====== FIRST LOCATION ======");
        System.out.println(allLocationsMap.get(0));

        System.out.println("====== FIRST LOCATION ID ======");
        System.out.println(allLocationsMap.get(0).get("location_id"));

        System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
        System.out.println(allLocationsMap.get(0).get("country_id"));


        System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
        List<Map<String,Object>> links = (List<Map<String, Object>>) allLocationsMap.get(0).get("links");
        System.out.println(links.get(0).get("href"));


        System.out.println("====== LAST LOCATION ID ======");

    }


}
