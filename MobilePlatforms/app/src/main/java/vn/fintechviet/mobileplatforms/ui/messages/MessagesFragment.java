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

package vn.fintechviet.mobileplatforms.ui.messages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.OBR;
import vn.fintechviet.mobileplatforms.databinding.FragmentMessagesBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.ui.messages.adapters.MessagesViewerAdapter;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnItemClickListener;
import vn.fintechviet.mobileplatforms.utils.adapter.SpinnerCustomAdapter;

/**
 * Created by long_tran on 09/07/17.
 */

public class MessagesFragment extends BaseFragment<FragmentMessagesBinding, MessagesViewModel> implements MessagesNavigator {

    public static final String TAG = MessagesFragment.class.getSimpleName();

    @Inject
    MessagesViewModel messagesViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    private MessagesViewerAdapter messagesViewerAdapter;
    private FragmentMessagesBinding fragmentMessagesBinding;
    private SpinnerCustomAdapter<OBR> spinnerCustomAdapter;

    public static MessagesFragment newInstance() {
        Bundle args = new Bundle();
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.messagesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_messages;
    }

    @Override
    public MessagesViewModel getViewModel() {
        return messagesViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentMessagesBinding = getViewDataBinding();
        spinnerCustomAdapter = new SpinnerCustomAdapter<OBR>(getActivity(), android.R.layout.simple_spinner_item);
        spinnerCustomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentMessagesBinding.appCompatSpinnerOrganizationalBudgetRelationship.setAdapter(spinnerCustomAdapter);
        messagesViewerAdapter = new MessagesViewerAdapter(getContext(), new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentMessagesBinding.recyclerViewMessages.setLayoutManager(mLayoutManager);
        fragmentMessagesBinding.recyclerViewMessages.setItemAnimator(new DefaultItemAnimator());
        fragmentMessagesBinding.recyclerViewMessages.setAdapter(messagesViewerAdapter);
        subscribeToLiveData();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        messagesViewModel.getMutableLiveDataListSectionDataModel().observe(this, x -> spinnerCustomAdapter.setData(x));
        messagesViewModel.getMutableLiveDataListMessages().observe(this, x -> messagesViewerAdapter.setData(x));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagesViewModel.setNavigator(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            messagesViewModel.dataFetching();
        }
    }
}