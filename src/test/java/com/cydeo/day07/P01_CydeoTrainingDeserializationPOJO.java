package com.cydeo.day07;
import com.cydeo.pojo.Student;
import com.cydeo.utilities.CydeoTrainingBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P01_CydeoTrainingDeserializationPOJO extends CydeoTrainingBase {


      /*
    Given accept type is application/json
    And path param is 2
    When user send request /student/{id}
    Then status code should be 200
    And verify following
                firstName Mark
                batch 13
                major math
                emailAddress mark@email.com
                companyName Cydeo
                street 777 5th Ave
                zipCode 33222

       // Ignore all non necessray fields


     */

    @DisplayName("GET /student/{id} 2 ")
    @Test
    public void test1() {

        JsonPath jsonPath = given().log().all().accept(ContentType.JSON)
                .and()
                .pathParam("id", 2).
                when().get("/student/{id}")
                .then().statusCode(200)
                .extract().jsonPath();


        Student student = jsonPath.getObject("students[0]", Student.class);

        assertEquals("Mark",student.getFirstName());
        assertEquals(13, student.getBatch());
        assertEquals("math",student.getMajor());
        assertEquals("mark@email.com",student.getContact().getEmailAddress());
        assertEquals("Cydeo",student.getCompany().getCompanyName());
        assertEquals("777 5th Ave",student.getCompany().getAddress().getStreet());
        assertEquals(33222,student.getCompany().getAddress().getZipCode());


    }

}