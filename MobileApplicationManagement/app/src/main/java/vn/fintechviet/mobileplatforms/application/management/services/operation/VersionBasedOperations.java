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
package vn.fintechviet.mobileplatforms.application.management.services.operation;


import vn.fintechviet.mobileplatforms.application.management.data.model.system.Operation;
import vn.fintechviet.mobileplatforms.application.management.utils.AndroidAgentException;

/**
 * This interface represents all the Operations that can be done through EMM Agent
 */
public interface VersionBasedOperations {

    /**
     * Upload file to the server
     *
     * @param operation -Operation object.
     * @throws AndroidAgentException - AndroidAgent Exception.
     */
    void uploadFile(Operation operation) throws AndroidAgentException;

    /**
     * Wipe the device.
     *
     * @param operation - Operation object.
     */
    void wipeDevice(Operation operation) throws AndroidAgentException;

    /**
     * Display notification.
     *
     * @param operation - Operation object.
     */
    void displayNotification(Operation operation) throws AndroidAgentException;

    /**
     * Ring the device.
     *
     * @param operation - Operation object.
     */
    void ringDevice(Operation operation);

    /**
     * Clear device password.
     *
     * @param operation - Operation object.
     */
    void clearPassword(Operation operation);

    /**
     * Install application/bundle.
     *
     * @param operation - Operation object.
     */
    void installAppBundle(Operation operation) throws AndroidAgentException;

    /**
     * Encrypt/Decrypt device storage.
     *
     * @param operation - Operation object.
     */
    void encryptStorage(Operation operation) throws AndroidAgentException;

    /**
     * Set device password policy.
     *
     * @param operation - Operation object.
     */
    void setPasswordPolicy(Operation operation) throws AndroidAgentException;

    /**
     * Change device lock code.
     *
     * @param operation - Operation object.
     */
    void changeLockCode(Operation operation) throws AndroidAgentException;

    /**
     * Enterprise wipe the device.
     *
     * @param operation - Operation object.
     */
    void enterpriseWipe(Operation operation) throws AndroidAgentException;

    /**
     * Blacklisting apps.
     *
     * @param operation - Operation object.
     */
    void blacklistApps(Operation operation) throws AndroidAgentException;

    /**
     * Disenroll the device from EMM.
     */
    void disenrollDevice(Operation operation);

    /**
     * Upgrading device firmware from the configured OTA server.
     *
     * @param operation - Operation object.
     */
    void upgradeFirmware(Operation operation) throws AndroidAgentException;

    /**
     * Reboot the device [System app required].
     *
     * @param operation - Operation object.
     */
    void rebootDevice(Operation operation) throws AndroidAgentException;

    /**
     * Execute shell commands as the super user.
     *
     * @param operation - Operation object.
     */
    void executeShellCommand(Operation operation) throws AndroidAgentException;

    /**
     * Setting system update policy.
     *
     * @param operation - Operation object.
     */
    void setSystemUpdatePolicy(Operation operation) throws AndroidAgentException;

    /**
     * Hide apps by package name
     *
     * @param operation - Operation object
     */
    void hideApp(Operation operation) throws AndroidAgentException;

    /**
     * Unhide apps by package name
     *
     * @param operation - Operation object
     */
    void unhideApp(Operation operation) throws AndroidAgentException;

    /**
     * Block uninstall by package name
     *
     * @param operation - Operation object
     */
    void blockUninstallByPackageName(Operation operation) throws AndroidAgentException;

    /**
     * Set Profile Name (User name will be changed if agent is the device owner).
     *
     * @param operation - Operation object
     */
    void setProfileName(Operation operation) throws AndroidAgentException;

    /**
     * Handle User Restriction related to Profile Owner.
     *
     * @param operation - Operation object
     */
    void handleOwnersRestriction(Operation operation) throws AndroidAgentException;

    /**
     * Handle User Restriction related to Device Owner.
     *
     * @param operation - Operation object
     */
    void handleDeviceOwnerRestriction(Operation operation) throws AndroidAgentException;

    /**
     * Configure work-profile
     *
     * @param operation - Operation object
     */
    void configureWorkProfile(Operation operation) throws AndroidAgentException;

    /**
     * Pass Operation to System Service Package
     *
     * @param operation - Operation object
     */
    void passOperationToSystemApp(Operation operation) throws AndroidAgentException;

    /**
     * Restrict access to applications
     *
     * @param operation  - Operation object
     * @throws AndroidAgentException
     */
    void restrictAccessToApplications(Operation operation) throws AndroidAgentException;

    /**
     * Set policy bundle.
     *
     * @param operation - Operation object.
     */
    void setPolicyBundle(Operation operation) throws AndroidAgentException;

    /**
     * Restrict response to runtime permission requests by apps.
     * @param operation
     * @throws AndroidAgentException
     */
    void setRuntimePermissionPolicy(Operation operation) throws AndroidAgentException;

    /**
     * Disable StatusBar.
     *
     * @param operation - Operation object.
     */
    void setStatusBarDisabled(Operation operation) throws AndroidAgentException;

    /**
     * Disable ScreenCapturing.
     *
     * @param operation - Operation object.
     */
    void setScreenCaptureDisabled(Operation operation) throws AndroidAgentException;

    /**
     * Configure AutoTime requirement.
     *
     * @param operation - Operation object.
     */
    void setAutoTimeRequired(Operation operation) throws AndroidAgentException;

    /**
     * Configure COSU profile requirement.
     *
     * @param operation - Operation object.
     */
    void configureCOSUProfile(Operation operation) throws AndroidAgentException;
}