package com.cydeo.day12;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class P03_ValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints = {10,20,30,40,20})
    public void test1(int number) {
        System.out.println(number);
        Assertions.assertTrue(number < 40);
    }

    @ParameterizedTest(name = "{index} name is {0}")
    @ValueSource(strings = {"Mike", "Rose", "Caberly", "Kimberly", "TJ", "King"})
    public void test2(String name){
        System.out.println("name = " + name);
        Assertions.assertTrue(name.startsWith("Ki"));
    }

    @ParameterizedTest
    @ValueSource(ints = {22030,22031, 22032, 22033 , 22034, 22035, 22036})
    public void test3(int zipcode){
        given().accept(ContentType.JSON)
                .pathParam("zipcode", zipcode)
                .when().get("https://api.zippopotam.us/us/{zipcode}")
                .then().statusCode(200);
    }


}
