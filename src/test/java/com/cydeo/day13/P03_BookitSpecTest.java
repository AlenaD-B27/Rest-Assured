package com.cydeo.day13;

import com.cydeo.utilities.BookitTestBase;
import com.cydeo.utilities.BookitUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class P03_BookitSpecTest extends BookitTestBase {
    @Test
    public void test1() {

        given().log().all()
                .header("Authorization", BookitUtils.getTokenByRole("teacher"))
                .accept(ContentType.JSON)
                .when().get("/api/users/me")
                .then().statusCode(200).contentType(ContentType.JSON);


    }

    @Test
    public void test2() {

        given().spec(BookitUtils.getRequestSpec("teacher"))
                .when().get("/api/users/me")
                .then().spec(BookitUtils.getResponseSpec(200))
                .body("firstName",is("Barbabas"));

    }

    /***
     *
     *  Create a Parametirized test by using csv file as userInfo.csv
     *
     *   role , firstName
     *   student-member,Raymond
     *   student-leader,Lissie
     *   teacher,Barbabas
     *
     *
     *    Send GET request /api/users/me for each data
     *    Verify status code is 200
     *    content type is JSON
     *    FirstName is firstName from csv File
     *
     */

    // TODO HOMEWORK

    @CsvFileSource(resources = "/userInfo.csv", numLinesToSkip = 1)
    @ParameterizedTest
    public void getMeRequest(String role, String firstName){
        given().spec(BookitUtils.getRequestSpec(role))
                .when().get("/api/users/me").prettyPeek()
                .then().spec(BookitUtils.getResponseSpec(200))
                .body("firstName",is (firstName));
    }
}
