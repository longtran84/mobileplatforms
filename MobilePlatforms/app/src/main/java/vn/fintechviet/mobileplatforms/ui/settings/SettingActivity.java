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
package vn.fintechviet.mobileplatforms.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.databinding.ActivitySettingBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettingViewModel> implements SettingNavigator {

    @Inject
    SettingViewModel settingViewModel;

    private ActivitySettingBinding activitySettingBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public SettingViewModel getViewModel() {
        return settingViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(getString(R.string.setting_system));
        activitySettingBinding = getViewDataBinding();
        settingViewModel.setNavigator(this);
        subscribeToLiveData();
        settingViewModel.dataFetching(this);
    }

    /**
     *
     */
    private void subscribeToLiveData() {

    }
}
