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

package vn.fintechviet.mobileplatforms.application.management.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by long_tran on 08/07/17.
 */
@Entity(tableName = "questions")
public class Question {

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String createdAt;

    @Expose
    @PrimaryKey
    public Long id;

    @Expose
    @SerializedName("question_img_url")
    @ColumnInfo(name = "question_img_url")
    public String imgUrl;

    @Expose
    @SerializedName("question_text")
    @ColumnInfo(name = "question_text")
    public String questionText;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String updatedAt;
}
