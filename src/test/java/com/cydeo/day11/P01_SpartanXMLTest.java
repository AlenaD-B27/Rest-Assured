package com.cydeo.day11;

import com.cydeo.utilities.SpartanAuthTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class P01_SpartanXMLTest extends SpartanAuthTestBase {

    /**
     * Given accept type is application/xml
     * When send the request /api/spartans
     * Then status code is 200
     * And content type is application/xml
     *   print firstname
     *   .....
     *   ...
     */


    @Test
    public void test1() {

        given().accept(ContentType.XML)
                .auth().basic("admin", "admin")
                .when().get("/api/spartans")//.prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.XML)
                .body("List.item[0].name", is("Meade"))
                .body("List.item[0].gender", is("Male"));

    }

    @DisplayName("GET /api/spartans with using XMLPath")
    @Test
    public void test2() {

        Response response = given().accept(ContentType.XML)
                .auth().basic("admin", "admin").
                when().get("/api/spartans").prettyPeek();

        XmlPath xmlPath = response.xmlPath();

        System.out.println("1st Spartan name = " + xmlPath.getString("List.item[0].name"));
        System.out.println("3rd Spartan name = " + xmlPath.getString("List.item[2].name"));
        System.out.println("Last Spartan name = " + xmlPath.getString("List.item[-1].name"));
        System.out.println("All Spartans names = " + xmlPath.getList("List.item.name"));
        System.out.println("Amount of Spartans = " + xmlPath.getList("List.item").size());


    }
}
