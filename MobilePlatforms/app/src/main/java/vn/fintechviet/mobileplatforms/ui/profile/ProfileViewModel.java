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

package vn.fintechviet.mobileplatforms.ui.profile;

import android.databinding.ObservableField;

import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.model.api.UserProfile;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewModel;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 09/07/17.
 */

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {

    private ObservableField<String> fullName;
    private ObservableField<String> profileEmail;
    private ObservableField<String> profilePosition;
    private ObservableField<String> profileDepartment;
    private ObservableField<String> profilePhone;
    private ObservableField<String> profileMobile;
    private ObservableField<String> profileAddress;


    public ProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        fullName = new ObservableField<>();
        profileEmail = new ObservableField<>();
        profilePosition = new ObservableField<>();
        profileDepartment = new ObservableField<>();
        profilePhone = new ObservableField<>();
        profileMobile = new ObservableField<>();
        profileAddress = new ObservableField<>();
    }

    public ObservableField<String> getFullName() {
        return fullName;
    }

    public ObservableField<String> getProfileEmail() {
        return profileEmail;
    }

    public ObservableField<String> getProfilePosition() {
        return profilePosition;
    }

    public ObservableField<String> getProfileDepartment() {
        return profileDepartment;
    }

    public ObservableField<String> getProfilePhone() {
        return profilePhone;
    }

    public ObservableField<String> getProfileMobile() {
        return profileMobile;
    }

    public ObservableField<String> getProfileAddress() {
        return profileAddress;
    }

    /**
     *
     */
    public void dataFetching() {
        setIsLoading(true);
        loadUserProfile();
    }

    /**
     *
     */
    private void loadUserProfile() {
        String accessToken = getDataManager().getAccessToken().trim();
        getCompositeDisposable().add(getDataManager()
                .doServerUserProfileApiCall(accessToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(profileResponse -> {
                    if (profileResponse != null) {
                        UserProfile userProfile = profileResponse.getData();
                        fullName.set(userProfile.getFullName());
                        profileEmail.set(userProfile.getEmail());
                        profilePosition.set(userProfile.getPosition());
                        profileDepartment.set(userProfile.getDepartment());
                        profilePhone.set(userProfile.getPosition());
                        profileMobile.set(userProfile.getMobile());
                        profileAddress.set(userProfile.getAddress());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }

    /**
     *
     */
    public void onServerProfileUpdateClick() {
        getNavigator().profileUpdateClick();
    }

    /**
     *
     */
    public void onServerProfileEditClick() {
        getNavigator().profileEditClick();
    }

    /**
     *
     * @param userProfile
     */
    protected void profileUpdate(UserProfile userProfile){

    }

}
