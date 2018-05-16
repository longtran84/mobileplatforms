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

package vn.fintechviet.mobileplatforms.ui.change.password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.databinding.FragmentChangePasswordBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivity;
import vn.fintechviet.mobileplatforms.ui.main.MainActivity;
import vn.fintechviet.mobileplatforms.utils.AppUtils;
import vn.fintechviet.mobileplatforms.utils.PasswordValidator;

/**
 * Created by long_tran on 08/07/17.
 */

public class ChangePasswordFragment extends BaseFragment<FragmentChangePasswordBinding, ChangePasswordViewModel> implements ChangePasswordNavigator {

    @Inject
    ChangePasswordViewModel forgotPasswordViewModel;

    private FragmentChangePasswordBinding fragmentChangePasswordBinding;

    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.changePasswordViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    public ChangePasswordViewModel getViewModel() {
        return forgotPasswordViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void changePassword() {
        PasswordValidator passwordValidator = new PasswordValidator();
        String oldPass = fragmentChangePasswordBinding.oldPassword.getText().toString().trim();
        String newPass = fragmentChangePasswordBinding.newPassword.getText().toString().trim();
        String confirmPass = fragmentChangePasswordBinding.confirmPassword.getText().toString().trim();
        if (StringUtils.isBlank(oldPass)) {
            fragmentChangePasswordBinding.oldPassword.setError(getString(R.string.old_password_empty_message));
        } else if (StringUtils.isBlank(newPass)) {
            fragmentChangePasswordBinding.newPassword.setError(getString(R.string.new_password_empty_message));
        } else if (!passwordValidator.validate(newPass)) {
            fragmentChangePasswordBinding.newPassword.setError(getString(R.string.new_password_invalidate_message));
        } else if (StringUtils.isBlank(confirmPass)) {
            fragmentChangePasswordBinding.confirmPassword.setError(getString(R.string.confirm_password_empty_message));
        } else if (!passwordValidator.validate(confirmPass)) {
            fragmentChangePasswordBinding.confirmPassword.setError(getString(R.string.confirm_password_invalidate_message));
        } else if (TextUtils.equals(oldPass, newPass)) {
            fragmentChangePasswordBinding.confirmPassword.setError(getString(R.string.choose_different_password_than_old_password));
        } else if (!TextUtils.equals(newPass, confirmPass)) {
            fragmentChangePasswordBinding.confirmPassword.setError(getString(R.string.password_confirmation_password_do_not_match));
        } else {
            forgotPasswordViewModel.changePassword(oldPass, newPass, confirmPass);
        }
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(getActivity());
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentChangePasswordBinding = getViewDataBinding();
        fragmentChangePasswordBinding.appCompatTextViewPasswordPolicy.setText(Html.fromHtml(getText(R.string.password_policy).toString()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPasswordViewModel.setNavigator(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }
}
