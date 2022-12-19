package com.cydeo.day09;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
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


        // get me year title
        System.out.println(xmlPath.getString("root.movie.@title"));

        // get me year genre

        // get me year writer




    }
}
