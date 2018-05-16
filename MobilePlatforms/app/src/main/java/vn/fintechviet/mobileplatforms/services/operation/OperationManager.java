/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package vn.fintechviet.mobileplatforms.services.operation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import vn.fintechviet.mobileplatforms.data.model.system.Operation;
import vn.fintechviet.mobileplatforms.services.AgentDeviceAdminReceiver;
import vn.fintechviet.mobileplatforms.utils.AndroidAgentException;
import vn.fintechviet.mobileplatforms.utils.Constants;
import vn.fintechviet.mobileplatforms.utils.Preference;

public abstract class OperationManager implements VersionBasedOperations {

    private static final String TAG = OperationManager.class.getSimpleName();

    private Context context;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName cdmDeviceAdmin;
    private Resources resources;

    private static final String APP_INFO_TAG_NAME = "name";
    private static final String APP_INFO_TAG_PACKAGE = "package";
    private static final String APP_INFO_TAG_VERSION = "version";
    private static final String APP_INFO_TAG_SYSTEM = "isSystemApp";
    private static final String APP_INFO_TAG_RUNNING = "isActive";
    private static final String STATUS = "status";
    private static final String TIMESTAMP = "timestamp";

    private static final int DEFAULT_PASSWORD_LENGTH = 0;
    private static final int DEFAULT_VOLUME = 0;
    private static final int DEFAULT_FLAG = 0;
    private static final int DEFAULT_PASSWORD_MIN_LENGTH = 4;
    private static final long DAY_MILLISECONDS_MULTIPLIER = 24 * 60 * 60 * 1000;
    private static String[] AUTHORIZED_PINNING_APPS;
    private static String AGENT_PACKAGE_NAME;
    private PowerManager powerManager;

    public OperationManager(Context context) {
        this.context = context;
        this.resources = context.getResources();
        this.powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        this.devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.cdmDeviceAdmin = new ComponentName(context, AgentDeviceAdminReceiver.class);
        AGENT_PACKAGE_NAME = context.getPackageName();
        AUTHORIZED_PINNING_APPS = new String[]{AGENT_PACKAGE_NAME};
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "New OperationManager created.");
        }
    }

    /**
     * Retrieve device information.
     *
     * @param operation - Operation object.
     */
    public void getDeviceInfo(Operation operation) throws AndroidAgentException {

        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "getDeviceInfo executed.");
        }
    }

    /**
     * Retrieve location device information.
     *
     * @param operation - Operation object.
     */
    public void getLocationInfo(Operation operation) throws AndroidAgentException {

    }

    /**
     * Retrieve device application information.
     *
     * @param operation - Operation object.
     */
    public void getApplicationList(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void wipeDevice(Operation operation) throws AndroidAgentException {

    }

    /**
     * Ring the device.
     *
     * @param operation - Operation object.
     */
    public void ringDevice(Operation operation) {


        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "Ringing is activated on the device");
        }
    }

    /**
     * Disable/Enable device camera.
     *
     * @param operation - Operation object.
     */
    public void disableCamera(Operation operation) throws AndroidAgentException {
        boolean camFunc = operation.isEnabled();
        devicePolicyManager.setCameraDisabled(cdmDeviceAdmin, !camFunc);
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "Camera enabled: " + camFunc);
        }
    }

    /**
     * Mute the device.
     *
     * @param operation - Operation object.
     */
    public void muteDevice(Operation operation) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, DEFAULT_VOLUME, DEFAULT_FLAG);
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "Device muted");
        }
    }

    /**
     * Open Google Play store application with an application given.
     *
     * @param packageName - Application package name.
     */
    public void triggerGooglePlayApp(String packageName) {

    }

    /**
     * Blacklisting apps.
     *
     * @param operation - Operation object.
     */
    public void blacklistApps(Operation operation) throws AndroidAgentException {

    }

    /**
     * Uninstall application.
     *
     * @param operation - Operation object.
     */
    public void uninstallApplication(Operation operation) throws AndroidAgentException {

    }

    /**
     * Reboot the device [System app required].
     *
     * @param operation - Operation object.
     */
    public void rebootDevice(Operation operation) throws AndroidAgentException {

    }

    /**
     * Upgrading device firmware from the configured OTA server.
     *
     * @param operation - Operation object.
     */
    public void upgradeFirmware(Operation operation) throws AndroidAgentException {

    }

    /**
     * Execute shell commands as the super user.
     *
     * @param operation - Operation object.
     */
    public void executeShellCommand(Operation operation) throws AndroidAgentException {

    }

    /**
     * Lock the device.
     *
     * @param operation - Operation object.
     */
    public void lockDevice(Operation operation) throws AndroidAgentException {

    }

    public void enableHardLock(String message, Operation operation) {

    }

    /**
     * Unlock the device.
     *
     * @param operation - Operabtion object.
     */
    public void unlockDevice(Operation operation) {

    }
}
