package com.cydeo.day05;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P02_HamsterSpartan extends SpartanTestBase {

    /*
    Given accept type is Json
    And path param id is 15
    When user sends a get request to spartans/{id}
    Then status code is 200
    And content type is Json
    And json data has following
        "id": 15,
        "name": "Meta",
        "gender": "Female",
        "phone": 1938695106
     */
    @DisplayName("Get Single Spartan with Hamcrest")
    @Test
    public void test1() {

         given().log().all().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/api/spartans/{id}")
                 .prettyPeek()
                 .then().log().ifValidationFails().statusCode(200)
             //    .statusCode(is(200))
                 .contentType(ContentType.JSON.toString())
                 .body("id", is(15),
                         "name", is("Meta"),
                         "gender", is("Female"),
                         "phone", is(1938695106));

    }


      /*
        REQUEST
            given().log().all() --> it will give all inforamtion anout your request (path/query params , URI , body etc )
                  .method()
                  .uri()
                  .parameters() ......
        RESPONSE

             then().log().all() --> it will give all response information
                         .ifValidationFails() --> it will print all response if one of the validation fails

         */

    // HOW TO PRINT RESPONSE BODY
        /*
            - response.prettyPrint() (String) ---> it is printing response body into screen

            - response.prettyPeek() (Response) ---> it will print response into screen, returns Response   and allows us to continue chaining

         */


}