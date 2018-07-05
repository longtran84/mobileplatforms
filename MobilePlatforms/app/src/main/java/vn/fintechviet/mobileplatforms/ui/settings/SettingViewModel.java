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

package vn.fintechviet.mobileplatforms.ui.settings;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import java.util.List;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.Modules;
import vn.fintechviet.mobileplatforms.data.model.api.RegisterAccountRequest;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfo;
import vn.fintechviet.mobileplatforms.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class SettingViewModel extends BaseViewModel<SettingNavigator> {

    private final ObservableField<String> deviceName;
    private final ObservableField<String> modelNumber;
    private final ObservableField<String> androidVersion;
    private final ObservableField<String> serialNumber;
    private final ObservableField<String> IMEI;
    private final ObservableField<String> SIMSerialNumber;
    private final ObservableField<String> batteryPercent;
    private final ObservableField<String> wifiMacAddress;


    public SettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        deviceName = new ObservableField<>();
        modelNumber = new ObservableField<>();
        androidVersion = new ObservableField<>();
        serialNumber = new ObservableField<>();
        IMEI = new ObservableField<>();
        SIMSerialNumber = new ObservableField<>();
        batteryPercent = new ObservableField<>();
        wifiMacAddress = new ObservableField<>();
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getDeviceName() {
        return deviceName;
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getModelNumber() {
        return modelNumber;
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getAndroidVersion() {
        return androidVersion;
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getSerialNumber() {
        return serialNumber;
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getIMEI() {
        return IMEI;
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getSIMSerialNumber() {
        return SIMSerialNumber;
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getBatteryPercent() {
        return batteryPercent;
    }

    /**
     *
     * @return
     */
    public ObservableField<String> getWifiMacAddress() {
        return wifiMacAddress;
    }

    /**
     *
     */
    public void dataFetching(Activity activity) {
        setIsLoading(false);
        DeviceInfo deviceInfo = new DeviceInfo(activity);
        deviceName.set(deviceInfo.getDeviceName());
        modelNumber.set(deviceInfo.getModel());
        androidVersion.set(deviceInfo.getOSVersion());
        serialNumber.set(deviceInfo.getDeviceSerialNumber());
        IMEI.set(deviceInfo.getDeviceId());
        SIMSerialNumber.set(deviceInfo.getSimSerialNumber());
        batteryPercent.set(String.format("%d%%", deviceInfo.getBatteryPercent()));
        wifiMacAddress.set(deviceInfo.getWifiMacAddress(activity));
    }
}
