package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static com.cydeo.utilities.NewsTestBase.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class P02_SpartanSpecTest extends SpartanNewTestBase {

    @Test
    public void getAllSpartans() {

        given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when().get("spartans")

                .then()

                .statusCode(200)
                .contentType(ContentType.JSON);

    }

    @Test
    public void getAllSpartansWithSpecReq() {

        RequestSpecification reqSpec = given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin");


        ResponseSpecification resSpec = expect().statusCode(200)
                .contentType(ContentType.JSON);


        given().spec(reqSpec)
                .when().get("/spartans")
                .then().spec(resSpec);

    }

    @Test
    public void getSingleSpartanWithSpecReq() {

        given().spec(reqSpec).
                pathParam("id",3).
                when().get("/spartans/{id}").prettyPeek().
                then().spec(resSpec)
                .body("id",is(3));

    }


    @Test
    public void getSingleSpartanAsUser(){

        given().spec(dynamicReqSpec("user", "user"))
                .pathParam("id",3)
                .when().get("/spartans/{id}").prettyPeek()
                .then().spec(dynamicResSpec(200));

    }



    }
