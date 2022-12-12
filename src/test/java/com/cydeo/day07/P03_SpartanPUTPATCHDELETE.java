package com.cydeo.day07;


import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class P03_SpartanPUTPATCHDELETE extends SpartanTestBase {

    @DisplayName("PUT Spartan Single Spartan With Map ")
    @Test
    public void test1() {

        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name","JeremyBB");
        requestBody.put("gender", "Male");
        requestBody.put("phone",1236547890L);

        int id = 105;

        given().log().body()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(requestBody)
                .when().put("/api/spartans/{id}")
                .then().statusCode(204).extract().jsonPath();// TODO Expected status code <204> but was <400>.

        given().accept(ContentType.JSON)
                .pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).contentType(ContentType.JSON).extract().jsonPath();




    }

    @DisplayName("DELETE  Single Spartan ")
    @Test
    public void test3() {


        int id=103;

        given().accept(ContentType.JSON)
                .pathParam("id",id)
                .when().delete("api/spartans/{id}")
                .then().statusCode(204);

        given().accept(ContentType.JSON)
                .pathParam("id",id)
                .when().get("api/spartans/{id}")
                .then().statusCode(404);




    }


}