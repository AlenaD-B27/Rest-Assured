package com.cydeo.day07;
import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class P02_SpartanPOST extends SpartanTestBase {

    /**
     Given accept type is JSON
     And Content type is JSON
     And request json body is:
     {
     "gender":"Male",
     "name":"John Doe",
     "phone":8877445596
     }
     When user sends POST request to '/api/spartans'
     Then status code 201
     And content type should be application/json
     And json payload/response/body should contain:
     verify the success value is A Spartan is Born!
     "name": "John Doe",
     "gender": "Male",
     "phone": 1231231231
     */
    @DisplayName("POST spartan with String body")
    @Test
    public void test1() {

        String requestBody = "{\n" +
                "     \"gender\":\"Male\",\n" +
                "     \"name\":\"John Doe\",\n" +
                "     \"phone\":8877445596\n" +
                "     }";

        String expectedMessage = "A Spartan is Born!";

        JsonPath jsonPath = given().accept(ContentType.JSON).log().body() // response type
                .contentType(ContentType.JSON) // request type
                .body(requestBody)
                .when().post("/api/spartans")
                .then().statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        assertEquals(expectedMessage,jsonPath.getString("success"));
        assertEquals("John Doe",jsonPath.getString("data.name"));
        assertEquals("Male",jsonPath.getString("data.gender"));
        assertEquals(8877445596L,jsonPath.getLong("data.phone"));

    }

    @DisplayName("POST spartan with Map body")
    @Test
    public void test2() {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("name","Jeremy Blond");
        requestBody.put("gender", "Male");
        requestBody.put("phone",1236547890L);

        JsonPath jsonPath = given().accept(ContentType.JSON).log().body() // response type
                .contentType(ContentType.JSON) // request type
                .body(requestBody) // Jackson does serialization (converting Map into JSON)
                .when().post("/api/spartans")
                .then().statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();


        assertEquals("Jeremy Blond",requestBody.get("name"));
        assertEquals("Male",requestBody.get("gender"));
        assertEquals(1236547890L,requestBody.get("phone"));

    }

    @DisplayName("POST spartan with POJO body")
    @Test
    public void test3() {


        Spartan requestBody = new Spartan();
        requestBody.setName("Dora Dro");
        requestBody.setGender("Female");
        requestBody.setPhone(9874563210L);

        JsonPath jsonPath = given().accept(ContentType.JSON).log().body() // response type
                .contentType(ContentType.JSON) // request type
                .body(requestBody) // Jackson does serialization (converting Map into JSON)
                .when().post("/api/spartans")
                .then().statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);


        assertEquals("Dora Dro",requestBody.getName());
        assertEquals("Female",requestBody.getGender());
        assertEquals(9874563210L,requestBody.getPhone());



    }


    @DisplayName("POST spartan with POJO body")
    @Test
    public void test4() {


        Spartan requestBody = new Spartan();
        requestBody.setName("Holly Molly");
        requestBody.setGender("Female");
        requestBody.setPhone(9999999999L);

        JsonPath jsonPath = given().accept(ContentType.JSON).log().body()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/api/spartans")
                .then().statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        int idFromPost = jsonPath.getInt("data.id");
        System.out.println("id = " + idFromPost);


        Spartan spartanGET = given().accept(ContentType.JSON)
                .pathParam("id", idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .extract().jsonPath().getObject("", Spartan.class);

        assertEquals(requestBody.getName(),spartanGET.getName());




    }
}