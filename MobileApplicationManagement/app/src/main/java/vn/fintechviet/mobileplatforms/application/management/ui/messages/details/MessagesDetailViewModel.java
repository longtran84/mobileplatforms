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

package vn.fintechviet.mobileplatforms.application.management.ui.messages.details;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import vn.fintechviet.mobileplatforms.application.management.data.DataManager;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.Messages;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.MessagesDetail;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.application.management.utils.JDateFormat;
import vn.fintechviet.mobileplatforms.application.management.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 08/07/17.
 */

public class MessagesDetailViewModel extends BaseViewModel<MessagesDetailNavigator> {

    private final ObservableField<String> observableFieldTitle;
    private final MutableLiveData<String> mutableLiveDataContent;
    private final ObservableField<String> observableFieldIcon;
    private final ObservableField<String> observableFieldApproveDate;

    public MessagesDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        observableFieldTitle = new ObservableField<>();
        mutableLiveDataContent = new MutableLiveData<>();
        observableFieldIcon = new ObservableField<>();
        observableFieldApproveDate = new ObservableField<>();
    }


    public ObservableField<String> getObservableFieldTitle() {
        return observableFieldTitle;
    }

    public MutableLiveData<String> getMutableLiveDataContent() {
        return mutableLiveDataContent;
    }

    public ObservableField<String> getObservableFieldIcon() {
        return observableFieldIcon;
    }

    public ObservableField<String> getObservableFieldApproveDate() {
        return observableFieldApproveDate;
    }

    /**
     *
     */
    public void dataFetching(String id) {
        getCompositeDisposable().add(getDataManager()
                .doServerMessagesDetailApiCall(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(messagesDetailResponse -> {
                    if (messagesDetailResponse != null) {
                        MessagesDetail messagesDetail = messagesDetailResponse.getData();
                        if (null != messagesDetail) {
                            observableFieldTitle.set(messagesDetail.getTitle());
                            mutableLiveDataContent.setValue(messagesDetail.getContent());
                            observableFieldIcon.set(messagesDetail.getIcon());
                            observableFieldApproveDate.set(JDateFormat.formatDate(messagesDetail.getApproveDate().trim(), "dd/MM/yyyy"));
                        }
                    }
                }, throwable -> {

                }));
    }
}
