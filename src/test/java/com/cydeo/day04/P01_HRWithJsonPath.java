package com.cydeo.day04;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01_HRWithJsonPath extends HrTestBase {

    //Given accept type application/JSON
    // And query param limit is 200
    // When user send request /employees
    // Then user should se....

    @DisplayName("Get All /employees?limit=200 ---> JSON path")
    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("limit", 200)
                .when().get("/employees");


       // response.prettyPrint();

        //Status code 200
        assertEquals(200,response.statusCode());
        // get all emails from response
        JsonPath jsonPath = response.jsonPath();
        List<String> allEmails = jsonPath.getList("items.emails");
        //get all emails who is working as IT_PROG

        List<String> listItProgs = jsonPath.getList("items.findAll {it.job_id=='IT_PROG'}.email");
        System.out.println("listItProgs = " + listItProgs);
        // get me all employees first names whose salary is more than 10000
        List<Integer> allEmployeesSalaryMoreThan10000 = jsonPath.getList("items.names.findAll {it.salary >= 10000}.first_name");

        // get me all information from response  who has max salary
        System.out.println("max salary --> " + jsonPath.getString("items.max{it.salary}"));

        // get me firstname from response  who has max salary
        System.out.println("max salary.first_name " + jsonPath.getString("items.max{it.salary}.first_name"));

        // get me firstname from response  who has min salary
        System.out.println("min salary.first_name " + jsonPath.getString("items.min{it.salary}.first_name"));

    }


     /*

    TASK
    Given
             accept type is application/json
     When
             user sends get request to /locaitons
     Then
             response status code must be 200
             content type equals to application/json
             get the second city with JsonPath
             get the last city with JsonPath
             get all country ids
             get all city where their country id is UK

      */

    @Test
    public void task2(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/locations");

        assertEquals(200, response.statusCode());
        assertEquals(ContentType.JSON.toString(),response.contentType());
        JsonPath jsonPath = response.jsonPath();
        String secondCity = jsonPath.getString("items[1].city");
        System.out.println("secondCity = " + secondCity);
        String lastCity = jsonPath.getString("items[-1].city");
        System.out.println("lastCity = " + lastCity);
        List<String> allCountryIds = jsonPath.getList("items.country_id");
        System.out.println("allCountryIds = " + allCountryIds);
        List<String> citiesUK = jsonPath.getList("items.findAll {it.country_id == 'UK'}.city");
        System.out.println("citiesUK = " + citiesUK);

    }






}
