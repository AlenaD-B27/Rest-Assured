package com.cydeo.office_hour.day01;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import com.cydeo.utilities.SpartanUtil;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpartanFlow extends SpartanTestBase {
    /*
    - POST     /api/spartans
            Create a spartan Map
                name = "API Flow POST"
                gender="Male"
                phone=1231231231l
            - verify status code 201
            - message is "A Spartan is Born!"
            - take spartanID from response and save as a global  variable
     */

    static int createdSpartanId;
    static Spartan spartan;

    static Spartan updatedSpartan;

    @Order(value = 1)
    @Test
    void post() {

        spartan = SpartanUtil.createSpartan();
        Response response = SpartanUtil.postSpartan(spartan);
        Assertions.assertEquals(201,response.statusCode());
        Assertions.assertEquals("A Spartan is Born!",response.jsonPath().getString("success"));
        createdSpartanId = response.jsonPath().getInt("data.id");

    }
    
    /*
     - GET  Spartan with spartanID     /api/spartans/{id}
             - verify status code 200
             - verify name is API Flow POST
     */

    @Order(value = 2)
    @Test
    void getCreatedSpartan() {
        Response response = SpartanUtil.getSpartan(createdSpartanId);
        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertEquals(spartan.getName(),response.jsonPath().getString("name"));
    }

    /*  - PUT  Spartan with spartanID    /api/spartans/{id}
             Create a spartan Map
                name = "PUT Flow"
                - verify status code 204
     */


    @Order(value = 3)
    @Test
    void put() {

        updatedSpartan = new Spartan();

        updatedSpartan.setName("Put " + spartan.getName());
        updatedSpartan.setGender(spartan.getGender());
        updatedSpartan.setPhone(spartan.getPhone());

        Response response = SpartanUtil.updateSpartan(createdSpartanId, updatedSpartan);

        Assertions.assertEquals(204,response.statusCode());
    }

    @Order(value = 4)
    @Test
    void getUpdatedSpartan() {
        Response response = SpartanUtil.getSpartan(createdSpartanId);

        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertEquals(updatedSpartan.getName(),response.jsonPath().getString("name"));

    }


    @Order(value = 5)
    @Test
    void delete() {

        Response response = SpartanUtil.deleteSpartan(createdSpartanId);
        Assertions.assertEquals(204, response.statusCode());


    }

    @Order(value = 6)
    @Test
    void getDeletedSpartan() {
        Response response = SpartanUtil.getSpartan(createdSpartanId);
        Assertions.assertEquals(404,response.statusCode());
    }
}
