/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package vn.fintechviet.mobileplatforms.application.management.services;

import android.annotation.TargetApi;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.utils.CommonUtils;
import vn.fintechviet.mobileplatforms.application.management.utils.Constants;
import vn.fintechviet.mobileplatforms.application.management.utils.Preference;

import java.util.Map;

/**
 * This is the component that is responsible for actual device administration.
 * It becomes the receiver when a policy is applied. It is important that we
 * subclass DeviceAdminReceiver class here and to implement its only required
 * method onEnabled().
 */
public class AgentDeviceAdminReceiver extends DeviceAdminReceiver {

    private static final String TAG = AgentDeviceAdminReceiver.class.getName();
    public static final String DISALLOW_SAFE_BOOT = "no_safe_boot";

    /**
     * Called when this application is approved to be a device administrator.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onEnabled(final Context context, Intent intent) {
        super.onEnabled(context, intent);
        Preference.putBoolean(context, Constants.PreferenceFlag.DEVICE_ACTIVE, true);
    }

    /**
     * Called when this application is no longer the device administrator.
     */
    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Preference.putBoolean(context, Constants.PreferenceFlag.DEVICE_ACTIVE, false);
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "onPasswordChanged.");
        }
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        super.onPasswordFailed(context, intent);
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "onPasswordFailed.");
        }
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        super.onPasswordSucceeded(context, intent);
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "onPasswordSucceeded.");
        }
    }

    /**
     * Generates a {@link ComponentName} that is used throughout the app.
     *
     * @return a {@link ComponentName}
     */
    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), AgentDeviceAdminReceiver.class);
    }

    public void onLockTaskModeEntering(Context context, Intent intent, String pkg) {
        Toast.makeText(context, "Device is locked", Toast.LENGTH_LONG).show();
    }

    public void onLockTaskModeExiting(Context context, Intent intent) {
        Toast.makeText(context, "Device is unlocked", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUserRestriction(DevicePolicyManager devicePolicyManager,
                                    ComponentName adminComponentName, String restriction, boolean disallow) {
        if (disallow) {
            devicePolicyManager.addUserRestriction(adminComponentName, restriction);
        } else {
            devicePolicyManager.clearUserRestriction(adminComponentName, restriction);
        }
    }

}
