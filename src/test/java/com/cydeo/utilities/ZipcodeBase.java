package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class ZipcodeBase {
    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://api.zippopotam.us";
    }
}
