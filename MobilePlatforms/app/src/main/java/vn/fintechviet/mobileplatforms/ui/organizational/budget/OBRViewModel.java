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

package vn.fintechviet.mobileplatforms.ui.organizational.budget;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.google.gson.Gson;

import java.util.List;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.OBR;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 07/07/17.
 * OBR = OrganizationalBudgetRelationshipViewModel
 */
public class OBRViewModel extends BaseViewModel<OBRNavigator> {


    public final ObservableList<OBR> oBRObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<OBR>> oBRListLiveData;

    /**
     * @param dataManager
     * @param schedulerProvider
     */
    public OBRViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        oBRListLiveData = new MutableLiveData<>();
        fetchOBRs();
    }

    public void addOBRItemsToList(List<OBR> obrs) {
        oBRObservableArrayList.clear();
        oBRObservableArrayList.addAll(obrs);
    }

    public void fetchOBRs() {
        setIsLoading(true);
        String accessToken = getDataManager().getAccessToken().trim();
        getCompositeDisposable().add(getDataManager()
                .doServerOBRApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(obrResponse -> {
                    if (obrResponse != null && obrResponse.getListData() != null) {
                        oBRListLiveData.setValue(obrResponse.getListData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public MutableLiveData<List<OBR>> getOBRListLiveData() {
        return oBRListLiveData;
    }

    public ObservableList<OBR> getBlogObservableList() {
        return oBRObservableArrayList;
    }
}
