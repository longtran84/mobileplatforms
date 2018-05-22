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

package vn.fintechviet.mobileplatforms.application.management.ui.change.password;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

import vn.fintechviet.mobileplatforms.application.management.data.DataManager;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ChangePasswordRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LoginRequest;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.application.management.utils.CommonUtils;
import vn.fintechviet.mobileplatforms.application.management.utils.PasswordValidator;
import vn.fintechviet.mobileplatforms.application.management.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 08/07/17.
 */

public class ChangePasswordViewModel extends BaseViewModel<ChangePasswordNavigator> {

    public ChangePasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * @param email
     * @return
     */
    public boolean isEmailAndPasswordValid(String email) {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false;
        }
        return true;
    }

    /**
     *
     */
    public void onServerChangePasswordClick() {
        getNavigator().changePassword();
    }

    /**
     * @param oldPass
     * @param newPass
     * @param confirmPass
     * @return
     */
    protected void changePassword(String oldPass, String newPass, String confirmPass) {
        setIsLoading(true);
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUserName(getDataManager().getAuthorization());
        changePasswordRequest.setOldPassword(oldPass);
        changePasswordRequest.setNewPassword(newPass);
        getCompositeDisposable().add(getDataManager()
                .doServerChangePasswordApiCall(changePasswordRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getDataManager().setAuthorization(null);
                    getNavigator().openLoginActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }
}
