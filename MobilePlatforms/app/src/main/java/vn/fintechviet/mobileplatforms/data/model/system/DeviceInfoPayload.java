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
package vn.fintechviet.mobileplatforms.data.model.system;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class DeviceInfoPayload {

    @Expose
    @SerializedName("serialNumber")
    private String serialNumber;

    @Expose
    @SerializedName("model")
    private String model;

    @Expose
    @SerializedName("manufacture")
    private String manufacture;

    @Expose
    @SerializedName("osBuilddate")
    private String osBuildDate;

    @Expose
    @SerializedName("osVersion")
    private String osVersion;

    @Expose
    @SerializedName("imei")
    private String imei;

    @Expose
    @SerializedName("fcmToken")
    private String fcmToken;

    @Expose
    @SerializedName("deviceName")
    private String deviceName;

    @Expose
    @SerializedName("owner")
    private String owner;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public DeviceInfoPayload(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo(context);
        this.serialNumber = deviceInfo.getDeviceSerialNumber();
        this.model = deviceInfo.getModel();
        this.manufacture = deviceInfo.getManufacturer();
        this.osBuildDate = deviceInfo.getOSBuildDate();
        this.osVersion = deviceInfo.getOSVersion();
        this.imei = deviceInfo.getDeviceId();
        this.deviceName = deviceInfo.getDeviceName();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getOsBuildDate() {
        return osBuildDate;
    }

    public void setOsBuildDate(String osBuildDate) {
        this.osBuildDate = osBuildDate;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
