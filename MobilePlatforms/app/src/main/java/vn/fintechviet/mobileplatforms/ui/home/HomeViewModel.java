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

package vn.fintechviet.mobileplatforms.ui.home;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.OBR;
import vn.fintechviet.mobileplatforms.data.model.api.UserModules;
import vn.fintechviet.mobileplatforms.data.model.api.UserProfile;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 09/07/17.
 */

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private final ObservableField<String> userName;
    private final MutableLiveData<List<OBR>> mutableLiveDataListSectionDataModel;
    private final MutableLiveData<List<UserModules>> mutableLiveDataListUserModules;

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mutableLiveDataListSectionDataModel = new MutableLiveData<>();
        mutableLiveDataListUserModules = new MutableLiveData<>();
        userName = new ObservableField<>();
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getUserName() {
        return userName;
    }

    /**
     *
     * @return
     */
    public MutableLiveData<List<OBR>> getMutableLiveDataListSectionDataModel() {
        return mutableLiveDataListSectionDataModel;
    }

    /**
     *
     * @return
     */
    public MutableLiveData<List<UserModules>> getMutableLiveDataListUserModules() {
        return mutableLiveDataListUserModules;
    }

    /**
     *
     */
    public void dataFetching() {
        setIsLoading(true);
        String accessToken = getDataManager().getAccessToken().trim();
//        getCompositeDisposable().add(getDataManager()
//                .doServerOBRApiCall(accessToken)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(obrResponse -> {
//                    if (obrResponse != null && obrResponse.getListData() != null) {
//                        mutableLiveDataListSectionDataModel.setValue(obrResponse.getListData());
//                    }
//                    setIsLoading(false);
//                }, throwable -> {
//                    setIsLoading(false);
//                    //getNavigator().handleError(throwable);
//                }));

        getCompositeDisposable().add(getDataManager()
                .doServerUserModuleApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(userModulesResponse -> {
                    if (userModulesResponse != null && userModulesResponse.getListData() != null) {
                        mutableLiveDataListUserModules.setValue(userModulesResponse.getListData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));

        //loadUserProfile();
    }

    /**
     *
     */
    private void loadUserProfile() {
        String accessToken = getDataManager().getAccessToken().trim();
        getCompositeDisposable().add(getDataManager()
                .doServerUserProfileApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(profileResponse -> {
                    if (profileResponse != null) {
                        UserProfile userProfile = profileResponse.getData();
                        if (null != userProfile) {
                            if (!StringUtils.isBlank(userProfile.getUserName())) {
                                userName.set(userProfile.getUserName());
                            }
                        }
                    }
                }, throwable -> {

                }));
    }
}
