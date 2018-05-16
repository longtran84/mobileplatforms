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

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import vn.fintechviet.mobileplatforms.data.model.system.Operation;
import vn.fintechviet.mobileplatforms.utils.AndroidAgentException;

public class OperationManagerBYOD extends OperationManager {

    private static final String TAG = OperationManagerBYOD.class.getSimpleName();

    public OperationManagerBYOD(Context context) {
        super(context);
    }

    @Override
    public void uploadFile(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void displayNotification(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void clearPassword(Operation operation) {

    }

    @Override
    public void installAppBundle(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void encryptStorage(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void setPasswordPolicy(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void changeLockCode(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void enterpriseWipe(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void disenrollDevice(Operation operation) {

    }

    @Override
    public void setSystemUpdatePolicy(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void hideApp(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void unhideApp(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void blockUninstallByPackageName(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void setProfileName(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void handleOwnersRestriction(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void handleDeviceOwnerRestriction(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void configureWorkProfile(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void passOperationToSystemApp(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void restrictAccessToApplications(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void setPolicyBundle(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void setRuntimePermissionPolicy(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void setStatusBarDisabled(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void setScreenCaptureDisabled(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void setAutoTimeRequired(Operation operation) throws AndroidAgentException {

    }

    @Override
    public void configureCOSUProfile(Operation operation) throws AndroidAgentException {

    }
}
