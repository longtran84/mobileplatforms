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

import java.util.List;

public class OrganizationRelatedBudgetResponse extends AbstractMessageResponse {

    @Expose
    @SerializedName("data")
    private List<OrganizationRelatedBudget> listOrganizationRelatedBudget;

    public List<OrganizationRelatedBudget> getListOrganizationRelatedBudget() {
        return listOrganizationRelatedBudget;
    }

    public void setListOrganizationRelatedBudget(List<OrganizationRelatedBudget> listOrganizationRelatedBudget) {
        this.listOrganizationRelatedBudget = listOrganizationRelatedBudget;
    }
}
