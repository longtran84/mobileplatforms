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

package vn.fintechviet.mobileplatforms.ui.process.register;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.view.View;

import java.util.List;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.LookupRegister;
import vn.fintechviet.mobileplatforms.data.model.api.ProcessRegister;
import vn.fintechviet.mobileplatforms.data.model.api.ProcessRegisterRequest;
import vn.fintechviet.mobileplatforms.data.model.api.UpdateStatusRegisterRequest;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 09/07/17.
 */

public class ProcessRegisterViewModel extends BaseViewModel<ProcessRegisterNavigator> {

    private ObservableField<String> observableFieldFromDate;
    private ObservableField<String> observableFieldToDate;
    private final MutableLiveData<List<ProcessRegister>> mutableLiveDataListProcessRegister;
    private String fromDateGlobalVariable;
    private String toDateGlobalVariable;
    private String nameGlobalVariable;

    /**
     * @param dataManager
     * @param schedulerProvider
     */
    public ProcessRegisterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        observableFieldFromDate = new ObservableField<>();
        observableFieldToDate = new ObservableField<>();
        mutableLiveDataListProcessRegister = new MutableLiveData<>();
    }

    /**
     * @return
     */
    public MutableLiveData<List<ProcessRegister>> getMutableLiveDataListProcessRegister() {
        return mutableLiveDataListProcessRegister;
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
     *
     */
    public void onServerRejectClick() {
        getNavigator().serverRejectClick();
    }

    /***
     *
     * @param fromDate
     * @param toDate
     * @param name
     */
    private void lookupRequest(String fromDate, String toDate, String name){
        setIsLoading(true);
        ProcessRegisterRequest processRegisterRequest = new ProcessRegisterRequest();
        processRegisterRequest.setName(name);
        processRegisterRequest.setFromDate(fromDate);
        processRegisterRequest.setToDate(toDate);
        getCompositeDisposable().add(getDataManager()
                .doServerProcessRegisterApiCall(processRegisterRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(processRegisterResponse -> {
                    if (processRegisterResponse != null) {
                        mutableLiveDataListProcessRegister.setValue(processRegisterResponse.getData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }
    /**
     * @param fromDate
     * @param toDate
     */
    protected void lookupClick(String fromDate, String toDate, String name) {
        nameGlobalVariable = name;
        fromDateGlobalVariable = fromDate;
        toDateGlobalVariable = toDate;
        lookupRequest(fromDateGlobalVariable, toDateGlobalVariable, nameGlobalVariable);
    }
    /**
     * @param statusCode
     */
    protected void updateStatusRegister(String statusCode, List<String> listRegisterId) {
        setIsLoading(true);
        UpdateStatusRegisterRequest updateStatusRegisterRequest = new UpdateStatusRegisterRequest();
        updateStatusRegisterRequest.setStatusCode(statusCode);
        updateStatusRegisterRequest.setListRegisterId(listRegisterId);
        getCompositeDisposable().add(getDataManager()
                .doServerUpdateStatusRegisterApiCall(updateStatusRegisterRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(updateStatusRegisterResponse -> {
                    if (updateStatusRegisterResponse != null) {
                        lookupRequest(fromDateGlobalVariable, toDateGlobalVariable, nameGlobalVariable);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }
}
