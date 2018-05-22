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

package vn.fintechviet.mobileplatforms.application.management.ui.forgot.password;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

import vn.fintechviet.mobileplatforms.application.management.data.DataManager;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LoginRequest;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.application.management.utils.CommonUtils;
import vn.fintechviet.mobileplatforms.application.management.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 08/07/17.
 */

public class ForgotPasswordViewModel extends BaseViewModel<ForgotPasswordNavigator> {

    public ForgotPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * @param account
     * @return
     */
    public boolean isAccountValid(String account) {
        if (StringUtils.isBlank(account)) {
            return false;
        }
        return true;
    }

    /**
     * @param account
     */
    public void forgotPassword(String account) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerForgotPasswordApiCall(account)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(forgotPasswordResponse -> {
                    setIsLoading(false);
                    if (forgotPasswordResponse.getStatusCode() == 1) {
                        getNavigator().openLoginActivity();
                    }
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    /**
     *
     */
    public void onServerForgotPasswordClick() {
        getNavigator().forgotPasswordClick();
    }

    /**
     *
     */
    public void onLoginClick() {
        getNavigator().openLoginActivity();
    }

    /**
     *
     */
    public void onSignUp() {
        getNavigator().openSignUp();
    }
}
