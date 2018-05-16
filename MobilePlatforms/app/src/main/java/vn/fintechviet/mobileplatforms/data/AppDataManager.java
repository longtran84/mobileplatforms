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
package vn.fintechviet.mobileplatforms.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import vn.fintechviet.mobileplatforms.data.local.db.DbHelper;
import vn.fintechviet.mobileplatforms.data.local.prefs.PreferencesHelper;
import vn.fintechviet.mobileplatforms.data.model.api.AccountBalanceResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ChangePasswordRequest;
import vn.fintechviet.mobileplatforms.data.model.api.ChangePasswordResponse;
import vn.fintechviet.mobileplatforms.data.model.api.CheckUpdateRequest;
import vn.fintechviet.mobileplatforms.data.model.api.CheckUpdateResponse;
import vn.fintechviet.mobileplatforms.data.model.api.ForgotPasswordResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LoginRequest;
import vn.fintechviet.mobileplatforms.data.model.api.LoginResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LogoutResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LookupDocumentResponse;
import vn.fintechviet.mobileplatforms.data.model.api.LookupRegisterRequest;
import vn.fintechviet.mobileplatforms.data.model.api.LookupRegisterResponse;
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
import vn.fintechviet.mobileplatforms.data.model.db.Option;
import vn.fintechviet.mobileplatforms.data.model.db.Question;
import vn.fintechviet.mobileplatforms.data.model.db.User;
import vn.fintechviet.mobileplatforms.data.model.others.QuestionCardData;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.data.remote.ApiHeader;
import vn.fintechviet.mobileplatforms.data.remote.ApiHelper;
import vn.fintechviet.mobileplatforms.data.remote.VietnamStateTreasuryService;
import vn.fintechviet.mobileplatforms.utils.AppConstants;
import vn.fintechviet.mobileplatforms.utils.CommonUtils;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by long_tran on 07/07/17.
 */
@Singleton
public class AppDataManager implements DataManager {

