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

package vn.fintechviet.mobileplatforms.ui.base;

import android.annotation.NonNull;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

import me.yokeyword.fragmentation.SupportActivity;
import vn.fintechviet.mobileplatforms.BuildConfig;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.CheckUpdateRequest;
import vn.fintechviet.mobileplatforms.data.model.api.CheckUpdateResponse;
import vn.fintechviet.mobileplatforms.data.model.api.UserProfile;
import vn.fintechviet.mobileplatforms.utils.CommonUtils;
import vn.fintechviet.mobileplatforms.utils.NetworkUtils;

import dagger.android.AndroidInjection;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by long_tran on 07/07/17.
 */

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends SupportActivity
        implements BaseFragment.Callback {

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;
    private T mViewDataBinding;
    private V mViewModel;
    private MutableLiveData<CheckUpdateResponse> mutableLiveDataCheckUpdateResponse;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mutableLiveDataCheckUpdateResponse = new MutableLiveData<>();
        performDataBinding();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
//        startActivity(LoginActivity.newIntent(this));
//        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public MutableLiveData<CheckUpdateResponse> getMutableLiveDataCheckUpdateResponse() {
        return mutableLiveDataCheckUpdateResponse;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    /**
     *
     */
    private void subscribeToLiveData() {

    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    /**
     *
     * @param title
     */
    public void setToolbar(CharSequence title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //setResult(Activity.RESULT_OK, new Intent());
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /****
     * @return
     */
    public MaterialDialog getMaterialDialogAlert(Activity activity, String title, String content) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(R.string.ok)
                .titleColorRes(R.color.red)
                .positiveColorRes(R.color.colorPrimary)
                .autoDismiss(false)
                .cancelable(false)
                .iconRes(R.drawable.vietnam_state_treasury)
                .maxIconSize(96)
                .canceledOnTouchOutside(false)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
    }

    /****
     * @return
     */
    public MaterialDialog getMaterialDialogAlert(Activity activity, String title, String content,
                                                 MaterialDialog.SingleButtonCallback singleButtonCallback) {
        return new MaterialDialog.Builder(activity)
                .titleColorRes(R.color.colorPrimary)
                .title(title)
                .content(content)
                .iconRes(R.drawable.vietnam_state_treasury)
                .maxIconSize(96)
                .contentColor(Color.BLACK)
                .positiveColor(0xFF157efb)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(singleButtonCallback)
                .build();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }
}

