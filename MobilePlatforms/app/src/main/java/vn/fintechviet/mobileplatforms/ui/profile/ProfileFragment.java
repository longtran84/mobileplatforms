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

package vn.fintechviet.mobileplatforms.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.UserProfile;
import vn.fintechviet.mobileplatforms.databinding.FragmentProfileBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseFragment;

/**
 * Created by long_tran on 09/07/17.
 */

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> implements ProfileNavigator {

    public static final String TAG = ProfileFragment.class.getSimpleName();

    @Inject
    ProfileViewModel profileViewModel;

    private FragmentProfileBinding fragmentProfileBinding;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        return profileViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentProfileBinding = getViewDataBinding();
        subscribeToLiveData();
    }

    /**
     *
     */
    private void subscribeToLiveData() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel.setNavigator(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            profileViewModel.dataFetching();
        }
    }

    @Override
    public void profileUpdateClick() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(fragmentProfileBinding.textViewFullName.getText().toString().trim());
        userProfile.setEmail(fragmentProfileBinding.textViewProfileEmail.getText().toString().trim());
        userProfile.setPosition(fragmentProfileBinding.textViewProfilePosition.getText().toString().trim());
        userProfile.setDepartment(fragmentProfileBinding.textViewProfileDepartment.getText().toString().trim());
        userProfile.setPhone(fragmentProfileBinding.textViewProfilePhone.getText().toString().trim());
        userProfile.setMobile(fragmentProfileBinding.textViewProfileMobile.getText().toString().trim());
        userProfile.setAddress(fragmentProfileBinding.textViewProfileAddress.getText().toString().trim());
        profileViewModel.profileUpdate(userProfile);
        fragmentProfileBinding.floatingActionButtonEdit.setVisibility(View.VISIBLE);
        fragmentProfileBinding.floatingActionButtonSave.setVisibility(View.GONE);
        setVisibilityWidget(false);
    }

    @Override
    public void profileEditClick() {
        fragmentProfileBinding.floatingActionButtonEdit.setVisibility(View.GONE);
        fragmentProfileBinding.floatingActionButtonSave.setVisibility(View.VISIBLE);
        setVisibilityWidget(true);
    }

    /**
     *
     * @param enabled
     */
    private void setVisibilityWidget(boolean enabled){
        fragmentProfileBinding.textViewFullName.setEnabled(enabled);
        fragmentProfileBinding.textViewProfileEmail.setEnabled(enabled);
        fragmentProfileBinding.textViewProfileAddress.setEnabled(enabled);
        fragmentProfileBinding.textViewProfilePosition.setEnabled(enabled);
        fragmentProfileBinding.textViewProfileDepartment.setEnabled(enabled);
        fragmentProfileBinding.textViewProfilePhone.setEnabled(enabled);
        fragmentProfileBinding.textViewProfileMobile.setEnabled(enabled);
        fragmentProfileBinding.textViewProfileAddress.setEnabled(enabled);
    }
}
