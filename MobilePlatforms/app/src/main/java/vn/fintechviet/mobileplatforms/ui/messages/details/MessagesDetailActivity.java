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

package vn.fintechviet.mobileplatforms.ui.messages.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.Messages;
import vn.fintechviet.mobileplatforms.databinding.ActivityMessagesDetailBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.utils.Constants;

/**
 * Created by long_tran on 08/07/17.
 */

public class MessagesDetailActivity extends BaseActivity<ActivityMessagesDetailBinding, MessagesDetailViewModel> implements MessagesDetailNavigator {

    @Inject
    MessagesDetailViewModel messagesDetailViewModel;

    private ActivityMessagesDetailBinding activityMessagesDetailBinding;

    public static Intent newIntent(Context context, Messages messages) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleConstant.MESSAGES_DETAIL_ID, messages.getUuid());
        Intent intent = new Intent(context, MessagesDetailActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_messages_detail;
    }

    @Override
    public MessagesDetailViewModel getViewModel() {
        return messagesDetailViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(getString(R.string.messages_toolbar));
        activityMessagesDetailBinding = getViewDataBinding();
        messagesDetailViewModel.setNavigator(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        messagesDetailViewModel.dataFetching(bundle.getString(Constants.BundleConstant.MESSAGES_DETAIL_ID));
        messagesDetailViewModel.getMutableLiveDataContent().observe(this, x -> {
            activityMessagesDetailBinding.webViewContent.getSettings().setJavaScriptEnabled(false);
            activityMessagesDetailBinding.webViewContent.loadDataWithBaseURL("", x.trim(), "text/html", "UTF-8", "");
        });
    }
}