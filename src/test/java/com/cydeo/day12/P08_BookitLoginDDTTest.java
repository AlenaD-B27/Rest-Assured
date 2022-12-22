package com.cydeo.day12;

import com.cydeo.utilities.BookitUtils;
import com.cydeo.utilities.ExcelUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class P08_BookitLoginDDTTest {

    //create a method to read bookitqa3 excel file information (QA3 Sheet)
    //use those information as an email and password to send a get request to /sign endpoint
    //verify you got 200 for each request
    //print accessToken for each request

    @ParameterizedTest
    @MethodSource("getExcel")
    public void testBookitCredentialsQA3(Map<String, String> credentials){
     //   System.out.println(credentials);

        System.out.println(given().baseUri("https://api.qa3.bookit.cydeo.com")
                .queryParam("email", credentials.get("email"))
                .queryParam("password", credentials.get("password"))
                .when().get("/sign")//.prettyPeek()
                .then().statusCode(200)
                .extract().jsonPath().getString("accessToken"));
    }

    private static List<Map<String, String>> getExcel(){
        return new ExcelUtil("src/test/resources/BookItQa3.xlsx","QA3").getDataList();
    }

}
