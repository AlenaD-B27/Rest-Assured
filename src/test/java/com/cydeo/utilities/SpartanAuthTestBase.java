package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class SpartanAuthTestBase {
    @BeforeAll
    public static void init(){
        RestAssured.baseURI="http://34.204.7.162:7000";
    }
}
