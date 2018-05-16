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

package vn.fintechviet.mobileplatforms.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.fintechviet.mobileplatforms.data.model.others.ISpinnerData;

public class OBR implements ISpinnerData {

    @Expose
    @SerializedName("id")
    private String uuid;

    @Expose
    @SerializedName("maQhns")
    private String organizationalBudgetRelationshipCode;

    @Expose
    @SerializedName("tenQhns")
    private String organizationalBudgetRelationshipName;

    @Expose
    @SerializedName("diachi")
    private String organizationalBudgetRelationshipAddress;

    @Expose
    @SerializedName("dienthoai")
    private String organizationalBudgetRelationshipTelephone;

    @Expose
    @SerializedName("fax")
    private String organizationalBudgetRelationshipFax;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrganizationalBudgetRelationshipCode() {
        return organizationalBudgetRelationshipCode;
    }

    public void setOrganizationalBudgetRelationshipCode(String organizationalBudgetRelationshipCode) {
        this.organizationalBudgetRelationshipCode = organizationalBudgetRelationshipCode;
    }

    public String getOrganizationalBudgetRelationshipName() {
        return organizationalBudgetRelationshipName;
    }

    public void setOrganizationalBudgetRelationshipName(String organizationalBudgetRelationshipName) {
        this.organizationalBudgetRelationshipName = organizationalBudgetRelationshipName;
    }

    public String getOrganizationalBudgetRelationshipAddress() {
        return organizationalBudgetRelationshipAddress;
    }

    public void setOrganizationalBudgetRelationshipAddress(String organizationalBudgetRelationshipAddress) {
        this.organizationalBudgetRelationshipAddress = organizationalBudgetRelationshipAddress;
    }

    public String getOrganizationalBudgetRelationshipTelephone() {
        return organizationalBudgetRelationshipTelephone;
    }

    public void setOrganizationalBudgetRelationshipTelephone(String organizationalBudgetRelationshipTelephone) {
        this.organizationalBudgetRelationshipTelephone = organizationalBudgetRelationshipTelephone;
    }

    public String getOrganizationalBudgetRelationshipFax() {
        return organizationalBudgetRelationshipFax;
    }

    public void setOrganizationalBudgetRelationshipFax(String organizationalBudgetRelationshipFax) {
        this.organizationalBudgetRelationshipFax = organizationalBudgetRelationshipFax;
    }

    @Override
    public String getLabel() {
        return organizationalBudgetRelationshipName;
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
