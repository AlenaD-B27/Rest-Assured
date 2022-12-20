package com.cydeo.day12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

public class P06_MethodSourceTest {

    @ParameterizedTest
    @MethodSource("getNames")
    public void test1(String name) {
        System.out.println("name = " + name);
    }

    public static List<String> getNames(){
        List<String> nameList = Arrays.asList("Kimberly", "King", "TJ", "Bond");
        return nameList;
    }


}
