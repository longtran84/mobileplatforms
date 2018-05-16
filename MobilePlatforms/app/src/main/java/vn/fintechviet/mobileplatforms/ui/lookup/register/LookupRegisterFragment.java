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

package vn.fintechviet.mobileplatforms.ui.lookup.register;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.databinding.FragmentLookupRegisterBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.ui.lookup.register.adapters.LookupRegisterViewerAdapter;
import vn.fintechviet.mobileplatforms.utils.JDateFormat;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnItemClickListener;

/**
 * Created by long_tran on 09/07/17.
 */

public class LookupRegisterFragment extends BaseFragment<FragmentLookupRegisterBinding, LookupRegisterViewModel> implements LookupRegisterNavigator {

    public static final String TAG = LookupRegisterFragment.class.getSimpleName();

    @Inject
    LookupRegisterViewModel lookupRegisterViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    private LookupRegisterViewerAdapter lookupRegisterViewerAdapter;
    private FragmentLookupRegisterBinding fragmentLookupRegisterBinding;

    /**
     * @return
     */
    public static LookupRegisterFragment newInstance() {
        Bundle args = new Bundle();
        LookupRegisterFragment fragment = new LookupRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.lookupRegisterViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lookup_register;
    }

    @Override
    public LookupRegisterViewModel getViewModel() {
        return lookupRegisterViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void handleError(Throwable throwable) {
        //todo
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
                        lookupRegisterViewModel.updateFromDate(fromDateGlobalVariable);
                        //fromDateGlobalVariable = JDateFormat.formatDate(fromDateGlobalVariable, "MM-dd-yyyy", "yyyy-MM-dd'T'HH:mm:ss");
                    }
                }, yearMonthDay[0], (yearMonthDay[1] - 1), yearMonthDay[2]
        );
        dpd.setAccentColor(Color.parseColor("#518fca"));
        dpd.show(getActivity().getFragmentManager(), "SchedulePresenter");
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
                        lookupRegisterViewModel.updateToDate(toDateGlobalVariable);
                        //toDateGlobalVariable = JDateFormat.formatDate(toDateGlobalVariable, "MM-dd-yyyy", "yyyy-MM-dd'T'HH:mm:ss");
                    }
                }, yearMonthDay[0], (yearMonthDay[1] - 1), yearMonthDay[2]
        );
        dpd.setAccentColor(Color.parseColor("#518fca"));
        dpd.show(getActivity().getFragmentManager(), "SchedulePresenter");
    }

    @Override
    public void serverLookupClick() {
        String fullName = fragmentLookupRegisterBinding.appCompatEditTextFullName.getText().toString().trim();
//        if (!StringUtils.isBlank(fullName)) {
            lookupRegisterViewModel.lookupClick(
                    fragmentLookupRegisterBinding.appCompatTextViewFromDate.getText().toString().trim(),
                    fragmentLookupRegisterBinding.appCompatTextViewToDate.getText().toString().trim(), fullName);
//        } else {
//            fragmentLookupRegisterBinding.appCompatEditTextFullName.setError(getString(R.string.lookup_register_field_not_be_empty));
//        }
    }


    @Override
    public void serverApprovalClick() {

    }

    @Override
    public void serverAcceptClick() {
        
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        fragmentLookupRegisterBinding = getViewDataBinding();
        lookupRegisterViewerAdapter = new LookupRegisterViewerAdapter(getContext(), new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentLookupRegisterBinding.recyclerViewLookupRegister.setLayoutManager(mLayoutManager);
        fragmentLookupRegisterBinding.recyclerViewLookupRegister.setItemAnimator(new DefaultItemAnimator());
        fragmentLookupRegisterBinding.recyclerViewLookupRegister.setAdapter(lookupRegisterViewerAdapter);
        subscribeToLiveData();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        lookupRegisterViewModel.getMutableLiveDataListLookupRegister().observe(this, x -> lookupRegisterViewerAdapter.setData(x));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lookupRegisterViewModel.setNavigator(this);
    }
}
