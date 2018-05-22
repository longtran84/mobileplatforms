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
package vn.fintechviet.mobileplatforms.ui.register;

import android.annotation.ColorInt;
import android.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.facetec.zoom.sdk.FaceIDIdentification;
import com.facetec.zoom.sdk.ZoomEnrollmentResult;
import com.facetec.zoom.sdk.ZoomEnrollmentStatus;
import com.facetec.zoom.sdk.ZoomSDK;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mtramin.rxfingerprint.sdk.FingerprintAuthenticationActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.RegisterAccountRequest;
import vn.fintechviet.mobileplatforms.databinding.ActivityRegisterBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivity;
import vn.fintechviet.mobileplatforms.utils.CommonUtils;
import vn.fintechviet.mobileplatforms.utils.PasswordValidator;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator {

    @Inject
    RegisterViewModel registerViewModel;

    private final String siteKey = "6Lf42lgUAAAAAMd7uvDnCPnPgcFeP4Ad-7HOrnhH";
    private final String secretKey = "6Lf42lgUAAAAAN4eqJtpmqa_t_OpJjRlAnudZgzN";
    private ActivityRegisterBinding activityRegisterBinding;
    private List<String> listModule;
    //private SpinnerCustomAdapter<Modules> spinnerCustomAdapter;
    private FaceIDIdentification faceIDIdentification;
    //
    // Holds images used for Audit Trail purposes
    //
    ArrayList<Bitmap> auditTrailResult;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterViewModel getViewModel() {
        return registerViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
        getMaterialDialogAlert(this, getString(R.string.register_error_warning),
                getString(R.string.register_error)).show();
    }

    @Override
    public void handleSuccessfully() {
        getMaterialDialogAlert(this, getString(R.string.register_title_notification),
                getString(R.string.register_notification_successfully), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        openLoginActivity();
                    }
                }).show();
    }

    @Override
    public void registerClick() {
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest();
        registerAccountRequest.setOrganizationName(activityRegisterBinding.registerOrganizationName.getText().toString().trim());
        registerAccountRequest.setOrganizationAddress(activityRegisterBinding.registerOrganizationAddress.getText().toString().trim());
        registerAccountRequest.setOrganizationEmail(activityRegisterBinding.registerOrganizationWebSite.getText().toString().trim());
        registerAccountRequest.setOrganizationTelephone(activityRegisterBinding.registerOrganizationTelephone.getText().toString().trim());

        registerAccountRequest.setFullName(activityRegisterBinding.registerFullName.getText().toString().trim());
        registerAccountRequest.setEmail(activityRegisterBinding.registerEmail.getText().toString().trim());
        registerAccountRequest.setMobile(activityRegisterBinding.registerMobile.getText().toString().trim());

        registerAccountRequest.setUserName(activityRegisterBinding.registerAccount.getText().toString().trim());
        registerAccountRequest.setPassword(activityRegisterBinding.registerPassword.getText().toString().trim());
        registerAccountRequest.setListModule(listModule);
        PasswordValidator passwordValidator = new PasswordValidator();
        if (StringUtils.isEmpty(registerAccountRequest.getOrganizationName())) {
            activityRegisterBinding.registerOrganizationName.setError(getString(R.string.register_not_be_empty));
        } else if (StringUtils.isEmpty(registerAccountRequest.getOrganizationAddress())) {
            activityRegisterBinding.registerOrganizationAddress.setError(getString(R.string.register_not_be_empty));
        } else if (StringUtils.isEmpty(registerAccountRequest.getOrganizationEmail())) {
            activityRegisterBinding.registerOrganizationWebSite.setError(getString(R.string.register_not_be_empty));
        } else if (StringUtils.isEmpty(registerAccountRequest.getOrganizationTelephone())) {
            activityRegisterBinding.registerOrganizationTelephone.setError(getString(R.string.register_not_be_empty));
        } else if (StringUtils.isEmpty(registerAccountRequest.getFullName())) {
            activityRegisterBinding.registerFullName.setError(getString(R.string.register_not_be_empty));
        } else if (StringUtils.isEmpty(registerAccountRequest.getEmail())) {
            activityRegisterBinding.registerEmail.setError(getString(R.string.register_not_be_empty));
        } else if (!CommonUtils.isEmailValid(registerAccountRequest.getEmail())) {
            activityRegisterBinding.registerEmail.setError(getString(R.string.register_invalid_email));
        } else if (StringUtils.isEmpty(registerAccountRequest.getMobile())) {
            activityRegisterBinding.registerMobile.setError(getString(R.string.register_not_be_empty));
        } else if (StringUtils.isEmpty(registerAccountRequest.getUserName())) {
            activityRegisterBinding.registerAccount.setError(getString(R.string.register_not_be_empty));
        } else if (!passwordValidator.validate(registerAccountRequest.getPassword())) {
            activityRegisterBinding.registerPassword.setError(getString(R.string.register_password_invalidate_message));
        } else if (!passwordValidator.validate(activityRegisterBinding.registerConfirmPassword.getText().toString().trim())) {
            activityRegisterBinding.registerConfirmPassword.setError(getString(R.string.register_confirm_password_invalidate_message));
        } else if (!TextUtils.equals(registerAccountRequest.getPassword(), activityRegisterBinding.registerConfirmPassword.getText().toString().trim())) {
            activityRegisterBinding.registerPassword.setError(getString(R.string.password_confirmation_password_do_not_match));
            activityRegisterBinding.registerConfirmPassword.setError(getString(R.string.password_confirmation_password_do_not_match));
        } else if (listModule.size() <= 0) {
            activityRegisterBinding.appCompatTextViewChooseApp.setTextColor(Color.RED);
        } else {
            registerViewModel.registerAccount(registerAccountRequest);
            activityRegisterBinding.appCompatCheckBoxVerifyReCaptcha.setBackgroundResource(R.drawable.square_outline);
            activityRegisterBinding.appCompatButtonRegister.setEnabled(false);
        }
    }

    @Override
    public void verifyWithReCaptchaClick() {
        SafetyNet.getClient(this).verifyWithRecaptcha(siteKey)
                .addOnSuccessListener(this,
                        new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                // Indicates communication with reCAPTCHA service was
                                // successful.
                                String userResponseToken = response.getTokenResult();
                                if (!userResponseToken.isEmpty()) {
                                    //new Check().execute();
                                    activityRegisterBinding.appCompatCheckBoxVerifyReCaptcha.setBackgroundResource(R.drawable.check);
                                    activityRegisterBinding.appCompatButtonRegister.setEnabled(true);
                                }
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            // An error occurred when communicating with the
                            // reCAPTCHA service. Refer to the status code to
                            // handle the error appropriately.
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();
                            //Log.d(TAG, "Error: " + CommonStatusCodes.getStatusCodeString(statusCode));
                        } else {
                            // A different, unknown type of error occurred.
                            //Log.d(TAG, "Error: " + e.getMessage());
                        }
                        activityRegisterBinding.appCompatCheckBoxVerifyReCaptcha.setBackgroundResource(R.drawable.square_outline);
                    }
                });
    }

    @Override
    public void faceIDRecognitionClick() {
        String registerPassword = activityRegisterBinding.registerPassword.getText().toString().trim();
        String registerConfirmPassword = activityRegisterBinding.registerConfirmPassword.getText().toString().trim();
        PasswordValidator passwordValidator = new PasswordValidator();
        if (!passwordValidator.validate(registerPassword)) {
            activityRegisterBinding.registerPassword.setError(getString(R.string.register_password_invalidate_message));
        } else if (!passwordValidator.validate(registerConfirmPassword)) {
            activityRegisterBinding.registerConfirmPassword.setError(getString(R.string.register_confirm_password_invalidate_message));
        } else if (!TextUtils.equals(registerPassword, registerConfirmPassword)) {
            activityRegisterBinding.registerPassword.setError(getString(R.string.password_confirmation_password_do_not_match));
            activityRegisterBinding.registerConfirmPassword.setError(getString(R.string.password_confirmation_password_do_not_match));
        } else {
            faceIDIdentification.onEnrollClick(this, "231984", activityRegisterBinding.registerPassword.getText().toString().trim());
        }
    }

    @Override
    public void fingerprintRecognitionClick() {
        String registerPassword = activityRegisterBinding.registerPassword.getText().toString().trim();
        String registerConfirmPassword = activityRegisterBinding.registerConfirmPassword.getText().toString().trim();
        PasswordValidator passwordValidator = new PasswordValidator();
        if (!passwordValidator.validate(registerPassword)) {
            activityRegisterBinding.registerPassword.setError(getString(R.string.register_password_invalidate_message));
        } else if (!passwordValidator.validate(registerConfirmPassword)) {
            activityRegisterBinding.registerConfirmPassword.setError(getString(R.string.register_confirm_password_invalidate_message));
        } else if (!TextUtils.equals(registerPassword, registerConfirmPassword)) {
            activityRegisterBinding.registerPassword.setError(getString(R.string.password_confirmation_password_do_not_match));
            activityRegisterBinding.registerConfirmPassword.setError(getString(R.string.password_confirmation_password_do_not_match));
        } else {
            Intent intent = FingerprintAuthenticationActivity.newIntent(this,
                    FingerprintAuthenticationActivity.MODE_ENCRYPTION_SECRET_VALUE, activityRegisterBinding.registerPassword.getText().toString().trim());
            startActivityForResult(intent, FingerprintAuthenticationActivity.REQUEST_CODE_AUTHENTICATION);
        }
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(getString(R.string.register_application));
        activityRegisterBinding = getViewDataBinding();
        registerViewModel.setNavigator(this);
        listModule = new ArrayList<>();
        //spinnerCustomAdapter = new SpinnerCustomAdapter<Modules>(this, android.R.layout.simple_spinner_item);
        //spinnerCustomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //activityRegisterBinding.appCompatSpinnerModule.setAdapter(spinnerCustomAdapter);
        activityRegisterBinding.registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //
                registerViewModel.getCompositeDisposable().add(
                        Single
                                .just(1)
                                .subscribeOn(registerViewModel.getSchedulerProvider().io())
                                .observeOn(registerViewModel.getSchedulerProvider().ui())
                                .subscribe(new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer integer) throws Exception {
                                        activityRegisterBinding.appCompatImageViewFaceIDRecognition.setColorFilter(Color.RED);
                                        activityRegisterBinding.appCompatImageViewFaceIDRecognition.setVisibility(View.VISIBLE);
                                    }
                                }));
            }
        });
        activityRegisterBinding.registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //
                registerViewModel.getCompositeDisposable().add(
                        Single
                                .just(1)
                                .subscribeOn(registerViewModel.getSchedulerProvider().io())
                                .observeOn(registerViewModel.getSchedulerProvider().ui())
                                .subscribe(new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer integer) throws Exception {
                                        activityRegisterBinding.appCompatImageViewFaceIDRecognition.setColorFilter(Color.RED);
                                        activityRegisterBinding.appCompatImageViewFaceIDRecognition.setVisibility(View.VISIBLE);
                                    }
                                }));
            }
        });
        activityRegisterBinding.registerPasswordPolicy.setText(Html.fromHtml(getText(R.string.password_policy).toString()));
        activityRegisterBinding.appCompatImageViewFaceIDRecognition.setVisibility(View.GONE);
        activityRegisterBinding.appCompatImageViewFingerprintRecognition.setVisibility(View.GONE);
        final ZoomSDK.InitializeCallback mInitializeCallback = new ZoomSDK.InitializeCallback() {
            @Override
            public void onCompletion(final boolean successful) {
                if (successful) {
                    registerViewModel.getCompositeDisposable().add(
                            Single
                                    .just(1)
                                    .subscribeOn(registerViewModel.getSchedulerProvider().io())
                                    .observeOn(registerViewModel.getSchedulerProvider().ui())
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            activityRegisterBinding.appCompatImageViewFaceIDRecognition.setColorFilter(Color.RED);
                                            activityRegisterBinding.appCompatImageViewFaceIDRecognition.setVisibility(View.VISIBLE);
                                            activityRegisterBinding.appCompatImageViewFingerprintRecognition.setColorFilter(Color.RED);
                                            activityRegisterBinding.appCompatImageViewFingerprintRecognition.setVisibility(View.VISIBLE);
                                        }
                                    }));
                } else {
                    throw new RuntimeException("Initialization failed.  Please check that you have set your ZoOm app token to the zoomAppToken variable in this file.  To retrieve your app token, visit https://dev.zoomlogin.com/zoomsdk/#/account.");
                }
            }
        };
        faceIDIdentification = new FaceIDIdentification();
        faceIDIdentification.initializeZoom(this, mInitializeCallback);
        subscribeToLiveData();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        final List<KeyPairBoolData> listKeyPairBoolData = new ArrayList<>();
        KeyPairBoolData keyPairBoolDataDefault = new KeyPairBoolData();
        keyPairBoolDataDefault.setId(1);
        keyPairBoolDataDefault.setName("No data!");
        keyPairBoolDataDefault.setSelected(false);
        listKeyPairBoolData.add(keyPairBoolDataDefault);
        activityRegisterBinding.appCompatSpinnerModule.setItems(listKeyPairBoolData, -1, new SpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> list) {

            }
        });
        registerViewModel.getMutableLiveDataListModules().observe(this, x -> {
            listKeyPairBoolData.clear();
            for (int i = 0; i < x.size(); i++) {
                KeyPairBoolData keyPairBoolData = new KeyPairBoolData();
                keyPairBoolData.setId(i + 1);
                keyPairBoolData.setName(x.get(i).getName());
                keyPairBoolData.setSelected(false);
                listKeyPairBoolData.add(keyPairBoolData);
            }
            activityRegisterBinding.appCompatSpinnerModule.setItems(listKeyPairBoolData, -1, new SpinnerListener() {

                @Override
                public void onItemsSelected(List<KeyPairBoolData> items) {

                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).isSelected()) {
                            listModule.add(x.get(i).getUuid());
                            //Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                        }
                    }
                    activityRegisterBinding.appCompatTextViewChooseApp.setTextColor(Color.BLACK);
                }
            });
        });
    }

    /**
     * @param zoomEnrollmentResult
     */
    private void handleZoomEnrollmentResult(ZoomEnrollmentResult zoomEnrollmentResult) {
        //
        // Build enrollment result string
        //
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Status: ");
        stringBuilder.append(zoomEnrollmentResult.getStatus().toString());

        if (zoomEnrollmentResult.getFaceMetrics() != null) {
            stringBuilder.append("\nLiveness: ");
            stringBuilder.append(zoomEnrollmentResult.getFaceMetrics().getLiveness().toString());
            stringBuilder.append("\nLiveness Score: ");
            stringBuilder.append(Float.toString(zoomEnrollmentResult.getFaceMetrics().getLivenessScore()));
            stringBuilder.append("\nExternal Image Set Verification: ");
            stringBuilder.append(zoomEnrollmentResult.getFaceMetrics().getExternalImageSetVerificationResult().toString());
        }

        stringBuilder.append("\n\nFace Enrollment: ");
        stringBuilder.append(zoomEnrollmentResult.getFaceEnrollmentState().toString());

        stringBuilder.append("\nFingerprint Enrollment: ");
        stringBuilder.append(zoomEnrollmentResult.getFingerprintEnrollmentState().toString());

        stringBuilder.append("\nPIN Enrollment: ");
        stringBuilder.append(zoomEnrollmentResult.getPinEnrollmentState().toString());

        stringBuilder.append("\n# of sessions performed: ");
        stringBuilder.append(zoomEnrollmentResult.getCountOfZoomSessionsPerformed());

        String lastResult = stringBuilder.toString();

        System.err.print(lastResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        // Make sure the result was returned correctly
        //
        if (resultCode == RESULT_OK) {
            //
            // Check if this was an enrollment intent
            //
            if (requestCode == ZoomSDK.REQUEST_CODE_ENROLLMENT) {
                //
                // Get the enrollment results
                //
                ZoomEnrollmentResult zoomEnrollmentResult = data.getParcelableExtra(ZoomSDK.EXTRA_ENROLL_RESULTS);

                //
                // retrieve the enrollment audit trail image
                // note: this is enabled on a per-application basis
                // please contact support@zoomlogin.com to request access
                //
                if (zoomEnrollmentResult.getFaceMetrics() != null) {
                    auditTrailResult = zoomEnrollmentResult.getFaceMetrics().getAuditTrail();
                }

                if (zoomEnrollmentResult.getStatus() == ZoomEnrollmentStatus.USER_WAS_ENROLLED || zoomEnrollmentResult.getStatus() == ZoomEnrollmentStatus.USER_WAS_ENROLLED_WITH_FALLBACK_STRATEGY) {
                    //
                    // The user successfully enrolled, now you can call authenticate!
                    //
                    registerViewModel.getCompositeDisposable().add(
                            Single
                                    .just(1)
                                    .subscribeOn(registerViewModel.getSchedulerProvider().io())
                                    .observeOn(registerViewModel.getSchedulerProvider().ui())
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                activityRegisterBinding.appCompatImageViewFaceIDRecognition.setColorFilter(getResources().getColor(R.color.colorPrimary, getTheme()));
                                            } else {
                                                activityRegisterBinding.appCompatImageViewFaceIDRecognition.setColorFilter(getResources().getColor(R.color.colorPrimary));
                                            }
                                            activityRegisterBinding.appCompatImageViewFaceIDRecognition.setVisibility(View.VISIBLE);
                                        }
                                    }));
                } else {
                    //
                    // Handle failures, cancellation
                    //
                }

                //
                // Handle the enrollment result
                //
                handleZoomEnrollmentResult(zoomEnrollmentResult);
            } else if (requestCode == FingerprintAuthenticationActivity.REQUEST_CODE_AUTHENTICATION) {
                registerViewModel.getDataManager().setFingerprintDataEncrypt(data.getStringExtra(FingerprintAuthenticationActivity.EXTRA_ENCRYPTION_RESULTS));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activityRegisterBinding.appCompatImageViewFingerprintRecognition.setColorFilter(getResources().getColor(R.color.colorPrimary, getTheme()));
                } else {
                    activityRegisterBinding.appCompatImageViewFingerprintRecognition.setColorFilter(getResources().getColor(R.color.colorPrimary));
                }
                activityRegisterBinding.appCompatImageViewFingerprintRecognition.setVisibility(View.VISIBLE);
            }
        }
    }
}
