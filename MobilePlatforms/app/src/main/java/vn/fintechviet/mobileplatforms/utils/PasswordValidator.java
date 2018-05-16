/*
 *  Copyright (C) 2018 - 2019 FINTECHVIET
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package vn.fintechviet.mobileplatforms.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class PasswordValidator {

    private Pattern pattern;
    private Matcher matcher;
    /**
     * (			# Start of group
     * (?=.*\d)		#   must contains one digit from 0-9
     * (?=.*[a-z])		#   must contains one lowercase characters
     * (?=.*[A-Z])		#   must contains one uppercase characters
     * (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
     * .		#     match anything with previous condition checking
     * {6,20}	#        length at least 6 characters and maximum of 20
     * )			# End of group
     */
    private static final String PASSWORD_PATTERN =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};’:”\\\\|,.<>\\/?]).{8,20}$";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     *
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password) {
        //todo
//        matcher = pattern.matcher(password);
//        return matcher.matches();
        return true;
    }
}
