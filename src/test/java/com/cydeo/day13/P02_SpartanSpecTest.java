package com.cydeo.day13;

import com.cydeo.utilities.SpartanNewTestBase;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

    /**
     *  Create GET_RBAC.csv
     *   username,password,id,statuscode
     *    admin,admin,3,200
     *    editor,editor,3,200
     *    user,user,3,200
     *
     *  Create a parameterized test to check RBAC for GET method
     *
     *
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/GET_RBAC.csv", numLinesToSkip = 1)
    public void checkAll_RBAC_forGET_Method(String username, String password,int id,int statusCode){

        given().spec(dynamicReqSpec(username,password))
                .pathParam("id", id)
                .when().get("/spartans/{id}")
                .then().spec(dynamicResSpec(statusCode));

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/DELETE_RBAC.csv", numLinesToSkip = 1)
    public void checkAll_RBAC_ForDeleteMethod(String username, String password,int id,int statusCode){

        given().spec(dynamicReqSpec(username,password))
                .pathParam("id", id)
                .when().delete("/spartans/{id}")
                .then().spec(dynamicResSpec(statusCode));

    }






    }
