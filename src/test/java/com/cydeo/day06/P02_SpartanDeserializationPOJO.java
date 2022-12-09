package com.cydeo.day06;

import com.cydeo.pojo.Search;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P02_SpartanDeserializationPOJO extends SpartanTestBase {

    @DisplayName("Get single spartan for deserialization to POJO")
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        // RESPONSE

        Spartan spartan = response.as(Spartan.class);
        System.out.println(spartan);
        System.out.println(spartan.getId());

        // JSONPATH

        JsonPath jsonPath = response.jsonPath();
        Spartan spartan1 = jsonPath.getObject("",Spartan.class);

    }

    @DisplayName("Get spartan from search endpoint for deserialization to POJO")
    @Test
    public void test2() {

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans/search")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();


        // RESPONSE
        System.out.println("==GET ONE SPARTAN==");
        //


        // JSONPATH
        JsonPath jsonPath = response.jsonPath();
        Spartan spartan = jsonPath.getObject("content[0]", Spartan.class);
        System.out.println(spartan);

    }

    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans/search")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        Search search = jsonPath.getObject("", Search.class);
    }

    @Test
    public void test4() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans/search")
              //  .prettyPeek()
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        List<Spartan> allSpartans = jsonPath.getList("content", Spartan.class);

        System.out.println("allSpartans.get(0).getId() = " + allSpartans.get(0).getId());


    }


}
