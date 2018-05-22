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

package vn.fintechviet.mobileplatforms.ui.version.management;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.VersionManager;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 09/07/17.
 */

public class VersionManagerViewModel extends BaseViewModel<VersionManagerNavigator> {

    private final MutableLiveData<List<VersionManager>> mutableLiveDataListVersionManager;

    /**
     * @param dataManager
     * @param schedulerProvider
     */
    public VersionManagerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mutableLiveDataListVersionManager = new MutableLiveData<>();
    }

    /**
     * @return
     */
    public MutableLiveData<List<VersionManager>> getMutableLiveDataListVersionManager() {
        return mutableLiveDataListVersionManager;
    }

    /**
     *
     */
    public void dataFetching(String appCode) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerVersionManagerApiCall(appCode)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(versionManagerResponse -> {
                    if (versionManagerResponse != null) {
                        List<VersionManager> listMessages = versionManagerResponse.getData();
                        mutableLiveDataListVersionManager.setValue(listMessages);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                }));
    }

    /**
     *
     */
    public void onServerUpdateClick() {
        getNavigator().onUpdateClick();
    }
}
