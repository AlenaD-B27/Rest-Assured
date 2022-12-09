package com.cydeo.day06;

import com.cydeo.utilities.CydeoTrainingBase;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.*;

public class LunchTask extends CydeoTrainingBase {
    /*
     * Create a test called getTeachers
     * 1. Send request to GET /teacher/all
     * 2. log uri to see
     * 3. Get all Json as a map and print out screen all the things with the help of  map
     */

    @Test
    public void getTeachers(){
        JsonPath jsonPath = given().log().uri()
                .when().get("/teacher/all")
        //        .prettyPeek()
                .then().statusCode(200)
                .extract().response().jsonPath();

        System.out.println();

        System.out.println("====== GET FIRST TEACHER INFO   ======");
        List<Map<String, Object>> allTeachers = jsonPath.getList("teachers");
        Map<String, Object> firstTeacherMap = allTeachers.get(0);
        System.out.println(firstTeacherMap);

        System.out.println();

        System.out.println("====== GET FIRST TEACHER NAME  ======");
        String firstTeacherName = (String) firstTeacherMap.get("firstName");
        System.out.println(firstTeacherName);

        System.out.println();

        System.out.println("====== GET ALL TEACHER INFO  AS LIST OF MAP======");
        allTeachers.forEach(p -> {
            System.out.println(p);
            System.out.println();
        });

        System.out.println("====== FIRST TEACHER INFO======");
        firstTeacherMap.entrySet().forEach(System.out::println);

        System.out.println();

        System.out.println("====== FIRST TEACHER NAME ======");
        String firstTeacherFullName = firstTeacherMap.get("firstName") + " " + firstTeacherMap.get("lastName");
        System.out.println(firstTeacherFullName);

        System.out.println();

        System.out.println("====== LAST TEACHER NAME  ======");
        Map<String, Object> lastTeacherMap = allTeachers.get(allTeachers.size() - 1);
        String lastTeacherFullName = lastTeacherMap.get("firstName") + " " + lastTeacherMap.get("lastName");
        System.out.println(lastTeacherFullName);

    }

}
