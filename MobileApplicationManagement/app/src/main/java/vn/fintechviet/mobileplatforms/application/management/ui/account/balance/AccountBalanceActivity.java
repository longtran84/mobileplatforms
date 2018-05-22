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

package vn.fintechviet.mobileplatforms.application.management.ui.account.balance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.application.management.BR;
import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.databinding.ActivityAccountBalanceBinding;
import vn.fintechviet.mobileplatforms.application.management.ui.account.balance.adapters.AccountBalanceViewerAdapter;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.application.management.utils.JDateFormat;
import vn.fintechviet.mobileplatforms.application.management.utils.RecyclerViewOnItemClickListener;

/**
 * Created by long_tran on 08/07/17.
 */

public class AccountBalanceActivity extends BaseActivity<ActivityAccountBalanceBinding, AccountBalanceViewModel> implements AccountBalanceNavigator {

    @Inject
    AccountBalanceViewModel accountBalanceViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    private AccountBalanceViewerAdapter accountBalanceViewerAdapter;
    private ActivityAccountBalanceBinding activityAccountBalanceBinding;

    /**
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, AccountBalanceActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_balance;
    }

    @Override
    public AccountBalanceViewModel getViewModel() {
        return accountBalanceViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void fromDateClick() {
        int[] yearMonthDay = JDateFormat.getYearMonthDay(JDateFormat.getCurrentTimeStamp("yyyy-MM-dd'T'HH:mm:ss"),
                "yyyy-MM-dd'T'HH:mm:ss");
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String fromDateGlobalVariable = String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year);
                        fromDateGlobalVariable = JDateFormat.dateString(fromDateGlobalVariable, "dd/mm/yyyy");
                        accountBalanceViewModel.updateFromDate(fromDateGlobalVariable);
                        //fromDateGlobalVariable = JDateFormat.formatDate(fromDateGlobalVariable, "MM-dd-yyyy", "yyyy-MM-dd'T'HH:mm:ss");
                    }
                }, yearMonthDay[0], (yearMonthDay[1] - 1), yearMonthDay[2]
        );
        dpd.setAccentColor(Color.parseColor("#518fca"));
        dpd.show(getFragmentManager(), "SchedulePresenter");
    }

    @Override
    public void toDateClick() {
        int[] yearMonthDay = JDateFormat.getYearMonthDay(JDateFormat.getCurrentTimeStamp("yyyy-MM-dd'T'HH:mm:ss"),
                "yyyy-MM-dd'T'HH:mm:ss");
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String toDateGlobalVariable = String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year);
                        toDateGlobalVariable = JDateFormat.dateString(toDateGlobalVariable, "dd/mm/yyyy");
                        accountBalanceViewModel.updateToDate(toDateGlobalVariable);
                        //toDateGlobalVariable = JDateFormat.formatDate(toDateGlobalVariable, "MM-dd-yyyy", "yyyy-MM-dd'T'HH:mm:ss");
                    }
                }, yearMonthDay[0], (yearMonthDay[1] - 1), yearMonthDay[2]
        );
        dpd.setAccentColor(Color.parseColor("#518fca"));
        dpd.show(getFragmentManager(), "SchedulePresenter");
    }

    @Override
    public void serverLookupClick() {
        accountBalanceViewModel.lookupClick(activityAccountBalanceBinding.appCompatTextViewFromDate.getText().toString().trim(),
                activityAccountBalanceBinding.appCompatTextViewToDate.getText().toString().trim());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(getString(R.string.lookup_account_balance));
        activityAccountBalanceBinding = getViewDataBinding();
        accountBalanceViewModel.setNavigator(this);
        accountBalanceViewerAdapter = new AccountBalanceViewerAdapter(this, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityAccountBalanceBinding.recyclerViewMessages.setLayoutManager(mLayoutManager);
        activityAccountBalanceBinding.recyclerViewMessages.setItemAnimator(new DefaultItemAnimator());
        activityAccountBalanceBinding.recyclerViewMessages.setAdapter(accountBalanceViewerAdapter);
        subscribeToLiveData();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        accountBalanceViewModel.getMutableLiveDataListAccountBalance().observe(this, x -> {
            accountBalanceViewerAdapter.setData(x);
        });
    }
}
