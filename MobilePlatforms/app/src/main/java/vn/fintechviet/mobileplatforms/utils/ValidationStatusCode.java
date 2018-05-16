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

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Enum representing an status code
 *
 * @author long_tran
 */
public enum ValidationStatusCode {

    Continue(1, "Continue", "The client should continue with its request."),
    Unknown(2, "Unknown HTTP Status Code", "Unknown or unsupported HTTP status code");

    private final int code;
    private final String name;
    private final String description;

    /**
     *
     * @param code
     * @param name
     * @param description
     */
    private ValidationStatusCode(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the int status code this enum represents
     *
     * @return the int status code this enum represents
     */
    public final int getCode() {
        return code;
    }

    /**
     * Returns the name of the HTTP status this enum represents
     *
     * @return the name of the HTTP status this enum represents
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns a description of the HTTP status this enum represents
     *
     * @return a description of the HTTP status this enum represents
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Returns the HttpStatus object with a code matching the supplied int
     *
     * @param code
     *            the Status code
     * @return the HttpStatus object with a code matching the supplied int
     */
    public static ValidationStatusCode getByCode(int code) {
        for (ValidationStatusCode status : ValidationStatusCode.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return Unknown;
    }

}
