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

package vn.fintechviet.mobileplatforms.application.management.ui.lookup.register;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import java.util.List;

import vn.fintechviet.mobileplatforms.application.management.data.DataManager;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LookupRegister;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.LookupRegisterRequest;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.application.management.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 09/07/17.
 */

public class LookupRegisterViewModel extends BaseViewModel<LookupRegisterNavigator> {

    private ObservableField<String> observableFieldFromDate;
    private ObservableField<String> observableFieldToDate;
    private final MutableLiveData<List<LookupRegister>> mutableLiveDataListLookupRegister;

    /**
     * @param dataManager
     * @param schedulerProvider
     */
    public LookupRegisterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        observableFieldFromDate = new ObservableField<>();
        observableFieldToDate = new ObservableField<>();
        mutableLiveDataListLookupRegister = new MutableLiveData<>();
    }

    /**
     * @return
     */
    public MutableLiveData<List<LookupRegister>> getMutableLiveDataListLookupRegister() {
        return mutableLiveDataListLookupRegister;
    }

    /**
     * @return
     */
    public ObservableField<String> getObservableFieldFromDate() {
        return observableFieldFromDate;
    }

    /**
     * @return
     */
    public ObservableField<String> getObservableFieldToDate() {
        return observableFieldToDate;
    }

    /**
     * @param fromDate
     */
    public void updateFromDate(String fromDate) {
        observableFieldFromDate.set(fromDate);
    }

    /**
     * @param toDate
     */
    public void updateToDate(String toDate) {
        observableFieldToDate.set(toDate);
    }

    /**
     *
     */
    public void onFromDateClick() {
        getNavigator().fromDateClick();
    }

    /**
     *
     */
    public void onToDateClick() {
        getNavigator().toDateClick();
    }

    /**
     *
     */
    public void onServerLookupClick() {
        getNavigator().serverLookupClick();
    }

    /**
     *
     */
    public void onServerApprovalClick() {
        getNavigator().serverApprovalClick();
    }

    /**
     *
     */
    public void onServerAcceptClick() {
        getNavigator().serverAcceptClick();
    }
    /**
     * @param fromDate
     * @param toDate
     */
    protected void lookupClick(String fromDate, String toDate, String name) {
        setIsLoading(true);
        LookupRegisterRequest lookupRegisterRequest = new LookupRegisterRequest();
        lookupRegisterRequest.setName(name);
        lookupRegisterRequest.setFromDate(fromDate);
        lookupRegisterRequest.setToDate(toDate);
        getCompositeDisposable().add(getDataManager()
                .doServerLookupRegisterApiCall(lookupRegisterRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(lookupRegisterResponse -> {
                    if (lookupRegisterResponse != null) {
                        mutableLiveDataListLookupRegister.setValue(lookupRegisterResponse.getData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }
}
