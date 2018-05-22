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

package vn.fintechviet.mobileplatforms.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.OBR;
import vn.fintechviet.mobileplatforms.data.model.api.UserApplicationModules;
import vn.fintechviet.mobileplatforms.data.model.api.UserModules;
import vn.fintechviet.mobileplatforms.databinding.FragmentHomeBinding;
import vn.fintechviet.mobileplatforms.ui.account.balance.AccountBalanceActivity;
import vn.fintechviet.mobileplatforms.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.ui.home.adapters.RecyclerViewDataAdapter;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivity;
import vn.fintechviet.mobileplatforms.ui.lookup.document.LookupDocumentActivity;
import vn.fintechviet.mobileplatforms.ui.main.MainActivity;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnObjectClickListener;
import vn.fintechviet.mobileplatforms.utils.adapter.SpinnerCustomAdapter;

/**
 * Created by long_tran on 09/07/17.
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    GridLayoutManager gridLayoutManager;

    @Inject
    HomeViewModel homeViewModel;

    private FragmentHomeBinding fragmentHomeBinding;
    private SpinnerCustomAdapter<OBR> spinnerCustomAdapter;
    private List<UserModules> listUserModules;
    private RecyclerViewDataAdapter recyclerViewDataAdapter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.homeViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        return homeViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    /**
     *
     */
    private void openAccountBalanceActivity() {
        Intent intent = AccountBalanceActivity.newIntent(getActivity());
        startActivity(intent);
    }

    /**
     *
     */
    private void openLookupDocumentActivity() {
        Intent intent = LookupDocumentActivity.newIntent(getActivity());
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentHomeBinding = getViewDataBinding();
        spinnerCustomAdapter = new SpinnerCustomAdapter<OBR>(getActivity(), android.R.layout.simple_spinner_item);
        spinnerCustomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentHomeBinding.appCompatSpinnerOrganizationalBudgetRelationship.setAdapter(spinnerCustomAdapter);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentHomeBinding.recyclerViewApplication.setLayoutManager(mLayoutManager);
        fragmentHomeBinding.recyclerViewApplication.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.recyclerViewApplication.setHasFixedSize(true);
        listUserModules = new ArrayList<>();
        recyclerViewDataAdapter = new RecyclerViewDataAdapter(getActivity(), listUserModules);
        recyclerViewDataAdapter.setRecyclerViewOnObjectClickListener(new RecyclerViewOnObjectClickListener<UserApplicationModules>() {
            @Override
            public void onClick(View v, int position, UserApplicationModules object) {
                if (StringUtils.isBlank(object.getCode())) {
                    return;
                }
                if (object.getCode().equalsIgnoreCase("FILE")) {
                    openLookupDocumentActivity();
                } else if (object.getCode().equalsIgnoreCase("BALANCE")) {
                    openAccountBalanceActivity();
                } else {

                }
            }
        });
        fragmentHomeBinding.recyclerViewApplication.setAdapter(recyclerViewDataAdapter);
        subscribeToLiveData();
        homeViewModel.dataFetching();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel.setNavigator(this);
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        homeViewModel.getMutableLiveDataListSectionDataModel().observe(this, x -> spinnerCustomAdapter.setData(x));
        homeViewModel.getMutableLiveDataListUserModules().observe(this, userModulesList -> {
            listUserModules.clear();
            List<UserModules> listUserModulesTemp = new ArrayList<>();
            for (UserModules userModules : userModulesList) {
                if (userModules.getListUserApplicationModules() != null && userModules.getListUserApplicationModules().size() > 0) {
                    listUserModulesTemp.add(userModules);
                    FirebaseMessaging.getInstance().subscribeToTopic(userModules.getCode());
                }
            }
            listUserModules.addAll(listUserModulesTemp);
            recyclerViewDataAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            homeViewModel.dataFetching();
        }
    }
}
