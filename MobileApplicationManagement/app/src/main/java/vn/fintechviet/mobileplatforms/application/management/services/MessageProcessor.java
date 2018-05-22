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

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import vn.fintechviet.mobileplatforms.application.management.data.model.system.DeviceInfo;
import vn.fintechviet.mobileplatforms.application.management.data.model.system.Operation;
import vn.fintechviet.mobileplatforms.application.management.services.operation.OperationProcessor;
import vn.fintechviet.mobileplatforms.application.management.utils.AndroidAgentException;
import vn.fintechviet.mobileplatforms.application.management.utils.Constants;
import vn.fintechviet.mobileplatforms.application.management.utils.Preference;

/**
 * This class handles all the functionality related to coordinating the retrieval
 * and processing of messages from the server.
 */
public class MessageProcessor {

    private static final String TAG = MessageProcessor.class.getSimpleName();

    private static final String ERROR_STATE = "ERROR";
    private static volatile long invokedTimestamp = 0;
    private Context context;
    private String deviceId;
    private OperationProcessor operationProcessor;

    /**
     * Local notification message handler.
     *
     * @param context Context of the application.
     */
    public MessageProcessor(Context context) {
        this.context = context;
        deviceId = Preference.getString(context, Constants.PreferenceFlag.DEVICE_ID_PREFERENCE_KEY);
        operationProcessor = new OperationProcessor(context.getApplicationContext());
        if (deviceId == null) {
            DeviceInfo deviceInfo = new DeviceInfo(context.getApplicationContext());
            deviceId = deviceInfo.getDeviceId();
            Preference.putString(context, Constants.PreferenceFlag.DEVICE_ID_PREFERENCE_KEY, deviceId);
        }
        invokedTimestamp = Calendar.getInstance().getTimeInMillis();
    }

    static long getInvokedTimeStamp() {
        return invokedTimestamp;
    }

    /**
     * Call the message retrieval end point of the server to get messages pending.
     */
    public void getMessages() throws AndroidAgentException {
        operationProcessor.doTask(new Operation());
    }
}
