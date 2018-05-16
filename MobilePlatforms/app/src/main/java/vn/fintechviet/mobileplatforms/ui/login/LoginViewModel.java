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

package vn.fintechviet.mobileplatforms.ui.login;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import retrofit2.HttpException;
import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.LoginRequest;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.CommonUtils;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    public boolean isUserAndPasswordValid(String email, String password) {
        // validate email and password
        if (StringUtils.isEmpty(email)) {
            return false;
        }

        if (StringUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param email
     * @return
     */
    public boolean isEmailValid(String email) {
        // validate email
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param password
     * @return
     */
    public boolean isPasswordValid(String password) {
        // validate password
        if (StringUtils.isBlank(password)) {
            return false;
        }
        return true;
    }
    /**
     * @param email
     * @param password
     */
    protected void login(String email, String password, DeviceInfoPayload deviceInfoPayload) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                .doOnSuccess(response ->
                        {
                            if(!StringUtils.isBlank(response.getAuthorization())){
                                getDataManager().setAuthorization(response.getAuthorization());
                                getDataManager().setAccessToken(response.getAuthorization().replace("Bearer ", "").trim());
                            }
                        }
                )
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    deviceInfoPayload.setFcmToken(getDataManager().getFireBaseCloudMessagingToken());
                    deviceInfoPayload.setOwner(response.getUserName());
                    getCompositeDisposable().add(getDataManager()
                            .doRegisterDevice(deviceInfoPayload)
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(aString -> {
                                setIsLoading(false);
                                getNavigator().openMainActivity();
                            }, throwable -> {
                                setIsLoading(false);
                                getNavigator().handleError(throwable);
                            }));
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onFbLoginClick() {

    }

    /**
     *
     */
    public void onForgotPasswordClick() {
        getNavigator().forgotPasswordClick();
    }

    /**
     *
     */
    public void onSignUp() {
        getNavigator().signUp();
    }

    /**
     *
     */
    public void onServerLoginClick() {
        getNavigator().login();
    }

    /**
     *
     */
    public void onVerifyWithReCaptchaClick() {
        getNavigator().verifyWithReCaptchaClick();
    }

    /**
     *
     */
    public void onFaceIDRecognitionClick(){
        getNavigator().faceIDRecognitionClick();
    }

    /**
     *
     */
    public void onFingerprintRecognitionClick(){
        getNavigator().fingerprintRecognitionClick();
    }
}
