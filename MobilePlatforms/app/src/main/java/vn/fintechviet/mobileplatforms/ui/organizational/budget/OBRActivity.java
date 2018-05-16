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

package vn.fintechviet.mobileplatforms.ui.organizational.budget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.databinding.ActivityOrganizationalBudgetRelationshipBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.ui.organizational.budget.adapter.OBRAdapter;

/**
 *
 */
public class OBRActivity extends BaseActivity<ActivityOrganizationalBudgetRelationshipBinding, OBRViewModel>
        implements OBRNavigator, OBRAdapter.BlogAdapterListener {

    @Inject
    OBRViewModel mOBRViewModel;

    @Inject
    OBRAdapter mBlogAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    private ActivityOrganizationalBudgetRelationshipBinding activityOrganizationalBudgetRelationshipBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_organizational_budget_relationship;
    }

    @Override
    public OBRViewModel getViewModel() {
        return mOBRViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, OBRActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrganizationalBudgetRelationshipBinding = getViewDataBinding();
        mOBRViewModel.setNavigator(this);
        mBlogAdapter.setListener(this);
        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityOrganizationalBudgetRelationshipBinding.obrRecyclerView.setLayoutManager(mLayoutManager);
        activityOrganizationalBudgetRelationshipBinding.obrRecyclerView.setItemAnimator(new DefaultItemAnimator());
        activityOrganizationalBudgetRelationshipBinding.obrRecyclerView.setAdapter(mBlogAdapter);
    }

    @Override
    public void onRetryClick() {
        mOBRViewModel.fetchOBRs();
    }

    private void subscribeToLiveData() {
        mOBRViewModel.getOBRListLiveData().observe(this, obrs -> mOBRViewModel.addOBRItemsToList(obrs));
    }
}
