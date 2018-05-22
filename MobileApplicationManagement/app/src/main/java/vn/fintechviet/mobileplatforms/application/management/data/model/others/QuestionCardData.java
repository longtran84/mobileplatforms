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

package vn.fintechviet.mobileplatforms.application.management.data.model.others;

import vn.fintechviet.mobileplatforms.application.management.data.model.db.Option;
import vn.fintechviet.mobileplatforms.application.management.data.model.db.Question;

import java.util.List;

/**
 * Created by amitshekhar on 09/07/17.
 */

public class QuestionCardData {

    public boolean mShowCorrectOptions;

    public List<Option> options;

    public Question question;

    public QuestionCardData(Question question, List<Option> options) {
        this.question = question;
        this.options = options;
    }
}