    private final VietnamStateTreasuryService mVietnamStateTreasuryService;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper,
                          VietnamStateTreasuryService vietnamStateTreasuryService, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mVietnamStateTreasuryService = vietnamStateTreasuryService;
        mGson = gson;
    }

    @Override
    public Single<RegisterDeviceResponse> doRegisterDevice(DeviceInfoPayload deviceInfoPayload) {
        return mVietnamStateTreasuryService.doRegisterDevice(deviceInfoPayload);
    }

    @Override
    public Single<JsonObject> sources() {
        return mVietnamStateTreasuryService.sources();
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return null;
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return null;
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return null;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mVietnamStateTreasuryService.doServerLoginApiCall(request);
    }

    @Override
    public Single<ChangePasswordResponse> doServerChangePasswordApiCall(ChangePasswordRequest request) {
        return mVietnamStateTreasuryService.doServerChangePasswordApiCall(request);
    }

    @Override
    public Single<ProfileResponse> doServerUserProfileApiCall(String userName) {
        return mVietnamStateTreasuryService.doServerUserProfileApiCall(userName);
    }

    @Override
    public Single<OBRResponse> doServerOBRApiCall(String userName) {
        return mVietnamStateTreasuryService.doServerOBRApiCall(userName);
    }

    @Override
    public Single<ForgotPasswordResponse> doServerForgotPasswordApiCall(String userName) {
        return mVietnamStateTreasuryService.doServerForgotPasswordApiCall(userName);
    }

    @Override
    public Single<MessagesResponse> doServerMessagesApiCall(String userName) {
        return mVietnamStateTreasuryService.doServerMessagesApiCall(userName);
    }

    @Override
    public Single<ModulesResponse> doServerModuleApiCall() {
        return mVietnamStateTreasuryService.doServerModuleApiCall();
    }

    @Override
    public Single<CheckUpdateResponse> doServerCheckUpdateApiCall(CheckUpdateRequest checkUpdateRequest) {
        return mVietnamStateTreasuryService.doServerCheckUpdateApiCall(checkUpdateRequest);
    }

    @Override
    public Single<RegisterAccountResponse> doServerRegisterAccountApiCall(RegisterAccountRequest registerAccountRequest) {
        return mVietnamStateTreasuryService.doServerRegisterAccountApiCall(registerAccountRequest);
    }

    @Override
    public Single<UserModulesResponse> doServerUserModuleApiCall(String userName) {
        return mVietnamStateTreasuryService.doServerUserModuleApiCall(userName);
    }

    @Override
    public Single<AccountBalanceResponse> doServerLookupAccountBalanceApiCall(String fromDate,
                                                                              String toDate, String userName) {
        return mVietnamStateTreasuryService.doServerLookupAccountBalanceApiCall(fromDate, toDate, userName);
    }

    @Override
    public Single<LookupDocumentResponse> doServerLookupDocumentApiCall(String fromDate, String toDate, String userName) {
        return mVietnamStateTreasuryService.doServerLookupDocumentApiCall(fromDate, toDate, userName);
    }

    @Override
    public Single<ReminderRegisterResponse> doServerReminderRegisterApiCall(String userName) {
        return mVietnamStateTreasuryService.doServerReminderRegisterApiCall(userName);
    }

    @Override
    public Single<LookupRegisterResponse> doServerLookupRegisterApiCall(LookupRegisterRequest lookupRegisterRequest) {
        return mVietnamStateTreasuryService.doServerLookupRegisterApiCall(lookupRegisterRequest);
    }

    @Override
    public Single<ProcessRegisterResponse> doServerProcessRegisterApiCall(ProcessRegisterRequest processRegisterRequest) {
        return mVietnamStateTreasuryService.doServerProcessRegisterApiCall(processRegisterRequest);
    }

    @Override
    public Single<UpdateStatusRegisterResponse> doServerUpdateStatusRegisterApiCall(UpdateStatusRegisterRequest updateStatusRegisterRequest) {
        return mVietnamStateTreasuryService.doServerUpdateStatusRegisterApiCall(updateStatusRegisterRequest);
    }

    @Override
    public String getAuthorization() {
        return mPreferencesHelper.getAuthorization();
    }

    @Override
    public void setAuthorization(String authorization) {
        mPreferencesHelper.setAuthorization(authorization);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public String getFireBaseCloudMessagingToken() {
        return mPreferencesHelper.getFireBaseCloudMessagingToken();
    }

    @Override
    public void setFireBaseCloudMessagingToken(String token) {
        mPreferencesHelper.setFireBaseCloudMessagingToken(token);
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public ApiHeader getApiHeader() {
        return null;
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void setFingerprintDataEncrypt(String fingerprintDataEncrypt) {
        mPreferencesHelper.setFingerprintDataEncrypt(fingerprintDataEncrypt);
    }

    @Override
    public String getFingerprintDataEncrypt() {
        return mPreferencesHelper.getFingerprintDataEncrypt();
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return null;
    }

    @Override
    public Observable<List<Option>> getOptionsForQuestionId(Long questionId) {
        return mDbHelper.getOptionsForQuestionId(questionId);
    }

    @Override
    public Observable<List<QuestionCardData>> getQuestionCardData() {
        return mDbHelper.getAllQuestions()
                .flatMap(questions -> Observable.fromIterable(questions))
                .flatMap(question -> Observable.zip(
                        mDbHelper.getOptionsForQuestionId(question.id),
                        Observable.just(question),
                        (options, question1) -> new QuestionCardData(question1, options)))
                .toList()
                .toObservable();
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {
        return mDbHelper.isOptionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        Type type = new TypeToken<List<Option>>() {
                        }.getType();
                        List<Option> optionList = mGson.fromJson(CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_OPTIONS), type);
                        return saveOptionList(optionList);
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {
        return mDbHelper.isQuestionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, Question.class);
                        List<Question> questionList = mGson
                                .fromJson(CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS), type);
                        return saveQuestionList(questionList);
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {

    }

    @Override
    public void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);

        updateApiHeader(userId, accessToken);
    }
}
