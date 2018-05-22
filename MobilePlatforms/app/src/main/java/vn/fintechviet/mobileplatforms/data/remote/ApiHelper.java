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
package vn.fintechviet.mobileplatforms.data.remote;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Query;
import vn.fintechviet.mobileplatforms.data.model.api.AccountBalanceResponse;
import vn.fintechviet.mobileplatforms.data.model.api.AccountVerificationResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ChangePasswordRequest;
import vn.fintechviet.mobileplatforms.data.model.api.ChangePasswordResponse;
import vn.fintechviet.mobileplatforms.data.model.api.CheckUpdateRequest;
import vn.fintechviet.mobileplatforms.data.model.api.CheckUpdateResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ForgotPasswordResponse;
import vn.fintechviet.mobileplatforms.data.model.api.HelpResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LoginRequest;
import vn.fintechviet.mobileplatforms.data.model.api.LoginResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LogoutResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LookupDocumentResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LookupRegisterRequest;
import vn.fintechviet.mobileplatforms.data.model.api.LookupRegisterResponse;
import vn.fintechviet.mobileplatforms.data.model.api.MessagesDetailResponse;
import vn.fintechviet.mobileplatforms.data.model.api.MessagesResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ModulesResponse;
import vn.fintechviet.mobileplatforms.data.model.api.OBRResponse;
import vn.fintechviet.mobileplatforms.data.model.api.OpenSourceResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ProcessRegisterRequest;
import vn.fintechviet.mobileplatforms.data.model.api.ProcessRegisterResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ProfileResponse;
import vn.fintechviet.mobileplatforms.data.model.api.RegisterAccountRequest;
import vn.fintechviet.mobileplatforms.data.model.api.RegisterAccountResponse;
import vn.fintechviet.mobileplatforms.data.model.api.RegisterDeviceResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ReminderRegisterResponse;
import vn.fintechviet.mobileplatforms.data.model.api.UpdateStatusRegisterRequest;
import vn.fintechviet.mobileplatforms.data.model.api.UpdateStatusRegisterResponse;
import vn.fintechviet.mobileplatforms.data.model.api.UserModulesResponse;
import vn.fintechviet.mobileplatforms.data.model.api.UserProfile;
import vn.fintechviet.mobileplatforms.data.model.api.VersionManagerResponse;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfoPayload;

/**
 * Created by long_tran on 07/07/17.
 */

public interface ApiHelper {

    Single<RegisterDeviceResponse> doRegisterDevice(DeviceInfoPayload deviceInfoPayload);

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Single<ChangePasswordResponse> doServerChangePasswordApiCall(ChangePasswordRequest request);

    Single<ProfileResponse> doServerUserProfileApiCall(String userName);

    Single<OBRResponse> doServerOBRApiCall(String userName);

    Single<ForgotPasswordResponse> doServerForgotPasswordApiCall(String userName);

    Single<MessagesResponse> doServerMessagesApiCall(String userName);

    Single<ModulesResponse> doServerModuleApiCall();

    Single<CheckUpdateResponse> doServerCheckUpdateApiCall(CheckUpdateRequest checkUpdateRequest);

    Single<RegisterAccountResponse> doServerRegisterAccountApiCall(RegisterAccountRequest registerAccountRequest);

    Single<UserModulesResponse> doServerUserModuleApiCall(String userName);

    Single<AccountBalanceResponse> doServerLookupAccountBalanceApiCall(String fromDate,
                                                                       String toDate, String userName);

    Single<LookupDocumentResponse> doServerLookupDocumentApiCall(String fromDate,
                                                                 String toDate, String userName);

    Single<ReminderRegisterResponse> doServerReminderRegisterApiCall(String userName);

    Single<LookupRegisterResponse> doServerLookupRegisterApiCall(LookupRegisterRequest lookupRegisterRequest);

    Single<ProcessRegisterResponse> doServerProcessRegisterApiCall(ProcessRegisterRequest processRegisterRequest);

    Single<UpdateStatusRegisterResponse> doServerUpdateStatusRegisterApiCall(UpdateStatusRegisterRequest updateStatusRegisterRequest);

    Single<MessagesDetailResponse> doServerMessagesDetailApiCall(String id);

    Single<VersionManagerResponse> doServerVersionManagerApiCall(String appCode);

    Single<AccountVerificationResponse> doServerAccountVerificationApiCall(String serialNumber);

    Single<ProfileResponse> doServerUserProfileUpdateApiCall(UserProfile userProfile);

    Single<HelpResponse> doServerHelpApiCall();



    Single<JsonObject> sources();

    Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Single<LogoutResponse> doLogoutApiCall();

    ApiHeader getApiHeader();

    Single<OpenSourceResponse> getOpenSourceApiCall();

}
