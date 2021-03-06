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

/**
 * Created by long_tran on 07/07/17.
 */

public final class LoginResponse {

    @Expose
    @SerializedName("authenticated")
    private boolean authenticated;

    @Expose
    @SerializedName("authorization")
    private String authorization;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Expose
    @SerializedName("email")
    private String userEmail;

    @Expose
    @SerializedName("user_id")
    private Long userId;

    @Expose
    @SerializedName("username")
    private String userName;

    @Expose
    @SerializedName("apiCodes")
    private List<String> apiCodes;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        LoginResponse that = (LoginResponse) object;

        if (statusCode != null ? !statusCode.equals(that.statusCode) : that.statusCode != null) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (authorization != null ? !authorization.equals(that.authorization) : that.authorization != null) {
            return false;
        }
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
            return false;
        }
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) {
            return false;
        }
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (authorization != null ? authorization.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getApiCodes() {
        return apiCodes;
    }

    public void setApiCodes(List<String> apiCodes) {
        this.apiCodes = apiCodes;
    }
}
