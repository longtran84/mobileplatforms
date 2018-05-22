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
package vn.fintechviet.mobileplatforms.application.management.data.remote;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.AccountBalanceResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.AccountVerificationResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ChangePasswordRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ChangePasswordResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.CheckUpdateRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.CheckUpdateResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ForgotPasswordResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.HelpResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LoginRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LoginResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LookupDocumentResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LookupRegisterRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LookupRegisterResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.MessagesDetailResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.MessagesResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ModulesResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.OBRResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.OrganizationRelatedBudgetResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ProcessRegisterRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ProcessRegisterResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ProfileResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.RegisterAccountRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.RegisterAccountResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.RegisterDeviceResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ReminderRegisterResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.UpdateStatusRegisterRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.UpdateStatusRegisterResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.UserModulesResponse;
import vn.fintechviet.mobileplatforms.application.management.data.model.system.DeviceInfoPayload;

public interface VietnamStateTreasuryService {

    @GET("sources")
    Single<JsonObject> sources();

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_DEVICE_PUT"})
    @PUT("ftv-gateway/api")
    Single<RegisterDeviceResponse> doRegisterDevice(@Body DeviceInfoPayload deviceInfoPayload);

    @Headers("Content-Type: application/json")
    @PUT("mobile/application/wsDVQHNSList")
    Single<OrganizationRelatedBudgetResponse> doOrganizationRelatedBudget();

    @Headers("Content-Type: application/json")
    @POST("ftv-gateway/api/system/authentication")
    Single<LoginResponse> doServerLoginApiCall(@Body LoginRequest.ServerLoginRequest request);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_POST"})
    @POST("ftv-gateway/api")
    Single<ChangePasswordResponse> doServerChangePasswordApiCall(@Body ChangePasswordRequest request);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_SYS_USER_INFO_GET"})
    @GET("ftv-gateway/api")
    Single<ProfileResponse> doServerUserProfileApiCall(@Query("userName") String userName);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_DVQHNS_LIST_GET"})
    @GET("ftv-gateway/api")
    Single<OBRResponse> doServerOBRApiCall(@Query("userName") String userName);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_RESET_PASSWORD_GET"})
    @GET("ftv-gateway/api")
    Single<ForgotPasswordResponse> doServerForgotPasswordApiCall(@Query("userName") String userName);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_SYS_USER_NOTIFICATION_LIST_GET"})
    @GET("ftv-gateway/api")
    Single<MessagesResponse> doServerMessagesApiCall(@Query("userName") String userName);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_APPLICATION_LIST_GET"})
    @GET("ftv-gateway/api")
    Single<ModulesResponse> doServerModuleApiCall();

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_APPLICATION_POST"})
    @POST("ftv-gateway/api")
    Single<CheckUpdateResponse> doServerCheckUpdateApiCall(@Body CheckUpdateRequest checkUpdateRequest);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_REGISTER_POST"})
    @POST("ftv-gateway/api")
    Single<RegisterAccountResponse> doServerRegisterAccountApiCall(@Body RegisterAccountRequest registerAccountRequest);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_APPLICATION_LIST_GET"})
    @GET("ftv-gateway/api")
    Single<UserModulesResponse> doServerUserModuleApiCall(@Query("userName") String userName);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_SEARCH_BALANCES_GET"})
    @GET("ftv-gateway/api")
    Single<AccountBalanceResponse> doServerLookupAccountBalanceApiCall(@Query("fromDate") String fromDate,
                                                                       @Query("toDate") String toDate,
                                                                       @Query("userName") String userName);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_SEARCH_FILES_GET"})
    @GET("ftv-gateway/api")
    Single<LookupDocumentResponse> doServerLookupDocumentApiCall(@Query("fromDate") String fromDate,
                                                                 @Query("toDate") String toDate,
                                                                 @Query("userName") String userName);


    @Headers({"Content-Type: application/json", "apiCode: MOBILE_SYS_USER_REMINDER_REGISTERS_GET"})
    @GET("ftv-gateway/api")
    Single<ReminderRegisterResponse> doServerReminderRegisterApiCall(@Query("userName") String userName);


    @Headers({"Content-Type: application/json", "apiCode: MOBILE_SYS_USER_SEARCH_REGISTER_POST"})
    @POST("ftv-gateway/api")
    Single<LookupRegisterResponse> doServerLookupRegisterApiCall(@Body LookupRegisterRequest lookupRegisterRequest);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_SYS_USER_SEARCH_TO_PROCESS_REGISTER_POST"})
    @POST("ftv-gateway/api")
    Single<ProcessRegisterResponse> doServerProcessRegisterApiCall(@Body ProcessRegisterRequest processRegisterRequest);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_SYS_USER_PROCESS_REGISTER_POST"})
    @POST("ftv-gateway/api")
    Single<UpdateStatusRegisterResponse> doServerUpdateStatusRegisterApiCall(@Body UpdateStatusRegisterRequest updateStatusRegisterRequest);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_DEVICE_CHECK_STATUS_GET"})
    @GET("ftv-gateway/api")
    Single<AccountVerificationResponse> doServerAccountVerificationApiCall(@Query("serialNumber") String serialNumber);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_NOTIFICATION_DETAIL_GET"})
    @GET("ftv-gateway/api")
    Single<MessagesDetailResponse> doServerMessagesDetailApiCall(@Query("id") String id);

    @Headers({"Content-Type: application/json", "apiCode: MOBILE_USER_GUIDELINES_GET"})
    @GET("ftv-gateway/api")
    Single<HelpResponse> doServerHelpApiCall();
}
