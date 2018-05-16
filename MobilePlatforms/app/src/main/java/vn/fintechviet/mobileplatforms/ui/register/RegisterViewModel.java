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

package vn.fintechviet.mobileplatforms.ui.register;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.Modules;
import vn.fintechviet.mobileplatforms.data.model.api.RegisterAccountRequest;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    private final MutableLiveData<List<Modules>> mutableLiveDataListModules;

    public RegisterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mutableLiveDataListModules = new MutableLiveData<>();
        dataFetching();
    }

    /**
     * @return
     */
    public MutableLiveData<List<Modules>> getMutableLiveDataListModules() {
        return mutableLiveDataListModules;
    }

    /**
     *
     */
    public void dataFetching() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerModuleApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(modulesResponse -> {
                    if (modulesResponse != null && modulesResponse.getListData() != null) {
                        mutableLiveDataListModules.setValue(modulesResponse.getListData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    //getNavigator().handleError(throwable);
                }));
    }

    /**
     * @param registerAccountRequest
     */
    public void registerAccount(RegisterAccountRequest registerAccountRequest) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerRegisterAccountApiCall(registerAccountRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().handleSuccessfully();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onServerRegisterClick() {
        getNavigator().registerClick();
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
