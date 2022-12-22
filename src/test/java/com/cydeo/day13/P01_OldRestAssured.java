package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class P01_OldRestAssured extends SpartanNewTestBase {


    @Test
    public void getAllSpartan() {


        given().accept(ContentType.JSON)
                .auth().basic("admin","admin")   // request specification

                .expect().statusCode(200)
                .contentType(ContentType.JSON)
                .body("id[0]", is(1))
                .body("id[1]", is(2)) // response specification

                .when().get("/spartans"); // assertions after "expect" works as soft assertion



    }
}