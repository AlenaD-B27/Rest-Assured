package com.cydeo.day11;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P02_ResponseTimeTest extends SpartanTestBase {

    @DisplayName("GET /api/spartans to get response time")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .auth().basic("admin", "admin").
                when().get("/api/spartans")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .time(both(greaterThan(100L)).and(lessThan(600L)))
                .extract().response();

        System.out.println(response.getTimeIn(TimeUnit.MILLISECONDS));


    }



}
