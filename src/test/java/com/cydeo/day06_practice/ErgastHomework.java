package com.cydeo.day06_practice;

import com.cydeo.pojo.Driver;
import com.cydeo.pojo.MRData;
import com.cydeo.utilities.ErgastBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class ErgastHomework extends ErgastBase {


    @Test
    public void test1_jsonPath(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json");

        JsonPath jsonPath = response.jsonPath();

//                - Then verify status code is 200
        assertEquals(200,response.statusCode());
//                - And content type is application/json; charset=utf-8
        assertEquals("application/json; charset=utf-8",response.contentType());
//                - And total is 1
        assertEquals(1, jsonPath.getInt("MRData.total"));
//                - And givenName is Fernando
        assertEquals("Fernando",jsonPath.getString("MRData.DriverTable.Drivers[0].givenName"));
//                - And familyName is Alonso
        assertEquals("Alonso",jsonPath.getString("MRData.DriverTable.Drivers[0].familyName"));
//                - And nationality is Spanish
        assertEquals("Spanish",jsonPath.getString("MRData.DriverTable.Drivers[0].nationality"));
    }

    @Test
    public void test1_hamcrestMatchers(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json
        given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
//                - Then verify status code is 200
                .then()
                .statusCode(200)
//                - And content type is application/json; charset=utf-8
                .and().contentType("application/json; charset=utf-8")
//                - And total is 1
                .and().body("MRData.total", is("1"),
//                - And givenName is Fernando
                "MRData.DriverTable.Drivers[0].givenName", is("Fernando"),
//                - And familyName is Alonso
                "MRData.DriverTable.Drivers[0].familyName", is("Alonso"),
//                - And nationality is Spanish
                "MRData.DriverTable.Drivers[0].nationality", is("Spanish"));


    }

    @Test
    public void test1_javaStructure(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json
//                - Then verify status code is 200
//                - And content type is application/json; charset=utf-8
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
                .then()
                .statusCode(200)
                .and().contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();

        Map<String,Object> alonsoMap = jsonPath.getMap("MRData.DriverTable.Drivers[0]");

        Map<String,Object> requirements = new HashMap<>();
        requirements.put("total",1);
        requirements.put("givenName","Fernando");
        requirements.put("familyName","Alonso");
        requirements.put("nationality","Spanish");
//                - And total is 1
//                - And givenName is Fernando
//                - And familyName is Alonso
//                - And nationality is Spanish
        alonsoMap.keySet().forEach(eachKey -> {
            if(requirements.containsKey(eachKey)){
                assertEquals(requirements.get(eachKey),alonsoMap.get(eachKey));
            }
        });
    }

    @Test
    public void test1_POJO(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json
//                - Then verify status code is 200
//                - And content type is application/json; charset=utf-8
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
                .then()
                .statusCode(200)
                .and().contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();

        Driver driver = jsonPath.getObject("MRData.DriverTable.Drivers[0]", Driver.class);
        MRData mrData = jsonPath.getObject("MRData",MRData.class);

//                - And total is 1
        assertEquals(1, mrData.getTotal());
//                - And givenName is Fernando
        assertEquals("Fernando",driver.getGivenName());
//                - And familyName is Alonso
        assertEquals("Alonso", driver.getFamilyName());
//                - And nationality is Spanish
        assertEquals("Spanish", driver.getNationality());
    }


}
