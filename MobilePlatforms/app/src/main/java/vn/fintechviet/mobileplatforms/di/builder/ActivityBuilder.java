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

package vn.fintechviet.mobileplatforms.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vn.fintechviet.mobileplatforms.ui.account.balance.AccountBalanceActivity;
import vn.fintechviet.mobileplatforms.ui.account.balance.AccountBalanceActivityModule;
import vn.fintechviet.mobileplatforms.ui.change.password.ChangePasswordFragmentProvider;
import vn.fintechviet.mobileplatforms.ui.forgot.password.ForgotPasswordActivity;
import vn.fintechviet.mobileplatforms.ui.forgot.password.ForgotPasswordActivityModule;
import vn.fintechviet.mobileplatforms.ui.home.HomeFragmentProvider;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivity;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivityModule;
import vn.fintechviet.mobileplatforms.ui.lookup.document.LookupDocumentActivity;
import vn.fintechviet.mobileplatforms.ui.lookup.document.LookupDocumentActivityModule;
import vn.fintechviet.mobileplatforms.ui.lookup.register.LookupRegisterFragmentProvider;
import vn.fintechviet.mobileplatforms.ui.main.MainActivity;
import vn.fintechviet.mobileplatforms.ui.main.MainActivityModule;
import vn.fintechviet.mobileplatforms.ui.messages.MessagesFragmentProvider;
import vn.fintechviet.mobileplatforms.ui.organizational.budget.OBRActivity;
import vn.fintechviet.mobileplatforms.ui.organizational.budget.OBRActivityModule;
import vn.fintechviet.mobileplatforms.ui.process.register.ProcessRegisterFragmentProvider;
import vn.fintechviet.mobileplatforms.ui.profile.ProfileFragmentProvider;
import vn.fintechviet.mobileplatforms.ui.register.RegisterActivity;
import vn.fintechviet.mobileplatforms.ui.register.RegisterActivityModule;
import vn.fintechviet.mobileplatforms.ui.reminder.ReminderFragmentProvider;
import vn.fintechviet.mobileplatforms.ui.splash.SplashActivity;
import vn.fintechviet.mobileplatforms.ui.splash.SplashActivityModule;

/**
 * Created by long_tran on 14/09/17.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            HomeFragmentProvider.class,
            ChangePasswordFragmentProvider.class,
            MessagesFragmentProvider.class,
            ProfileFragmentProvider.class,
            ReminderFragmentProvider.class,
            LookupRegisterFragmentProvider.class,
            ProcessRegisterFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = ForgotPasswordActivityModule.class)
    abstract ForgotPasswordActivity bindForgotPasswordActivity();

    @ContributesAndroidInjector(modules = OBRActivityModule.class)
    abstract OBRActivity bindOBRActivity();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = AccountBalanceActivityModule.class)
    abstract AccountBalanceActivity bindAccountBalanceActivity();

    @ContributesAndroidInjector(modules = LookupDocumentActivityModule.class)
    abstract LookupDocumentActivity bindLookupDocumentActivity();
}
