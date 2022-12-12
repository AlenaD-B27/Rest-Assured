package com.cydeo.day06_practice;

import com.cydeo.pojo.Driver;
import com.cydeo.pojo.FormulaConstructor;
import com.cydeo.pojo.MRData;
import com.cydeo.utilities.ErgastBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class ErgastHomework extends ErgastBase {


    @Test
    public void test1_jsonPath(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json");

        JsonPath jsonPath = response.jsonPath();

//                - Then verify status code is 200
        assertEquals(200,response.statusCode());
//                - And content type is application/json; charset=utf-8
        assertEquals("application/json; charset=utf-8",response.contentType());
//                - And total is 1
        assertEquals(1, jsonPath.getInt("MRData.total"));
//                - And givenName is Fernando
        assertEquals("Fernando",jsonPath.getString("MRData.DriverTable.Drivers[0].givenName"));
//                - And familyName is Alonso
        assertEquals("Alonso",jsonPath.getString("MRData.DriverTable.Drivers[0].familyName"));
//                - And nationality is Spanish
        assertEquals("Spanish",jsonPath.getString("MRData.DriverTable.Drivers[0].nationality"));
    }

    @Test
    public void test1_hamcrestMatchers(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json
        given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
//                - Then verify status code is 200
                .then()
                .statusCode(200)
//                - And content type is application/json; charset=utf-8
                .and().contentType("application/json; charset=utf-8")
//                - And total is 1
                .and().body("MRData.total", is("1"),
//                - And givenName is Fernando
                "MRData.DriverTable.Drivers[0].givenName", is("Fernando"),
//                - And familyName is Alonso
                "MRData.DriverTable.Drivers[0].familyName", is("Alonso"),
//                - And nationality is Spanish
                "MRData.DriverTable.Drivers[0].nationality", is("Spanish"));


    }

    @Test
    public void test1_javaStructure(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json
//                - Then verify status code is 200
//                - And content type is application/json; charset=utf-8
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
                .then()
                .statusCode(200)
                .and().contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();

        Map<String,Object> alonsoMap = jsonPath.getMap("MRData.DriverTable.Drivers[0]");

        Map<String,Object> requirements = new HashMap<>();
        requirements.put("total",1);
        requirements.put("givenName","Fernando");
        requirements.put("familyName","Alonso");
        requirements.put("nationality","Spanish");
//                - And total is 1
//                - And givenName is Fernando
//                - And familyName is Alonso
//                - And nationality is Spanish
        alonsoMap.keySet().forEach(eachKey -> {
            if(requirements.containsKey(eachKey)){
                assertEquals(requirements.get(eachKey),alonsoMap.get(eachKey));
            }
        });
    }

    @Test
    public void test1_POJO(){
//        - Given accept type is json
//        - And path param driverId is alonso
//                - When user send request /drivers/{driverId}.json
//                - Then verify status code is 200
//                - And content type is application/json; charset=utf-8
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().pathParam("driverId", "alonso")
                .when().get("/drivers/{driverId}.json")
                .then()
                .statusCode(200)
                .and().contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();

        Driver driver = jsonPath.getObject("MRData.DriverTable.Drivers[0]", Driver.class);
        MRData mrData = jsonPath.getObject("MRData",MRData.class);

//                - And total is 1
        assertEquals(1, mrData.getTotal());
//                - And givenName is Fernando
        assertEquals("Fernando",driver.getGivenName());
//                - And familyName is Alonso
        assertEquals("Alonso", driver.getFamilyName());
//                - And nationality is Spanish
        assertEquals("Spanish", driver.getNationality());
    }

    @Test
    public void test2_jsonPath(){
//        - Given accept type is json
//        - When user send request /constructorStandings/1/constructors.json
        Response response = given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json");

        JsonPath jsonPath = response.jsonPath();

//                -Then verify status code is 200
        assertEquals(200,response.statusCode());
//                - And content type is application/json; charset=utf-8
        assertEquals("application/json; charset=utf-8",response.contentType());
//                - And total is 17
        assertEquals(17,jsonPath.getInt("MRData.total"));
//                - And limit is 30
        assertEquals(30,jsonPath.getInt("MRData.limit"));

        List<Map<String, Object>> allConstructors = jsonPath.getList("MRData.ConstructorTable.Constructors");
        List<String> allConstructorsIds = jsonPath.getList("MRData.ConstructorTable.Constructors.constructorId");
        List<String> allConstructorsNames = jsonPath.getList("MRData.ConstructorTable.Constructors.name");

//                - And each constructor has constructorId
        assertThat(allConstructorsIds,everyItem(notNullValue()));
//                - And constructor has name
        assertThat(allConstructorsNames,everyItem(notNullValue()));

//                - And size of constructor is 17 (using with hasSize)
        assertThat(allConstructors,hasSize(17));

//        - And constructor IDs has “benetton”, “mercedes”,”team_lotus”
        assertThat(allConstructorsIds, hasItems("benetton", "mercedes", "team_lotus"));
//        - And names are in same order by following list

        List<String> names = Arrays.asList("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams");

        assertThat(allConstructorsNames,equalTo(names));
    }

    @Test
    public void test2_hamcrestMatches(){
//        - Given accept type is json
//        - When user send request /constructorStandings/1/constructors.json
        given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json")
                .then()
//                -Then verify status code is 200
                .and().statusCode(200)
//                - And content type is application/json; charset=utf-8
                .and().contentType("application/json; charset=utf-8")
//                - And total is 17
                .and().body("MRData.total", is("17"))
//                - And limit is 30
                .and().body("MRData.limit",is("30"))
//                - And each constructor has constructorId
                .and().body("MRData.ConstructorTable.Constructors.constructorId", everyItem(notNullValue()))
//                - And constructor has name
                .and().body("MRData.ConstructorTable.Constructors.name", everyItem(notNullValue()))
//                - And size of constructor is 17 (using with hasSize)
                .and().body("MRData.ConstructorTable.Constructors",hasSize(17))
//                - And constructor IDs has “benetton”, “mercedes”,”team_lotus”
                .and().body("MRData.ConstructorTable.Constructors.constructorId", hasItems("benetton", "mercedes", "team_lotus"))
//                - And names are in same order by following list
                .and().body("MRData.ConstructorTable.Constructors.name", equalTo(Arrays.asList("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams")));


    }

    @Test
    public void test2_javaStructure(){
//        - Given accept type is json
//        - When user send request /constructorStandings/1/constructors.json
//                -Then verify status code is 200
//                - And content type is application/json; charset=utf-8
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();


        Map<String, Object> mrData = jsonPath.getMap("MRData");
//                - And total is 17
//                - And limit is 30
        assertEquals("17", mrData.get("total"));
        assertEquals("30",mrData.get("limit"));

        List<Map<String, Object>> allConstructors = jsonPath.getList("MRData.ConstructorTable.Constructors");
//                - And each constructor has constructorId
//                - And constructor has name

        allConstructors.forEach(p -> {
            assertTrue(p.containsKey("constructorId"));
            assertTrue(p.containsKey("name"));
        });

//                - And size of constructor is 17
        assertThat(allConstructors,hasSize(17));

//        - And constructor IDs has “benetton”, “mercedes”,”team_lotus”
        List<String> expectedIds = new ArrayList<>(Arrays.asList("benetton", "mercedes", "team_lotus"));
        List<String> actualIds = new ArrayList<>();
        allConstructors.forEach(p -> actualIds.add((String) p.get("constructorId")));
        expectedIds.forEach(p ->{
            actualIds.forEach(s -> {
                if (s.equals(p)) {
                    assertEquals(p, s);
                }
            }); });
//        - And names are in same order by following list

        List<String> expectedNames = Arrays.asList("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams");

        List<String> actualNames = jsonPath.getList("MRData.ConstructorTable.Constructors.name");
        assertEquals(expectedNames,actualNames);
    }


    @Test
    public void test2_POJO(){
//        - Given accept type is json
//        - When user send request /constructorStandings/1/constructors.json -
//                Then verify status code is 200
//                - And content type is application/json; charset=utf-8
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .when().get("/constructorStandings/1/constructors.json")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract().response().jsonPath();

//                - And total is 17
        MRData mrData = jsonPath.getObject("MRData",MRData.class);
        assertEquals(17, mrData.getTotal());
//                - And limit is 30
        assertEquals(30, mrData.getLimit());
//                - And each constructor has constructorId

        List<FormulaConstructor> allConstructors = jsonPath.getList("MRData.ConstructorTable.Constructors", FormulaConstructor.class);

        allConstructors.forEach(p -> assertThat(p.getConstructorId(),notNullValue()));

//        - And constructor has name
        allConstructors.forEach(p -> assertNotNull(p.getName()));
//                - And size of constructor is 17 (using with hasSize)

        assertThat(allConstructors,hasSize(17));
//        - And constructor IDs has “benetton”, “mercedes”,”team_lotus”
        List<String> expectedIds = new ArrayList<>(Arrays.asList("benetton", "mercedes", "team_lotus"));
        List<String> actualIds = new ArrayList<>();
        allConstructors.forEach(p -> actualIds.add(p.getConstructorId()));
        assertTrue(actualIds.containsAll(expectedIds));
//        - And names are in same order by following list

        List<String> expectedNames = Arrays.asList("Benetton", "Brabham-Repco", "Brawn", "BRM", "Cooper-Climax", "Ferrari", "Lotus-Climax", "Lotus-Ford", "Matra-Ford", "McLaren", "Mercedes", "Red Bull", "Renault", "Team Lotus", "Tyrrell", "Vanwall", "Williams");

        List<String> actualNames = new ArrayList<>();
        allConstructors.forEach(p -> actualNames.add(p.getName()));

        assertEquals(expectedNames, actualNames);
    }



}
