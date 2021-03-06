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

package vn.fintechviet.mobileplatforms.ui.help.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.databinding.ActivityHelpDetailBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.ui.help.holder.IconTreeItem;
import vn.fintechviet.mobileplatforms.ui.help.holder.IconTreeItemHolder;
import vn.fintechviet.mobileplatforms.utils.Constants;

/**
 * Created by long_tran on 08/07/17.
 */

public class HelpDetailActivity extends BaseActivity<ActivityHelpDetailBinding, HelpDetailViewModel> implements HelpDetailNavigator {

    @Inject
    HelpDetailViewModel helpDetailViewModel;

    private ActivityHelpDetailBinding activityHelpDetailBinding;

    public static Intent newIntent(Context context, IconTreeItem iconTreeItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleConstant.HELP_DETAIL_PARCELABLE, iconTreeItem);
        Intent intent = new Intent(context, HelpDetailActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_detail;
    }

    @Override
    public HelpDetailViewModel getViewModel() {
        return helpDetailViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(getString(R.string.help_toolbar));
        activityHelpDetailBinding = getViewDataBinding();
        helpDetailViewModel.setNavigator(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        helpDetailViewModel.dataFetching(bundle.getParcelable(Constants.BundleConstant.HELP_DETAIL_PARCELABLE));
        helpDetailViewModel.getMutableLiveDataContent().observe(this, x -> {
            activityHelpDetailBinding.webViewContent.getSettings().setJavaScriptEnabled(false);
            activityHelpDetailBinding.webViewContent.loadDataWithBaseURL("", x.trim(), "text/html", "UTF-8", "");
        });
    }
}
