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
package vn.fintechviet.mobileplatforms.services.operation;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.system.Operation;
import vn.fintechviet.mobileplatforms.services.AgentDeviceAdminReceiver;
import vn.fintechviet.mobileplatforms.utils.AndroidAgentException;
import vn.fintechviet.mobileplatforms.utils.Constants;
import vn.fintechviet.mobileplatforms.utils.Preference;

import java.io.IOException;
import java.util.List;

/**
 * This class handles all the functionalities related to device management operations.
 */
public class OperationProcessor {

    private OperationManager operationManager;
    private Context context;

    private static final String TAG = OperationProcessor.class.getSimpleName();

    public OperationProcessor(Context context) {
        this.context = context;

        /* Get matching OperationManager from the Factory */
        OperationManagerFactory operationManagerFactory = new OperationManagerFactory(context);
        operationManager = operationManagerFactory.getOperationManager();
    }

    /**
     * Get device operation manager
     *
     * @return instance of OperationManager
     */
    public OperationManager getOperationManager() {
        return operationManager;
    }

    /**
     * Executes device management operations on the device.
     *
     * @param operation - Operation object.
     */
    public void doTask(Operation operation) throws AndroidAgentException {
        switch (operation.getCode()) {
            case Constants.Operation.DEVICE_INFO:
                operationManager.getDeviceInfo(operation);
                break;
            case Constants.Operation.DEVICE_LOCATION:
                operationManager.getLocationInfo(operation);
                break;
            case Constants.Operation.APPLICATION_LIST:
                operationManager.getApplicationList(operation);
                break;
            case Constants.Operation.REMOTE_CONNECT:

                break;
            case Constants.Operation.DEVICE_LOCK:
                operationManager.lockDevice(operation);
                break;
            case Constants.Operation.DEVICE_UNLOCK:
                operationManager.unlockDevice(operation);
                break;
            case Constants.Operation.WIPE_DATA:
                operationManager.wipeDevice(operation);
                break;
            case Constants.Operation.CLEAR_PASSWORD:
                operationManager.clearPassword(operation);
                break;
            case Constants.Operation.NOTIFICATION:
                operationManager.displayNotification(operation);
                break;
            case Constants.Operation.WIFI:

                break;
            case Constants.Operation.CAMERA:
                operationManager.disableCamera(operation);
                break;
            case Constants.Operation.INSTALL_APPLICATION:
            case Constants.Operation.INSTALL_APPLICATION_BUNDLE:
            case Constants.Operation.UPDATE_APPLICATION:
                operationManager.installAppBundle(operation);
                break;
            case Constants.Operation.UNINSTALL_APPLICATION:
                operationManager.uninstallApplication(operation);
                break;
            case Constants.Operation.ENCRYPT_STORAGE:
                operationManager.encryptStorage(operation);
                break;
            case Constants.Operation.DEVICE_RING:
                 operationManager.ringDevice(operation);
                break;
            case Constants.Operation.FILE_DOWNLOAD:

                break;
            case Constants.Operation.FILE_UPLOAD:
                operationManager.uploadFile(operation);
                break;
            case Constants.Operation.DEVICE_MUTE:
                operationManager.muteDevice(operation);
                break;
            case Constants.Operation.WEBCLIP:

                break;
            case Constants.Operation.PASSWORD_POLICY:
                operationManager.setPasswordPolicy(operation);
                break;
            case Constants.Operation.INSTALL_GOOGLE_APP:

                break;
            case Constants.Operation.CHANGE_LOCK_CODE:
                operationManager.changeLockCode(operation);
                break;
            case Constants.Operation.POLICY_BUNDLE:

                break;
            case Constants.Operation.WORK_PROFILE:
                operationManager.configureWorkProfile(operation);
                break;
            case Constants.Operation.POLICY_MONITOR:

                break;
            case Constants.Operation.POLICY_REVOKE:

                break;
            case Constants.Operation.ENTERPRISE_WIPE:
                operationManager.enterpriseWipe(operation);
                break;
            case Constants.Operation.BLACKLIST_APPLICATIONS:
                operationManager.blacklistApps(operation);
                break;
            case Constants.Operation.DISENROLL:
                operationManager.disenrollDevice(operation);
                break;
            case Constants.Operation.UPGRADE_FIRMWARE:
                operationManager.upgradeFirmware(operation);
                break;
            case Constants.Operation.REBOOT:
                operationManager.rebootDevice(operation);
                break;
            case Constants.Operation.EXECUTE_SHELL_COMMAND:
                operationManager.executeShellCommand(operation);
                break;
            case Constants.Operation.SYSTEM_UPDATE_POLICY:
                operationManager.setSystemUpdatePolicy(operation);
                break;
            case Constants.Operation.RUNTIME_PERMISSION_POLICY:
                operationManager.setRuntimePermissionPolicy(operation);
                break;
            case Constants.Operation.ALLOW_PARENT_PROFILE_APP_LINKING:
            case Constants.Operation.DISALLOW_CONFIG_VPN:
            case Constants.Operation.DISALLOW_INSTALL_APPS:
                if (Constants.SYSTEM_APP_ENABLED){
                    operationManager.passOperationToSystemApp(operation);
                } else {
                    operationManager.handleOwnersRestriction(operation);
                }
                break;
            case Constants.Operation.VPN:

                break;
            case Constants.Operation.APP_RESTRICTION:
                operationManager.restrictAccessToApplications(operation);
                break;
            case Constants.Operation.COSU_PROFILE_POLICY:
                operationManager.configureCOSUProfile(operation);
            case Constants.Operation.LOGCAT:

                break;
            case Constants.Operation.DISALLOW_ADJUST_VOLUME:
            case Constants.Operation.DISALLOW_SMS:
            case Constants.Operation.DISALLOW_CONFIG_CELL_BROADCASTS:
            case Constants.Operation.DISALLOW_CONFIG_BLUETOOTH:
            case Constants.Operation.DISALLOW_CONFIG_MOBILE_NETWORKS:
            case Constants.Operation.DISALLOW_CONFIG_TETHERING:
            case Constants.Operation.DISALLOW_CONFIG_WIFI:
            case Constants.Operation.DISALLOW_SAFE_BOOT:
            case Constants.Operation.DISALLOW_OUTGOING_CALLS:
            case Constants.Operation.DISALLOW_MOUNT_PHYSICAL_MEDIA:
            case Constants.Operation.DISALLOW_CREATE_WINDOWS:
            case Constants.Operation.DISALLOW_FACTORY_RESET:
            case Constants.Operation.DISALLOW_REMOVE_USER:
            case Constants.Operation.DISALLOW_ADD_USER:
            case Constants.Operation.DISALLOW_NETWORK_RESET:
            case Constants.Operation.DISALLOW_UNMUTE_MICROPHONE:
            case Constants.Operation.DISALLOW_USB_FILE_TRANSFER:
                operationManager.handleDeviceOwnerRestriction(operation);
                break;
            case Constants.Operation.DISALLOW_CONFIG_CREDENTIALS:
            case Constants.Operation.DISALLOW_APPS_CONTROL:
            case Constants.Operation.DISALLOW_CROSS_PROFILE_COPY_PASTE:
            case Constants.Operation.DISALLOW_DEBUGGING_FEATURES:
            case Constants.Operation.DISALLOW_INSTALL_UNKNOWN_SOURCES:
            case Constants.Operation.DISALLOW_MODIFY_ACCOUNTS:
            case Constants.Operation.DISALLOW_OUTGOING_BEAM:
            case Constants.Operation.DISALLOW_SHARE_LOCATION:
            case Constants.Operation.DISALLOW_UNINSTALL_APPS:
            case Constants.Operation.ENSURE_VERIFY_APPS:
                operationManager.handleOwnersRestriction(operation);
                break;
            case Constants.Operation.AUTO_TIME:
                operationManager.setAutoTimeRequired(operation);
            case Constants.Operation.SET_SCREEN_CAPTURE_DISABLED:
                operationManager.setScreenCaptureDisabled(operation);
                break;
            case Constants.Operation.SET_STATUS_BAR_DISABLED:
                operationManager.setStatusBarDisabled(operation);
                break;
            default:
                operationManager.passOperationToSystemApp(operation);
                break;
        }
    }

    private boolean isDeviceAdminActive() {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName cdmDeviceAdmin = new ComponentName(context, AgentDeviceAdminReceiver.class);
        return devicePolicyManager.isAdminActive(cdmDeviceAdmin);
    }

    /**
     * This method checks whether there are any previous notifications which were not sent
     * and send if found any.
     */
    public void checkPreviousNotifications() throws AndroidAgentException {
//        List<Operation> operations = NotificationService.getInstance(context.getApplicationContext()).checkPreviousNotifications();
//        for (Operation operation : operations) {
//            operationManager.getResultBuilder().build(operation);
//        }
    }

}

