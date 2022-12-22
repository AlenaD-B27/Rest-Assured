package com.cydeo.utilities;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class BookitUtils {

    public static String getToken(String email, String password){

        String accessToken = given().accept(ContentType.JSON)
                .queryParam("email", email)
                .queryParam("password", password)
                .when().get("/sign")
                .then().statusCode(200)
                .extract().jsonPath().getString("accessToken");

        assertThat(accessToken, not(emptyOrNullString()));


        return "Bearer " + accessToken;
    }

    public static String getTokenByRole(String role) {
        String email = "";
        String password = "";

        switch (role) {
            case "teacher":
                email = ConfigurationReader.getProperty("teacher_email");
                password = ConfigurationReader.getProperty("teacher_password");
                break;

            case "student-member":
                email = ConfigurationReader.getProperty("team_member_email");
                password = ConfigurationReader.getProperty("team_member_password");
                break;
            case "student-leader":
                email = ConfigurationReader.getProperty("team_leader_email");
                password = ConfigurationReader.getProperty("team_leader_password");
                break;
            default:

                throw new RuntimeException("Invalid Role Entry :\n>> " + role +" <<");
        }

        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        String accessToken = given()
                .queryParams(credentials)
                .when().get( "/sign").path("accessToken");

        return  "Bearer " + accessToken;

    }

    public static RequestSpecification getRequestSpec(String role){
        return given().log().all()
                .header("Authorization", getTokenByRole(role))
                .accept(ContentType.JSON);
    }

    public static ResponseSpecification getResponseSpec(int statusCode){
        return expect().statusCode(statusCode).contentType(ContentType.JSON);
    }


}
