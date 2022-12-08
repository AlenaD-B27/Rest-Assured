package com.cydeo.day05;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P03_HamCrestHR extends HrTestBase {

    /*
                Given accept type is Json
                And parameters: q = {"job_id":"IT_PROG"}
                When users sends a GET request to "/employees"
                Then status code is 200
                And Content type is application/json
                Verify
                    - each employees has manager
                    - each employees working as IT_PROG
                    - each of them getting salary greater than 3000
                    - first names are .... (find proper method to check list against list)
                    - emails without checking order (provide emails in different order,just make sure it has same emails)
           */
@Test
    public void test1(){

    List<String> names = Arrays.asList("Alexander", "Bruce", "David", "Valli", "Diana" );

    given().accept(ContentType.JSON)
            .and().queryParam("q","{\"job_id\":\"IT_PROG\"}")
            .when().get("/employees").prettyPeek()
            .then()

            .statusCode(200)
            .contentType("application/json")
            .body("items.manager_id", everyItem(notNullValue()))
            .body("items.job_id", everyItem(equalTo("IT_PROG")))
            .body("items.salary", everyItem(greaterThan(3000)))
            .body("items.first_name", equalTo(names))
            .body("items.email", containsInAnyOrder("DAUSTIN", "AHUNOLD", "BERNST", "VPATABAL", "DLORENTZ"));
}
}
