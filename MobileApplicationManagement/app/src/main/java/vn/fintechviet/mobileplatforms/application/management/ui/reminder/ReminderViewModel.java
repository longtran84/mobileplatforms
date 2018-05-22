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

package vn.fintechviet.mobileplatforms.application.management.ui.reminder;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import vn.fintechviet.mobileplatforms.application.management.data.DataManager;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ReminderRegister;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.application.management.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 09/07/17.
 */

public class ReminderViewModel extends BaseViewModel<ReminderNavigator> {

    private final MutableLiveData<List<ReminderRegister>> mutableLiveDataListReminderRegister;

    public ReminderViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        mutableLiveDataListReminderRegister = new MutableLiveData<>();
    }

    /**
     * @return
     */
    public MutableLiveData<List<ReminderRegister>> getMutableLiveDataListReminderRegister() {
        return mutableLiveDataListReminderRegister;
    }

    /**
     *
     */
    public void dataFetching() {
        setIsLoading(true);
        loadMessages();
    }

    /**
     *
     */
    private void loadMessages() {
        String accessToken = getDataManager().getAccessToken().trim();
        getCompositeDisposable().add(getDataManager()
                .doServerReminderRegisterApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(reminderRegisterResponse -> {
                    if (reminderRegisterResponse != null) {
                        List<ReminderRegister> listMessages = reminderRegisterResponse.getListData();
                        mutableLiveDataListReminderRegister.setValue(listMessages);
                    }
                }, throwable -> {

                }));
    }
}
