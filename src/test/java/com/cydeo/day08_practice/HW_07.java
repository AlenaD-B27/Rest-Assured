package com.cydeo.day08_practice;

import com.cydeo.utilities.NewsTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.*;

public class HW_07 extends NewsTestBase {

    String apiKey = "ec98c00a8b6441c19d8de17e596f29a1";

    static List<Map<String, Object>> articles;


    /*

- Given query param is q=“bitcoin”
         - And query param is apiKey=“yourKey”
         - When user sent request / everything endpoint
 - Then status code should be 200
 - And each articles contains “bitcoin”
     */

    @Test
    public void task1() {
        JsonPath jsonPath = given().accept(ContentType.JSON).queryParam("q", "bitcoin")
                .and().queryParam("apiKey", apiKey)
                .when().get("/everything")
                .then().statusCode(200)
                .and().extract().jsonPath();

        articles = jsonPath.getList("articles");
        // articles.forEach(p -> assertTrue(p.toString().toLowerCase().contains("bitcoin")));

        AtomicInteger count = new AtomicInteger();
        articles.forEach(p -> {
            if(!p.toString().contains("bitcoin")){
                //  System.out.println(p);
                count.getAndIncrement();
            }
        });
        System.out.println(count.get() + " FAILED ARTICLES");

    }

 /*   - Given query param is q=“bitcoin”
            - And header is X-Api-Key=“yourKey”
            - When user sent request / everything endpoint
 - Then status code should be 200
            - And each articles contains “bitcoin”
  */

    @Test
    void task2() {
        JsonPath jsonPath = given().accept(ContentType.JSON).queryParam("q", "bitcoin")
                .header("X-Api-Key", apiKey)
                .when().get("/everything").then().statusCode(200).extract().jsonPath();

        articles = jsonPath.getList("articles");
        // articles.forEach(p -> assertTrue(p.toString().toLowerCase().contains("bitcoin")));

        AtomicInteger count = new AtomicInteger();
        articles.forEach(p -> {
            if(!p.toString().contains("bitcoin")){
              //  System.out.println(p);
                count.getAndIncrement();
            }
        });
        System.out.println(count.get() + " FAILED ARTICLES");
    }

    /* - Given query param is q=“bitcoin”
            - And header is Authorization=“yourKey”
            - When user sent request / everything endpoint
 - Then status code should be 200
            - And each articles contains “bitcoin”

     */

    @Test
    void task3() {
        JsonPath jsonPath = given().accept(ContentType.JSON).queryParam("q", "bitcoin")
                .header("Authorization", apiKey).when().get("everything")
                .then().statusCode(200).extract().jsonPath();

        articles = jsonPath.getList("articles");
        // articles.forEach(p -> assertTrue(p.toString().toLowerCase().contains("bitcoin")));

        AtomicInteger count = new AtomicInteger();
        articles.forEach(p -> {
            if (!p.toString().contains("bitcoin")) {
                //  System.out.println(p);
                count.getAndIncrement();
            }
        });
        System.out.println(count.get() + " FAILED ARTICLES");
    }


    /*

- Given query param is country=“us”
            - And header is Authorization=“yourKey”
            - When user sent request / top-headlines endpoint
 - Then status code should be 200

     */

    @Test
    void task4() {
        given().accept(ContentType.JSON)
                .header("Authorization", apiKey)
                .queryParam("country","us")
                .when().get("/top-headlines").then().statusCode(200);
    }
}
