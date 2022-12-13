package com.cydeo.day07_practice;

import com.cydeo.pojo.Region;
import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.*;

import java.util.*;

import static com.cydeo.utilities.DB_Util.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HW_05 extends HrTestBase {

    @DisplayName("POST and GET region")
    @Order(1)
    @Test
    public void task1() {

        Map<String,Object> postRequest = new HashMap<>();
        postRequest.put("region_id",100);
        postRequest.put("region_name","Test Region");

//    Given accept is json
        JsonPath jsonPath = given().accept(ContentType.JSON)
//    and content type is json
                .and().contentType(ContentType.JSON)
//    When I send post request to "/regions/"
//    With json:
//    {
//        "region_id":100,
//            "region_name":"Test Region"
//    }
                .when().body(postRequest)
                .and().post("/regions/")
//    Then status code is 201
                .then().statusCode(201)
//    content type is json
                .and().contentType("application/json")
                .extract().jsonPath();

        Region justPostedRegion = jsonPath.getObject("",Region.class);
//    region_id is 100
        assertEquals(postRequest.get("region_id"), justPostedRegion.getRegionId());
//    region_name is Test Region
        assertEquals(postRequest.get("region_name"),justPostedRegion.getRegionName());


//    Given accept is json
        JsonPath jsonPath1 = given().accept(ContentType.JSON)
//    When I send GET request to "/regions/100"
                .pathParam("id", 100)
                .when().get("/regions/{id}")
//    Then status code is 200
                .then().statusCode(200)
//    content type is json
                .and().contentType(ContentType.JSON)
                .extract().jsonPath();

        Region getRegion = jsonPath.getObject("", Region.class);

//    region_id is 100
        assertEquals(postRequest.get("region_id"), getRegion.getRegionId());
//    region_name is Test Region
        assertEquals(postRequest.get("region_name"),getRegion.getRegionName());
    }


    @DisplayName("PUT and DELETE region")
    @Order(2)
    @Test
    public void task2(){

        Map<String,Object> putRequest = new HashMap<>();
        putRequest.put("region_id",100);
        putRequest.put("region_name","Wooden Region");

//        Given accept type is Json
        JsonPath jsonPath = given().accept(ContentType.JSON)
//        And content type is json
                .and().contentType(ContentType.JSON)
//        When i send PUT request to /regions/100
//        With json body:
//        {
//            "region_id": 100,
//                "region_name": "Wooden Region"
//        }
                .body(putRequest)
                .when().put("/regions/100")
//        Then status code is 200
                .then().statusCode(200)
//        And content type is json
                .and().contentType(ContentType.JSON)
                .extract().jsonPath();

        Region putRegion = jsonPath.getObject("",Region.class);

//        region_id is 100
        assertEquals(putRequest.get("region_id"),putRegion.getRegionId());

//        region_name is Wooden Region
        assertEquals(putRequest.get("region_name"),putRegion.getRegionName());


//        Given accept type is Json
        given().accept(ContentType.JSON)
//        When i send DELETE request to /regions/100
                .when().delete("/regions/100")
//        Then status code is 200
                .then().statusCode(200);
    }

    @DisplayName("POST-DB-DELETE region")
    @Order(3)
    @Test
    public void task3() {

        Map<String,Object> postRequest = new HashMap<>();
        postRequest.put("region_id",200);
        postRequest.put("region_name","Test Region");

//        Given accept is json
//        and content type is json
//        When I send post request to "/regions/"
//        With json:
//        {
//            "region_id":200,
//                "region_name":"Test Region"
//        }
//        Then status code is 201
//        content type is json

        given().accept(ContentType.JSON).contentType(ContentType.JSON).body(postRequest).when().post("/regions/").then().statusCode(201).contentType(ContentType.JSON);

//        When I connect to HR database and execute query "SELECT region_id,region_name FROM regions WHERE region_id = 200"

        createConnection("jdbc:oracle:thin:@34.204.7.162:1521:XE","hr","hr");


        runQuery("SELECT region_id,region_name FROM regions WHERE region_id = 200");

        Map<String, String> dbMap = getRowMap(1);

//        Then region_name from database should match region_name from POST request

        assertEquals(postRequest.get("region_name"),dbMap.get("REGION_NAME") );

//        Given accept type is Json
        given().accept(ContentType.JSON)
//        When i send DELETE request to /regions/100
                .when().delete("/regions/200")
//        Then status code is 200
                .then().statusCode(200);

    }

}
