package com.cydeo.day06_practice;

import com.cydeo.pojo.Region;

import com.cydeo.pojo.Regions;
import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class Task extends HrTestBase {
    /*
    TASK

    Given accept is application/json
    When send request  to /regions endpoint
    Then status should be 200
            verify that region ids are 1,2,3,4
            verify that regions names Europe ,Americas , Asia, Middle East and Africa
            verify that count is 4
        -- Create Regions POJO
        -- And ignore field that you don't need
     */
    @Test
    public void homework_test(){
        JsonPath jsonPath = given().accept(ContentType.JSON).when().get("/regions").
              //  prettyPeek().
                then().statusCode(200).
                extract().response().jsonPath();


        Regions regions = jsonPath.getObject("", Regions.class);


        List<Integer> rIds = new ArrayList<>();
        List<String> rNames = new ArrayList<>();

        regions.getAllRegions().forEach(eachRegion -> {
            rIds.add(eachRegion.getRegionId());
            rNames.add(eachRegion.getRegionName());
        });

        assertEquals(Arrays.asList(1,2,3,4),rIds);
        assertEquals(Arrays.asList("Europe" ,"Americas", "Asia", "Middle East and Africa"),rNames);
        assertEquals(4,regions.getCount());

    }
}
