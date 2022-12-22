package com.cydeo.utilities;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
public abstract class SpartanNewTestBase {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @BeforeAll
    public static void init(){

        baseURI="http://34.204.7.162";
        port = 7000;
        basePath="/api";

        // baseURI+port+basePath

        reqSpec = given().log().all().accept(ContentType.JSON)
                .auth().basic("admin", "admin");


        resSpec = expect().statusCode(200)
                .contentType(ContentType.JSON);
    }

    public static RequestSpecification dynamicReqSpec(String username, String password){

        return given()
                .log().all()
                .accept(ContentType.JSON)
                .auth().basic(username, password);

    }

    public static ResponseSpecification dynamicResSpec(int statusCode){

        return      expect()
                .statusCode(statusCode)
                .contentType(ContentType.JSON);

    }






}
