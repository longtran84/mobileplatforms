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

package vn.fintechviet.mobileplatforms.application.management.ui.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import vn.bcy.vgca.simtoolkit.VGCAToolkit;
import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.data.DataManager;
import vn.fintechviet.mobileplatforms.application.management.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.application.management.utils.Constants;
import vn.fintechviet.mobileplatforms.application.management.utils.Preference;
import vn.fintechviet.mobileplatforms.application.management.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 08/07/17.
 */

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    private final String TAG = SplashViewModel.class.getSimpleName();
    private final int FCM_TOKEN_WAIT_MILLIS = 60000;
    private Disposable disposable;

    /**
     * @param dataManager
     * @param schedulerProvider
     */
    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     *
     */
    public void dataFetching(DeviceInfoPayload deviceInfoPayload) {
        getCompositeDisposable().add(getDataManager()
                .doServerAccountVerificationApiCall(deviceInfoPayload.getSerialNumber())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(accountVerificationResponse -> {
                    if (accountVerificationResponse != null && accountVerificationResponse.getData() != null) {
                        Boolean accountVerification = accountVerificationResponse.getData();
                        if (accountVerification.booleanValue()) {
                            getNavigator().accountActive();
                        } else {
                            getNavigator().accountSuspension();
                        }
                    }
                    setIsLoading(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    setIsLoading(false);
                }));
    }

    /**
     *
     */
    public void startSeeding(Context context) {
        TedRx2Permission.with(context)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message) // "we need permission for read contact and find your location"
                .setPermissions(
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                        Manifest.permission.RECEIVE_SMS)
                .request()
                .subscribe(tedPermissionResult -> {
                            if (tedPermissionResult.isGranted()) {
                                if (Constants.DEBUG_MODE_ENABLED) {
                                    //Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                                }
                                disposable = Observable
                                        .interval(1, TimeUnit.SECONDS, Schedulers.io())
                                        .map(aLong -> aLong)
                                        .subscribe(x -> {
                                            String token = FirebaseInstanceId.getInstance().getToken();
                                            if (!StringUtils.isBlank(token)) {
                                                if (Constants.DEBUG_MODE_ENABLED) {
                                                    Log.d(TAG, "FCM Token: " + token);
                                                }
                                                getDataManager().setFireBaseCloudMessagingToken(token);
                                                //Preference.putString(context, Constants.FCM_REG_ID, token);
                                                decideNextActivity();
                                                disposable.dispose();
                                            }
                                        });

                            } else {
                                if (Constants.DEBUG_MODE_ENABLED) {
                                    Toast.makeText(context,
                                            "Permission Denied\n" + tedPermissionResult.getDeniedPermissions().toString(), Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        },
                        throwable -> {
                        },
                        () -> {
                        });
    }

    /**
     * This will start the FCM flow by registering the device with Google and sending the
     * registration ID to IoTS. This is done in a Async task as a network call may be done, and
     * it should be done out side the UI thread. After retrieving the registration Id, it is send
     * to the MDM server so that it can send notifications to the device.
     */
    public void registerFCM(Context context) {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            if (Constants.DEBUG_MODE_ENABLED) {
                Log.d(TAG, "FCM Token: " + token);
            }
            Preference.putString(context, Constants.FCM_REG_ID, token);
        } else {
            Log.w(TAG, "FCM Token is null. Will depend on FCMInstanceIdService.");
            Preference.removePreference(context, Constants.FCM_REG_ID);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, FCM_TOKEN_WAIT_MILLIS);
        }
    }

    /**
     *
     */
    private void decideNextActivity() {
        if (StringUtils.isBlank(getDataManager().getAuthorization())) {
            getNavigator().openLoginActivity();
        } else {
            getNavigator().openMainActivity();
        }
    }

    /**
     *
     */
    public void sign(Activity activity) {
        getCompositeDisposable().add(
                Single
                        .just(1)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                try {
                                    String sourceCert = VGCAToolkit.GetCert("01682927581");
                                    System.err.println(sourceCert +"-----------------");
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        }));
    }
}
