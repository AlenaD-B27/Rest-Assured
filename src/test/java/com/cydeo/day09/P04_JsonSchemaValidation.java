package com.cydeo.day09;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P04_JsonSchemaValidation extends SpartanTestBase {

    @DisplayName("GET /api/spartans/{id} to validate with JsonSchemaValidator")
    @Test
    public void test1() {

        given().accept(ContentType.JSON)
                .pathParam("id",45)
                .when().get("/api/spartans/{id}").prettyPeek()

                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SingleSpartanSchema.json")); // source root



    }

    @DisplayName("GET /api/spartans/{id} to validate with JsonSchemaValidator matchesJsonSchema")
    @Test
    public void test2() {

        given().accept(ContentType.JSON)
                .pathParam("id",45)
                .when().get("/api/spartans/{id}").prettyPeek()

                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/SingleSpartanSchema.json"))); // content root



    }

    @DisplayName("GET /api/spartans/search to validate with JsonSchemaValidator matchesJsonSchema")
    @Test
    public void test3() {

        given().accept(ContentType.JSON)
                .when().get("/api/spartans/search")
                .then().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SearchSpartansSchema.json"));

    }

    /**
     *     Do schema validation for ALLSPARTANS and POST SINGLE SPARTAN
     *
     *     ALL SPARTANS
     *      1- Get all spartans by using /api/spartans
     *      2- Validate schema by using  JsonSchemaValidator
     *
     *
     *    POST SINGLE SPARTANS
     *       1- Post single spartan
     *       2- Validate schema by using  JsonSchemaValidator
     *
     */

    @DisplayName("GET /api/spartans to validate with JsonSchemaValidator")
    @Test
    public void test4() {
        given().accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("AllSpartansSchema.json"));
    }

    @DisplayName("POST /api/spartans to validate with JsonSchemaValidator")
    @Test
    public void test5() {

        Map<String, Object> newSpartanInfo = new HashMap<>(Map.of("gender", "Female", "name", "Lidia", "phone", 3123127733L));

        given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(newSpartanInfo)
                .when().post("/api/spartans").then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("SpartanPostSchema.json"));

    }


}
