package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class P04_HrWithResponsePath extends HrTestBase {


    @DisplayName("GET request to countries with using Response Path")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":2}").
                when().get("/countries");

        System.out.println("response.path(\"limit\") = " + response.path("limit"));
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));
        System.out.println("response.path(\"items[1].country_id\") = " + response.path("items[1].country_id"));
        System.out.println("response.path(\"items[3].country_name\") = " + response.path("items[3].country_name"));
        System.out.println("response.path(\"items[3].links[0].href\") = " + response.path("items[3].links[0].href"));

        System.out.println("response.path(\"items.country_name\") = " + response.path("items.country_name"));

        List<String> allCountryNames = response.path("items.country_name");

        // verify all region id is 2

        List<Integer> allRegionIds = response.path("items.region_id");
        assertTrue(allRegionIds.stream().allMatch(each -> each == 2));


    }












}
