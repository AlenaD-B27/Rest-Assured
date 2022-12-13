package com.cydeo.day08;

import com.cydeo.utilities.BookitTestBase;
import com.cydeo.utilities.BookitUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class P02_BookitTest extends BookitTestBase {

    String email = "lfinnisz@yolasite.com";
    String password = "lissiefinnis";
    String accessToken = BookitUtils.getToken(email,password);

    @DisplayName("GET /api/campuses")
    @Test
    public void test1(){

        given().accept(ContentType.JSON)
                        .header("Authorization", accessToken)
                .when().get("/api/campuses").prettyPeek()
                        .then().statusCode(200);


    }

    @DisplayName("GET /api/users/me")
    @Test
    public void test2(){

        given().accept(ContentType.JSON)
                .header("Authorization", accessToken)
                .when().get("/api/users/me").prettyPeek()
                .then().statusCode(200);


    }


}
