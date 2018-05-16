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
package com.mtramin.rxfingerprint.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.infinum.goldfinger.Error;
import co.infinum.goldfinger.Goldfinger;

public class FingerprintAuthenticationActivity extends AppCompatActivity {

    public static final String MODE_ENCRYPTION_SECRET_NAME = "applicationModeEncryptionSecret";
    public static final int MODE_ENCRYPTION_SECRET_VALUE = 0x10011;
    public static final int MODE_DECRYPTION_SECRET_VALUE = 0x10012;
    public static final String DATA_ENCRYPTION_DECRYPTION_SECRET = "dataEncryptDecryptSecret";
    public static final String EXTRA_DECRYPTION_RESULTS = "dataDecryptResult";
    public static final String EXTRA_ENCRYPTION_RESULTS = "dataEncryptResult";

    public static final int REQUEST_CODE_AUTHENTICATION = 5001;
    private String encryptionSecretForUserID = BuildConfig.FINGER_APP_TOKEN;
    private ImageView imageView;
    private ImageView imageViewFingerPrint;
    private TextView textViewErrorFingerprint;

    private Goldfinger goldfinger;
    private int applicationModeEncryptionSecret;
    private String dataEncryptDecryptSecret;

    /**
     * @param context
     * @param mode
     * @return
     */
    public static Intent newIntent(Context context, int mode, String dataEncryptDecrypt) {
        Intent intent = new Intent(context, FingerprintAuthenticationActivity.class);
        intent.putExtra(MODE_ENCRYPTION_SECRET_NAME, mode);
        intent.putExtra(DATA_ENCRYPTION_DECRYPTION_SECRET, dataEncryptDecrypt);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //
        // set screen properties
        //
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fingerprint_authentication_activity);
        Intent intent = this.getIntent();
        applicationModeEncryptionSecret = intent.getIntExtra(MODE_ENCRYPTION_SECRET_NAME, Integer.MAX_VALUE);
        dataEncryptDecryptSecret = intent.getStringExtra(DATA_ENCRYPTION_DECRYPTION_SECRET);
        imageView = (ImageView) findViewById(R.id.backButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageViewFingerPrint = (ImageView) findViewById(R.id.image_view_finger_print);
        imageViewFingerPrint.setColorFilter(0xFF000000);//0xFF163178
        textViewErrorFingerprint = (TextView) findViewById(R.id.error_fingerprint_id);
        authenticate();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     *
     */
    private void authenticate() {
        goldfinger = new Goldfinger.Builder(this).setLogEnabled(BuildConfig.DEBUG).build();
        if (goldfinger.hasFingerprintHardware()
                && goldfinger.hasEnrolledFingerprint()) {
            imageViewFingerPrint.setColorFilter(Color.GREEN);
        }
        // Now the protection level of USE_FINGERPRINT permission is normal instead of dangerous.
        // See http://developer.android.com/reference/android/Manifest.permission.html#USE_FINGERPRINT
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        else if (!goldfinger.hasEnrolledFingerprint()) {
            // This happens when no fingerprints are registered.
            imageViewFingerPrint.setColorFilter(Color.RED);
            textViewErrorFingerprint.setText("Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint");
            return;
        } else {
            imageViewFingerPrint.setColorFilter(Color.RED);
        }

        goldfinger.authenticate(new Goldfinger.Callback() {
            @Override
            public void onSuccess(String value) {
                switch (applicationModeEncryptionSecret) {
                    case MODE_ENCRYPTION_SECRET_VALUE:
                        encrypt(dataEncryptDecryptSecret);
                        break;
                    case MODE_DECRYPTION_SECRET_VALUE:
                        decrypt(dataEncryptDecryptSecret);
                        break;
                }
            }

            @Override
            public void onError(Error error) {
                imageViewFingerPrint.setColorFilter(Color.RED);//0xFF163178
            }
        });
    }

    /**
     * @param dataEncrypt
     */
    private void encrypt(String dataEncrypt) {
        goldfinger.encrypt(BuildConfig.FINGER_APP_TOKEN, dataEncrypt, new Goldfinger.Callback() {
            @Override
            public void onError(Error error) {
                imageViewFingerPrint.setColorFilter(Color.RED);
            }

            @Override
            public void onSuccess(String value) {
                imageViewFingerPrint.setColorFilter(Color.GREEN);
                Intent var2 = new Intent();
                var2.putExtra(EXTRA_ENCRYPTION_RESULTS, value);
                setResult(RESULT_OK, var2);
                finish();
            }
        });
    }

    /**
     * @param encryptedValue
     */
    private void decrypt(String encryptedValue) {
        goldfinger.decrypt(BuildConfig.FINGER_APP_TOKEN, encryptedValue, new Goldfinger.Callback() {
            @Override
            public void onError(Error error) {
                imageViewFingerPrint.setColorFilter(Color.RED);
            }

            @Override
            public void onSuccess(String value) {
                Intent var2 = new Intent();
                var2.putExtra(EXTRA_DECRYPTION_RESULTS, value);
                setResult(RESULT_OK, var2);
                finish();
            }
        });
    }
}
