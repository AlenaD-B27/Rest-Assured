package com.cydeo.day11;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class P03_MovieXML{


    @Test
    public void test1() {

        Response response = given().queryParam("t", "Superman")
                .queryParam("r", "xml")
                .queryParam("apikey", "81815fe6").
                when().get("http://www.omdbapi.com").prettyPeek();

        // Create XMLPath
        XmlPath xmlPath = response.xmlPath();


        // get me year attribute
        System.out.println(xmlPath.getString("root.movie.@year"));


        // get me movie title
        System.out.println(xmlPath.getString("root.movie.@title"));

        // get me movie genre

        System.out.println(xmlPath.getString("root.movie.@genre"));

        // get me movie writer

        System.out.println(xmlPath.getString("root.movie.@writer"));


    }

    /**
     *  HOMEWORK
     * http://www.omdbapi.com/?apikey=81815fe6&s=Batman&page=2&r=xml
     *
     * - Try to ge all year information
     */

    @Test
    public void test2() {
       given().queryParam("apikey", "81815fe6")
                .queryParam("s", "Batman")
                .queryParam("page", 2)
                .queryParam("r", "xml")
                .when().get("http://www.omdbapi.com/")//.prettyPeek()
               .xmlPath().getList("root.result.@year").forEach(System.out::println);
    }

}
