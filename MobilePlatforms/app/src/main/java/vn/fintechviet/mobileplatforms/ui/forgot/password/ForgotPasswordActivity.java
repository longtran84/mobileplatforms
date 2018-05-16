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

package vn.fintechviet.mobileplatforms.ui.forgot.password;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.databinding.ActivityForgotPasswordBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivity;

/**
 * Created by long_tran on 08/07/17.
 */

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel> implements ForgotPasswordNavigator {

    @Inject
    ForgotPasswordViewModel forgotPasswordViewModel;

    private ActivityForgotPasswordBinding activityForgotPasswordBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public ForgotPasswordViewModel getViewModel() {
        return forgotPasswordViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void forgotPasswordClick() {
        String account = activityForgotPasswordBinding.textInputEditTextAccount.getText().toString();
        if (forgotPasswordViewModel.isAccountValid(account)) {
            hideKeyboard();
            forgotPasswordViewModel.forgotPassword(account);
        } else {
            activityForgotPasswordBinding.textInputEditTextAccount.setError(getString(R.string.invalid_account));
        }
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(ForgotPasswordActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openSignUp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding = getViewDataBinding();
        forgotPasswordViewModel.setNavigator(this);
        activityForgotPasswordBinding.appCompatTextViewForgotPasswordId.setPaintFlags(
                activityForgotPasswordBinding.appCompatTextViewForgotPasswordId.getPaintFlags() |
                        Paint.UNDERLINE_TEXT_FLAG);
        activityForgotPasswordBinding.appCompatTextViewSignUpId.setPaintFlags(
                activityForgotPasswordBinding.appCompatTextViewSignUpId.getPaintFlags() |
                        Paint.UNDERLINE_TEXT_FLAG);
    }
}
