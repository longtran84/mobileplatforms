///*
// *  Copyright (C) 2018 - 2019 FINTECHVIET
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *      https://mindorks.com/license/apache-v2
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License
// */
//package vn.fintechviet.mobileplatforms;
//
//
//import org.junit.*;
//import static org.junit.Assert.*;
//
//
//public class FoobarPasswordValidatorTest {
//
//
//    private String[] invalidPasswords = {
//            "123abc#@", 		//does not have uppercase
//            "123ABC#@", 		//does not have lowercase
//            "abcABC#@", 		//does not have a digit
//            "abcABC123", 		//does not have a special character
//            "aA1#", 			//too short
//            "abCD12345678*%&/"	//too long
//    };
//
//
//    private String[] validPasswords = {
//            "123aA%&",
//            "1337(fooBar)",
//            "f00B@rAc@demy"
//    };
//
//
//
//    private FoobarPasswordValidator validator;
//
//
//    @Before
//    public void setup(){
//        validator  = new FoobarPasswordValidator();
//    }
//
//
//    @Test
//    public void testInvalid(){
//        for (String pass : invalidPasswords) {
//            assertFalse(validator.isValid(pass));
//        }
//    }
//
//
//    @Test
//    public void testValid(){
//        for (String pass : validPasswords) {
//            assertTrue(validator.isValid(pass));
//        }
//    }
//
//}