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

import java.io.Serializable;
import java.util.List;

public class RegisterAccountRequest implements Serializable {

    @Expose
    @SerializedName("orgName")
    private String organizationName;

    @Expose
    @SerializedName("orgEmail")
    private String organizationEmail;

    @Expose
    @SerializedName("orgAddress")
    private String organizationAddress;

    @Expose
    @SerializedName("orgTel")
    private String organizationTelephone;

    @Expose
    @SerializedName("fullName")
    private String fullName;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("mobile")
    private String mobile;

    @Expose
    @SerializedName("userName")
    private String userName;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("appIds")
    private List<String> listModule;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationEmail() {
        return organizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationTelephone() {
        return organizationTelephone;
    }

    public void setOrganizationTelephone(String organizationTelephone) {
        this.organizationTelephone = organizationTelephone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getListModule() {
        return listModule;
    }

    public void setListModule(List<String> listModule) {
        this.listModule = listModule;
    }
}
