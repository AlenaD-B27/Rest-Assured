package com.cydeo.day12;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.*;

public class P09_SpartanPOSTDDT extends SpartanTestBase {

    /**
     *
     *  POST SPARTAN DDT
     *
     *  Given content type is json
     *  And accept type is json
     *  When I POST Spartan
     *  And status code needs to 201
     *  Verify spartan information is matching dynamic that we provide
     *
     *  Generate DUMMY DATA
     *  https://www.mockaroo.com/
     *
     *  name
     *  gender
     *  phone
     *
     */

    @ParameterizedTest
    @CsvFileSource(resources = "/newbornSpartans.csv", numLinesToSkip = 1)
    public void spartansBirthday(String name, String gender, long phone){

        Spartan newSpartan = new Spartan();
        newSpartan.setName(name);
        newSpartan.setGender(gender);
        newSpartan.setPhone(phone);

        JsonPath jsonPath = given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(newSpartan)
                .when().post("/api/spartans")//.prettyPeek()
                .then().statusCode(201)
                .extract().jsonPath();

        Spartan actualSpartanFromResponse = jsonPath.getObject("data", Spartan.class);


        Assertions.assertEquals(newSpartan.getName(), actualSpartanFromResponse.getName());
        Assertions.assertEquals(newSpartan.getGender(),actualSpartanFromResponse.getGender());
        Assertions.assertEquals(newSpartan.getPhone(),actualSpartanFromResponse.getPhone());

    }


}