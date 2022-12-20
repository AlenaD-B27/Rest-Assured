package com.cydeo.day12;

import com.cydeo.utilities.ExcelUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class P06_MethodSourceTest {

    @ParameterizedTest
    @MethodSource("getNames")
    public void test1(String name) {
        System.out.println("name = " + name);
    }


    @ParameterizedTest
    @MethodSource("getExcelData")
    public void credentialsTest(Map<String,String> userInfo){
        System.out.println(userInfo);
        System.out.println("userInfo.get(\"Email\") = " + userInfo.get("Email"));
        System.out.println("userInfo.get(\"Password\") = " + userInfo.get("Password"));
        System.out.println("-----------------------------------------");
    }



    public static List<String> getNames(){
        List<String> nameList = Arrays.asList("Kimberly", "King", "TJ", "Bond");
        return nameList;
    }

    public static List<Map<String,String>> getExcelData() {

        ExcelUtil library = new ExcelUtil("src/test/resources/Library.xlsx", "Library1-short");

        return library.getDataList();
    }


}
