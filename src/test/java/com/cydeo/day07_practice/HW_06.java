package com.cydeo.day07_practice;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class HW_06 extends SpartanTestBase {

    static int spartanId;
    Spartan dummySpartan;
    String message = "A Spartan is Born!";


    // Create a Spartan Flow to run below testCases dynamicly

    @DisplayName("POST /api/spartans")
    @Test
    @Order(1)
    public void test1() {
//            Create a spartan Map
        Map<String, Object> postSpartanRequest = new HashMap<>();
        postSpartanRequest.put("name", "API Flow POST");
        postSpartanRequest.put("gender", "Male");
        postSpartanRequest.put("phone",1231231231L);

//            - verify status code 201
        JsonPath jsonPath = given().accept(ContentType.JSON).log().body()
                .contentType(ContentType.JSON)
                .body(postSpartanRequest)
                .when().post("/api/spartans")
                .then().statusCode(201).contentType("application/json")
                .extract().jsonPath();

//            - message is "A Spartan is Born!"
        assertEquals(message,jsonPath.getString("success"));

//            - take spartanID from response and save as a global  variable
        spartanId = jsonPath.getInt("data.id");
        System.out.println("spartanId = " + spartanId);
    }

    @DisplayName("GET Spartan with spartanID /api/spartans/{id}")
    @Test
    @Order(2)
    public void test2() {

        JsonPath jsonPath = given().accept(ContentType.JSON).log().uri()
                .pathParam("id", spartanId)
                .get("/api/spartans/{id}")
//             - verify status code 200
                .then().statusCode(200)
//             - verfiy name is API Flow POST
                .extract().jsonPath();

        dummySpartan = jsonPath.getObject("",Spartan.class);

        assertEquals("API Flow POST", dummySpartan.getName());
    }

    @DisplayName("PUT Spartan with spartanID /api/spartans/{id}")
    @Test
    @Order(3)
    public void test3() {

//             Create a spartan Map
        Map<String, Object> putSpartanRequest = new HashMap<>();
        putSpartanRequest.put("name", "API PUT Flow");
        putSpartanRequest.put("gender", "Female");
        putSpartanRequest.put("phone", 3213213213L);

//             - verify status code 204
        given().contentType(ContentType.JSON).log().body()
                .pathParam("id",spartanId)
                .body(putSpartanRequest)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204);
    }

    @DisplayName("GET Spartan with spartanID /api/spartans/{id}")
    @Test
    @Order(4)
    public void test4() {
        JsonPath jsonPath = given().accept(ContentType.JSON).log().uri()
                .pathParam("id", spartanId)
                .get("/api/spartans/{id}")
//             - verify status code 200
                .then().statusCode(200)
//             - verify name is API PUT Flow
                .extract().jsonPath();

        dummySpartan = jsonPath.getObject("",Spartan.class);

        assertEquals("API PUT Flow", dummySpartan.getName());
    }

    @DisplayName("DELETE Spartan with spartanID /api/spartans/{id}")
    @Test
    @Order(5)
    public void test5() {

        given().accept(ContentType.JSON)
                .pathParam("id", spartanId)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);
//            - verify status code 204
    }

        @DisplayName("GET Spartan with spartanID /api/spartans/{id}")
        @Test
        @Order(6)
        public void test6() {
            JsonPath jsonPath = given().accept(ContentType.JSON).log().uri()
                    .pathParam("id", spartanId)
                    .get("/api/spartans/{id}")
//             - verify status code 404
                    .then().statusCode(404)
//             - verify name is API PUT Flow
                    .extract().jsonPath();
        }


}
