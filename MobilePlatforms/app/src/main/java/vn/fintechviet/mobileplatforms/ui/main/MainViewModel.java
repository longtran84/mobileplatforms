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

package vn.fintechviet.mobileplatforms.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.UserProfile;
import vn.fintechviet.mobileplatforms.data.model.others.QuestionCardData;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    private final ObservableField<String> appVersion = new ObservableField<>();

    private final MutableLiveData<UserProfile> userProfileData;

    private final ObservableList<QuestionCardData> questionDataList = new ObservableArrayList<>();

    private final ObservableField<String> userEmail = new ObservableField<>();

    private final ObservableField<String> userName = new ObservableField<>();

    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();

    private int action = NO_ACTION;

    /**
     * @param dataManager
     * @param schedulerProvider
     */
    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        userProfileData = new MutableLiveData<>();
        userProfileData.postValue(new UserProfile());
    }

    public int getAction() {
        return action;
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public MutableLiveData<UserProfile> getUserProfileData() {
        return userProfileData;
    }

    public ObservableList<QuestionCardData> getQuestionDataList() {
        return questionDataList;
    }

    public void setQuestionDataList(List<QuestionCardData> questionCardDatas) {
        action = ACTION_ADD_ALL;
        questionDataList.clear();
        questionDataList.addAll(questionCardDatas);
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    /**
     *
     */
    public void dataFetching(DeviceInfoPayload deviceInfoPayload) {
        getCompositeDisposable().add(getDataManager()
                .doServerAccountVerificationApiCall(deviceInfoPayload.getSerialNumber())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(accountVerificationResponse -> {
                    if (accountVerificationResponse != null && accountVerificationResponse.getData() != null) {
                        Boolean accountVerification = accountVerificationResponse.getData();
                        if (accountVerification.booleanValue()) {
                            getNavigator().accountActive();
                        } else {
                            getNavigator().accountSuspension();
                        }
                    }
                    setIsLoading(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }

    /**
     *
     */
    public void loadUserProfile() {
        String accessToken = getDataManager().getAccessToken().trim();
        getCompositeDisposable().add(getDataManager()
                .doServerUserProfileApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(profileResponse -> {
                    if (profileResponse != null) {
                        UserProfile userProfile = profileResponse.getData();
                        userProfileData.setValue(userProfile);
                        //action = ACTION_ADD_ALL;
                        //questionCardData.setValue(questionList);
                    }
                }, throwable -> {

                }));
    }

    public void logout() {
//        setIsLoading(true);
        getDataManager().setAuthorization(null);
        getNavigator().openLoginActivity();
//        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
//                .doOnSuccess(response -> getDataManager().setUserAsLoggedOut())
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(response -> {
//                    setIsLoading(false);
//                    getNavigator().openLoginActivity();
//                }, throwable -> {
//                    setIsLoading(false);
//                    getNavigator().handleError(throwable);
//                }));
    }

    public void onNavigationMenuUpdateUserProfile(UserProfile userProfile) {
        final String currentUserName = userProfile.getUserName();
        if (!StringUtils.isBlank(currentUserName)) {
            userName.set(currentUserName);
        }

        final String currentUserEmail = userProfile.getEmail();
        if (!StringUtils.isBlank(currentUserEmail)) {
            userEmail.set(currentUserEmail);
        }

        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
        if (!TextUtils.isEmpty(profilePicUrl)) {
            userProfilePicUrl.set(profilePicUrl);
        }
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }
}
