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

package vn.fintechviet.mobileplatforms.application.management.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.fintechviet.mobileplatforms.application.management.data.model.others.ISpinnerData;

public class Modules implements ISpinnerData {

    @Expose
    @SerializedName("id")
    private String uuid;

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("icon")
    private String icon;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public Long getId() {
        try {
            return Long.parseLong(uuid);
        } catch (Exception exception) {
            return new Long(0);
        }
    }
}
