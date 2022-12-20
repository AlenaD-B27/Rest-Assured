package com.cydeo.day12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class P02_Junit5Assertions {

    /**
     *
     * HARD ASSERT --> ASSERT
     *  - Test Execution will be aborted if the assert condition is not met
     *  - Rest of execution will stop
     *
     *  - Use Case --> if we are checking critical functionality of the app we can check with hard asssert to continue next steps
     *
     *
     *
     */

    @Test
    public  void hardAssert() {

        assertEquals(10,5+5);
        System.out.println("---- First Assert is DONE ");
        assertEquals(9,5+5);
        System.out.println("---- Second Assert is DONE");
        assertEquals(10,5+5);
        System.out.println("---- Third Assert is DONE");

    }


    /**
     *
     * SOFT ASSERT --> VERIFY (Soft Assertion is implementation of VERIFY )
     * - Test Execution will continue till end of the code fragment even if one the assertion is failing
     *
     *
     *     -------  TESTNG --> SoftAssert softAssert=new SoftAssert() ----------
     *                  softAssert.assertEquals()..
     *                  softAssert.assertAll()
     *
     *   ---------------------------------------------
     */

    @DisplayName("JUNIT5 SOFT ASSERTION IS IMPLEMENTED")
    @Test
    public  void softAssert() {

        assertAll("Learning Soft Assert",
                () -> assertEquals(10, 5+5),
                () -> assertEquals(9, 5+5),
                () -> assertEquals(9, 5+5)
        );

    }


}