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
package vn.fintechviet.mobileplatforms.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.facetec.zoom.sdk.FaceIDIdentification;
import com.facetec.zoom.sdk.ZoomAuthenticationResult;
import com.facetec.zoom.sdk.ZoomAuthenticationStatus;
import com.facetec.zoom.sdk.ZoomExternalImageSetVerificationResult;
import com.facetec.zoom.sdk.ZoomLivenessResult;
import com.facetec.zoom.sdk.ZoomSDK;
import com.mtramin.rxfingerprint.sdk.FingerprintAuthenticationActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.databinding.ActivityLoginBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.ui.forgot.password.ForgotPasswordActivity;
import vn.fintechviet.mobileplatforms.ui.main.MainActivity;
import vn.fintechviet.mobileplatforms.ui.register.RegisterActivity;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    @Inject
    LoginViewModel mLoginViewModel;

    private ActivityLoginBinding mActivityLoginBinding;
    private FaceIDIdentification faceIDIdentification;

    //
    // Holds images used for Audit Trail purposes
    //
    ArrayList<Bitmap> auditTrailResult;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return mLoginViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
        getMaterialDialogAlert(this, getString(R.string.login_error_warning), getString(R.string.login_error)).show();
    }

    @Override
    public void login() {
        String email = mActivityLoginBinding.etEmail.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();
        if (mLoginViewModel.isUserAndPasswordValid(email, password)) {
            hideKeyboard();
            DeviceInfoPayload deviceInfoPayload = new DeviceInfoPayload(this);
            mLoginViewModel.login(email, password, deviceInfoPayload);
        } else if (!mLoginViewModel.isEmailValid(email)) {
            mActivityLoginBinding.etEmail.setError(getString(R.string.invalid_account));
        } else if (!mLoginViewModel.isPasswordValid(password)) {
            mActivityLoginBinding.etPassword.setError(getString(R.string.invalid_password));
        }
    }

    @Override
    public void forgotPasswordClick() {
        Intent intent = ForgotPasswordActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void signUp() {
        Intent intent = RegisterActivity.newIntent(LoginActivity.this);
        startActivity(intent);
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    private String encryptedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);
        mActivityLoginBinding.appCompatTextViewForgotPasswordId.setPaintFlags(
                mActivityLoginBinding.appCompatTextViewForgotPasswordId.getPaintFlags() |
                        Paint.UNDERLINE_TEXT_FLAG);
        mActivityLoginBinding.appCompatTextViewSignUpId.setPaintFlags(
                mActivityLoginBinding.appCompatTextViewSignUpId.getPaintFlags() |
                        Paint.UNDERLINE_TEXT_FLAG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivityLoginBinding.appCompatImageViewFaceIDRecognition.setColorFilter(
                    getResources().getColor(R.color.black05, getTheme()));
            mActivityLoginBinding.appCompatImageViewFingerprintRecognition.setColorFilter(
                    getResources().getColor(R.color.black05, getTheme()));
        } else {
            mActivityLoginBinding.appCompatImageViewFaceIDRecognition.setColorFilter(
                    getResources().getColor(R.color.black05));
            mActivityLoginBinding.appCompatImageViewFingerprintRecognition.setColorFilter(
                    getResources().getColor(R.color.black05));
        }
        final ZoomSDK.InitializeCallback mInitializeCallback = new ZoomSDK.InitializeCallback() {
            @Override
            public void onCompletion(final boolean successful) {
                if (successful) {
                    mLoginViewModel.getCompositeDisposable().add(
                            Single
                                    .just(1)
                                    .subscribeOn(mLoginViewModel.getSchedulerProvider().io())
                                    .observeOn(mLoginViewModel.getSchedulerProvider().ui())
                                    .subscribe(new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                mActivityLoginBinding.appCompatImageViewFaceIDRecognition.setColorFilter(
                                                        getResources().getColor(R.color.colorPrimary, getTheme()));
                                                mActivityLoginBinding.appCompatImageViewFingerprintRecognition.setColorFilter(
                                                        getResources().getColor(R.color.colorPrimary, getTheme()));
                                            } else {
                                                mActivityLoginBinding.appCompatImageViewFaceIDRecognition.setColorFilter(
                                                        getResources().getColor(R.color.colorPrimary));
                                                mActivityLoginBinding.appCompatImageViewFingerprintRecognition.setColorFilter(
                                                        getResources().getColor(R.color.colorPrimary));
                                            }
                                        }
                                    }));
                } else {
                    throw new RuntimeException("Initialization failed.  Please check that you have set your ZoOm app token to the zoomAppToken variable in this file.  To retrieve your app token, visit https://dev.zoomlogin.com/zoomsdk/#/account.");
                }
            }
        };
        faceIDIdentification = new FaceIDIdentification();
        faceIDIdentification.initializeZoom(this, mInitializeCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void verifyWithReCaptchaClick() {

    }

    @Override
    public void faceIDRecognitionClick() {
        faceIDIdentification.onAuthClick(this, "231984");
    }

    @Override
    public void fingerprintRecognitionClick() {
        Intent intent = FingerprintAuthenticationActivity.newIntent(this,
                FingerprintAuthenticationActivity.MODE_DECRYPTION_SECRET_VALUE, "+s7PJB82CkZm3oiVznLQEA==");
        startActivityForResult(intent, FingerprintAuthenticationActivity.REQUEST_CODE_AUTHENTICATION);
    }

    private void handleZoomAuthenticationResult(ZoomAuthenticationResult zoomAuthenticationResult) {

        ZoomLivenessResult liveness = ZoomLivenessResult.LIVENESS_UNDETERMINED;
        ZoomExternalImageSetVerificationResult verificationResult = ZoomExternalImageSetVerificationResult.COULD_NOT_DETERMINE_MATCH;
        int faceFails = zoomAuthenticationResult.getCountOfFaceFailuresSinceLastSuccess();
        int fingerFails = zoomAuthenticationResult.getCountOfFingerprintFailuresSinceLastSuccess();
        int pinFails = zoomAuthenticationResult.getCountOfPinFailuresSinceLastSuccess();
        int sessionFails = zoomAuthenticationResult.getConsecutiveAuthenticationFailures();
        int lockouts = zoomAuthenticationResult.getConsecutiveLockouts();
        float livenessScore = 0;
        int numberOfAuditTrailImages = 0;

        switch (zoomAuthenticationResult.getStatus()) {
            case USER_WAS_AUTHENTICATED: {
                break;
            }
            case USER_FAILED_AUTHENTICATION_AND_WAS_DELETED: {
                break;
            }
            case USER_FAILED_AUTHENTICATION: {
                break;
            }
            case USER_WAS_AUTHENTICATED_WITH_FALLBACK_STRATEGY: {
                break;
            }
            default: {
                break;
            }
        }

        if (zoomAuthenticationResult.getFaceMetrics() != null) {

            liveness = zoomAuthenticationResult.getFaceMetrics().getLiveness();

            livenessScore = zoomAuthenticationResult.getFaceMetrics().getLivenessScore();

            verificationResult = zoomAuthenticationResult.getFaceMetrics().getExternalImageSetVerificationResult();

            numberOfAuditTrailImages = zoomAuthenticationResult.getFaceMetrics().getAuditTrail().size();
        }

        //
        // Build authentication result string
        //
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Overall Status: ");
        stringBuilder.append(zoomAuthenticationResult.getStatus().toString());

        stringBuilder.append("\nLiveness: ");
        stringBuilder.append(liveness.toString());

        stringBuilder.append("\nLiveness Score: ");
        stringBuilder.append(Float.toString(livenessScore));

        stringBuilder.append("\nExternal Image Set Verification: ");
        stringBuilder.append(verificationResult.toString());

        stringBuilder.append("\n\nFace Authenticator State: ");
        stringBuilder.append(zoomAuthenticationResult.getFaceZoomAuthenticatorState().toString());

        stringBuilder.append("\nFingerprint Authenticator State: ");
        stringBuilder.append(zoomAuthenticationResult.getFingerprintZoomAuthenticatorState().toString());

        stringBuilder.append("\nPIN Authenticator State: ");
        stringBuilder.append(zoomAuthenticationResult.getPinZoomAuthenticatorState().toString());

        stringBuilder.append("\n\nLockout Status:");

        stringBuilder.append("\n");
        stringBuilder.append((3 - lockouts - sessionFails));
        stringBuilder.append(" session failures remaining before lockout");

        stringBuilder.append("\n");
        stringBuilder.append((3 - lockouts));
        stringBuilder.append(" lockouts remaining before user deletion");

        stringBuilder.append("\n\n# of face failures since last success: ");
        stringBuilder.append(faceFails);

        stringBuilder.append("\n# of fingerprint failures since last success: ");
        stringBuilder.append(fingerFails);

        stringBuilder.append("\n# of PIN failures since last success: ");
        stringBuilder.append(pinFails);

        stringBuilder.append("\n# of Audit Trail Images: ");
        stringBuilder.append(numberOfAuditTrailImages);

        stringBuilder.append("\n# of sessions performed: ");
        stringBuilder.append(zoomAuthenticationResult.getCountOfZoomSessionsPerformed());

        String lastResult = stringBuilder.toString();

        System.err.println(lastResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        // Make sure the result was returned correctly
        //
        if (resultCode == RESULT_OK) {
            if (requestCode == ZoomSDK.REQUEST_CODE_AUTHENTICATION) {
                //
                // get the authentication results
                //
                ZoomAuthenticationResult zoomAuthenticationResult = data.getParcelableExtra(ZoomSDK.EXTRA_AUTH_RESULTS);
                //
                // retrieve the authentication audit trail image
                // note: this is enabled on a per-application basis
                // please contact support@zoomlogin.com to request access
                //
                if (zoomAuthenticationResult.getFaceMetrics() != null) {
                    auditTrailResult = zoomAuthenticationResult.getFaceMetrics().getAuditTrail();
                }

                if (zoomAuthenticationResult.getStatus() == ZoomAuthenticationStatus.USER_WAS_AUTHENTICATED) {
                    if (zoomAuthenticationResult.getSecret() != null) {
                        //
                        // The user successfully authenticated, now we can make authenticated API requests!
                        //
                        Log.d("Zoom Sample App", "Secret data returned from successful authentication: " + zoomAuthenticationResult.getSecret());
                        String email = mActivityLoginBinding.etEmail.getText().toString();
                        String password = zoomAuthenticationResult.getSecret();
                        DeviceInfoPayload deviceInfoPayload = new DeviceInfoPayload(getApplicationContext());
                        mLoginViewModel.login(email, password, deviceInfoPayload);
                    }
                } else if (requestCode == FingerprintAuthenticationActivity.REQUEST_CODE_AUTHENTICATION) {
                    String dataDecryptResult = data.getParcelableExtra(FingerprintAuthenticationActivity.EXTRA_DECRYPTION_RESULTS);
                    String email = mActivityLoginBinding.etEmail.getText().toString();
                    String password = dataDecryptResult;
                    DeviceInfoPayload deviceInfoPayload = new DeviceInfoPayload(getApplicationContext());
                    mLoginViewModel.login(email, password, deviceInfoPayload);
                } else {
                    //
                    // Handle failures, cancellation
                    //
                }

                handleZoomAuthenticationResult(zoomAuthenticationResult);
            }
        }
    }
}
