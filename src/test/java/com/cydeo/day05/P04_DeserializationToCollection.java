package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P04_DeserializationToCollection extends SpartanTestBase {
    /*
     Given accept type is application/json
     And Path param id = 10
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is json
     And spartan data matching:
     id > 10
     name>Lorenza
     gender >Female
     phone >3312820936
     */

    @Test
    public  void test1() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 10)
                .when().get("/api/spartans/{id}")
                .then()

                .statusCode(200)
                .contentType(ContentType.JSON.toString())
                .extract().response();

        // Approach 1 --> with Response

        Map<String, Object> spartanMap = response.as(Map.class);
        System.out.println("spartanMap = " + spartanMap);

        int id = (int) spartanMap.get("id");
        String name = (String) spartanMap.get("name");
        String gender = (String) spartanMap.get("gender");

        // Approach 2 --> with JsonPath

        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> jsonPathMap = jsonPath.getMap("");
        System.out.println("jsonPathMap = " + jsonPathMap);

        int idJson = (int) jsonPathMap.get("id");
        String nameJson = (String) jsonPathMap.get("name");


    }

    @Test
    public  void test2() {

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then()

                .statusCode(200)
                .contentType(ContentType.JSON.toString())
                .extract().response();

        // Approach 1 --> with Response

        List<Map<String, Object>> spartanList = response.as(List.class);

        for(Map<String, Object> eachMap : spartanList){
            System.out.println(eachMap);
        }

        System.out.println("====================================");

        Map <String, Object> firstSpartanMap =
                // spartanList.get(0);
                response.path("[0]");
        System.out.println("firstSpartanMap = " + firstSpartanMap);

        System.out.println("====================================");


        // Approach 2 --> with JsonPath
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> allSpartanList = jsonPath.getList("");

        for(Map<String, Object> eachMap : allSpartanList){
            System.out.println(eachMap);
        }

    }

}
