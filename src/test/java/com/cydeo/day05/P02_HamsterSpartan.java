package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P02_HamsterSpartan extends SpartanTestBase {

    /*
    Given accept type is Json
    And path param id is 15
    When user sends a get request to spartans/{id}
    Then status code is 200
    And content type is Json
    And json data has following
        "id": 15,
        "name": "Meta",
        "gender": "Female",
        "phone": 1938695106
     */
    @DisplayName("Get Single Spartan with Hamcrest")
    @Test
    public void test1() {

         given().log().all().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/api/spartans/{id}")
                 .then().log().ifValidationFails().statusCode(200)
             //    .statusCode(is(200))
                 .contentType(ContentType.JSON.toString())
                 .body("id", is(15),
                         "name", is("Meta"),
                         "gender", is("Female"),
                         "phone", is(1938695106));

    }



}
