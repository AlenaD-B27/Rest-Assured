package com.cydeo.day12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class P04_CsvSource {
    @ParameterizedTest
    @CsvSource({"1,3,4",
                "2,3,5",
                "3,4,7",
                "5,6,11"})
    public void test1(int num1,int num2, int sum){
        System.out.println(num1 + " + " + num2 + " = " + sum);
        Assertions.assertEquals(sum, (num1+num2));
    }

}
