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

package vn.fintechviet.mobileplatforms.application.management.utils;

/**
 * Created by long_tran on 07/07/17.
 */

public final class AppConstants {

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "fintechviet_mvvm.db";

    public static final long NULL_INDEX = -1L;

    public static final String PREF_NAME = "fintechviet_pref";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";

    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final int TYPE_HOME_FRAGMENT = 101;

    public static final int TYPE_MESSAGE_FRAGMENT = 102;

    public static final int TYPE_CHANGE_PASSWORD_FRAGMENT = 103;

    public static final int TYPE_PROFILE_FRAGMENT = 104;

    public static final int TYPE_REMINDER_FRAGMENT = 105;

    public static final int TYPE_REGISTRATION_PROCESSING = 106;

    public static final int TYPE_REGISTRATION_LOOKUP = 107;

    public static final int TYPE_HELP = 109;

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
