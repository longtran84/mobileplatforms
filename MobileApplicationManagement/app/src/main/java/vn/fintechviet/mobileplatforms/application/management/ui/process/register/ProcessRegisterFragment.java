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

package vn.fintechviet.mobileplatforms.application.management.ui.process.register;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.application.management.BR;
import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.ProcessRegister;
import vn.fintechviet.mobileplatforms.application.management.databinding.FragmentProcessRegisterBinding;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.process.register.adapters.ProcessRegisterViewerAdapter;
import vn.fintechviet.mobileplatforms.application.management.utils.JDateFormat;
import vn.fintechviet.mobileplatforms.application.management.utils.RecyclerViewOnItemClickListener;

/**
 * Created by long_tran on 09/07/17.
 */

public class ProcessRegisterFragment extends BaseFragment<FragmentProcessRegisterBinding, ProcessRegisterViewModel> implements ProcessRegisterNavigator {

    public static final String TAG = ProcessRegisterFragment.class.getSimpleName();

    @Inject
    ProcessRegisterViewModel lookupRegisterViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    private ProcessRegisterViewerAdapter processRegisterViewerAdapter;
    private FragmentProcessRegisterBinding fragmentProcessRegisterBinding;

    /**
     * @return
     */
    public static ProcessRegisterFragment newInstance() {
        Bundle args = new Bundle();
        ProcessRegisterFragment fragment = new ProcessRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_process_register;
    }

    @Override
    public ProcessRegisterViewModel getViewModel() {
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
        String fullName = fragmentProcessRegisterBinding.appCompatEditTextFullName.getText().toString().trim();
//        if (!StringUtils.isBlank(fullName)) {
        lookupRegisterViewModel.lookupClick(
                fragmentProcessRegisterBinding.appCompatTextViewFromDate.getText().toString().trim(),
                fragmentProcessRegisterBinding.appCompatTextViewToDate.getText().toString().trim(), fullName);
//        } else {
//            fragmentProcessRegisterBinding.appCompatEditTextFullName.setError(getString(R.string.lookup_register_field_not_be_empty));
//        }
    }


    @Override
    public void serverApprovalClick() {
        if (processRegisterViewerAdapter.getItemList() == null || processRegisterViewerAdapter.getItemList().size() <= 0) {
            return;
        }
        List<String> listRegisterId = new ArrayList<>();
        for (ProcessRegister processRegister : processRegisterViewerAdapter.getItemList()) {
            if (processRegister.isSelected()) {
                listRegisterId.add(processRegister.getRegistrationId());
            }
        }
        if (listRegisterId == null || listRegisterId.size() <= 0) {
            return;
        }
        getMaterialDialogAlert(getActivity(), getString(R.string.process_register_dialog_title_confirm),
                getString(R.string.process_register_dialog_message_approval_confirm), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        lookupRegisterViewModel.updateStatusRegister("0004", listRegisterId);
                    }
                }).show();
    }

    @Override
    public void serverAcceptClick() {

    }

    @Override
    public void serverRejectClick() {
        if (processRegisterViewerAdapter.getItemList() == null || processRegisterViewerAdapter.getItemList().size() <= 0) {
            return;
        }
        List<String> listRegisterId = new ArrayList<>();
        for (ProcessRegister processRegister : processRegisterViewerAdapter.getItemList()) {
            if (processRegister.isSelected()) {
                listRegisterId.add(processRegister.getRegistrationId());
            }
        }
        if (listRegisterId == null || listRegisterId.size() <= 0) {
            return;
        }
        getMaterialDialogAlert(getActivity(), getString(R.string.process_register_dialog_title_confirm),
                getString(R.string.process_register_dialog_message_reject_confirm), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        lookupRegisterViewModel.updateStatusRegister("0006", listRegisterId);
                    }
                }).show();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        fragmentProcessRegisterBinding = getViewDataBinding();
        processRegisterViewerAdapter = new ProcessRegisterViewerAdapter(getContext(), new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentProcessRegisterBinding.recyclerViewProcessRegister.setLayoutManager(mLayoutManager);
        fragmentProcessRegisterBinding.recyclerViewProcessRegister.setItemAnimator(new DefaultItemAnimator());
        fragmentProcessRegisterBinding.recyclerViewProcessRegister.setAdapter(processRegisterViewerAdapter);
        subscribeToLiveData();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        lookupRegisterViewModel.getMutableLiveDataListProcessRegister().observe(this, x -> {
            Collections.sort(x, new Comparator<ProcessRegister>() {
                public int compare(ProcessRegister o1, ProcessRegister o2) {
                    Date date1 = JDateFormat.fromString(o1.getRegistrationDate());
                    Date date2 = JDateFormat.fromString(o2.getRegistrationDate());
                    return date2.compareTo(date1);
                }
            });
            processRegisterViewerAdapter.setData(x);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lookupRegisterViewModel.setNavigator(this);
    }
}
