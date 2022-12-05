package com.cydeo.day03;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_SpartanWithResponsePath extends SpartanTestBase {

    /*
    Given accept type is json
    And path param id is 10
    When user sends a get request to "api/spartans/{id}"
    Then status code is 200
    And content-type is "application/json"
    And response payload values match the following:
         id is 10,
         name is "Lorenza",
         gender is "Female",
         phone is 3312820936
  */
    @DisplayName("GET spartan with Response Path")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("api/spartans/{id}");

        assertEquals(200,response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());

        int id = response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        // if the response path is incorrect  ---> returns NULL as a value


        assertEquals(10,id);
        assertEquals("Lorenza",name);
        assertEquals("Female",gender);
        assertEquals(3312820936L,phone);

    }


    @DisplayName("GET all spartans")
    @Test
    public void test2() {

        Response response = given().accept(ContentType.JSON).
                when().get("/api/spartans");

        // response.prettyPrint();

        // get me first spartan ID
        int firstID = response.path("[0].id");
        System.out.println("firstID = " + firstID);
        int IDFirst = response.path("id[0]");
        System.out.println("IDFirst = " + IDFirst);

        // get me first spartan name
        System.out.println("response.path(\"name[0]\") = " + response.path("name[0]"));

        // get me last spartan name
        // "name[-1]" --> -1 refers last element of the name list
        // with the help of GPATH we can get data
        System.out.println("response.path(\"name[-1]\") = " + response.path("name[-1]"));

        // get me second spartan name from the last
        System.out.println("response.path(\"name[-2]\") = " + response.path("name[-2]"));


        // get me all spartans name
        List<String> allNames = response.path("name");

        // How to print all
        for (String eachName : allNames) {
            System.out.println("eachName = " + eachName);
        }


    }






}
