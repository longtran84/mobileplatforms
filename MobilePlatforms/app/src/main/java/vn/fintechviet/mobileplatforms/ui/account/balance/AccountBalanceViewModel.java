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
package vn.fintechviet.mobileplatforms.ui.account.balance;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import java.util.List;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.AccountBalance;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 08/07/17.
 */

public class AccountBalanceViewModel extends BaseViewModel<AccountBalanceNavigator> {

    private ObservableField<String> observableFieldFromDate;
    private ObservableField<String> observableFieldToDate;
    private final MutableLiveData<List<AccountBalance>> mutableLiveDataListAccountBalance;

    /**
     * @param dataManager
     * @param schedulerProvider
     */
    public AccountBalanceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        observableFieldFromDate = new ObservableField<>();
        observableFieldToDate = new ObservableField<>();
        mutableLiveDataListAccountBalance = new MutableLiveData<>();
    }

    /**
     *
     * @return
     */
    public MutableLiveData<List<AccountBalance>> getMutableLiveDataListAccountBalance() {
        return mutableLiveDataListAccountBalance;
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
     * @param fromDate
     * @param toData
     */
    protected void lookupClick(String fromDate, String toData) {
        String accessToken = getDataManager().getAccessToken().trim();
        getCompositeDisposable().add(getDataManager()
                .doServerLookupAccountBalanceApiCall(fromDate, toData, accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(accountBalanceResponse -> {
                    if (accountBalanceResponse != null) {
                        mutableLiveDataListAccountBalance.setValue(accountBalanceResponse.getData());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }
}
