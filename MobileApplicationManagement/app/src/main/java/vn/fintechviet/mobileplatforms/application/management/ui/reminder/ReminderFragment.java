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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.application.management.BR;
import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.databinding.FragmentReminderBinding;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.reminder.adapters.ReminderViewerAdapter;
import vn.fintechviet.mobileplatforms.application.management.utils.RecyclerViewOnItemClickListener;

/**
 * Created by long_tran on 09/07/17.
 */

public class ReminderFragment extends BaseFragment<FragmentReminderBinding, ReminderViewModel> implements ReminderNavigator {

    public static final String TAG = ReminderFragment.class.getSimpleName();

    @Inject
    ReminderViewModel reminderViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    private ReminderViewerAdapter reminderViewerAdapter;
    private FragmentReminderBinding fragmentReminderBinding;

    /**
     *
     * @return
     */
    public static ReminderFragment newInstance() {
        Bundle args = new Bundle();
        ReminderFragment fragment = new ReminderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.messagesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_reminder;
    }

    @Override
    public ReminderViewModel getViewModel() {
        return reminderViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentReminderBinding = getViewDataBinding();
        reminderViewerAdapter = new ReminderViewerAdapter(getContext(), new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentReminderBinding.recyclerViewReminder.setLayoutManager(mLayoutManager);
        fragmentReminderBinding.recyclerViewReminder.setItemAnimator(new DefaultItemAnimator());
        fragmentReminderBinding.recyclerViewReminder.setAdapter(reminderViewerAdapter);
        subscribeToLiveData();
        reminderViewModel.dataFetching();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        reminderViewModel.getMutableLiveDataListReminderRegister().observe(this, x -> reminderViewerAdapter.setData(x));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reminderViewModel.setNavigator(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            reminderViewModel.dataFetching();
        }
    }
}
