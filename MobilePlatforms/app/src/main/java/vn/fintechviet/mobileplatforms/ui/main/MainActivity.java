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

package vn.fintechviet.mobileplatforms.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.yokeyword.fragmentation.SupportFragment;
import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.BuildConfig;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.databinding.ActivityMainBinding;
import vn.fintechviet.mobileplatforms.databinding.LayoutNavigationHeaderMainBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.ui.change.password.ChangePasswordFragment;
import vn.fintechviet.mobileplatforms.ui.home.HomeFragment;
import vn.fintechviet.mobileplatforms.ui.login.LoginActivity;
import vn.fintechviet.mobileplatforms.ui.lookup.register.LookupRegisterFragment;
import vn.fintechviet.mobileplatforms.ui.messages.MessagesFragment;
import vn.fintechviet.mobileplatforms.ui.process.register.ProcessRegisterFragment;
import vn.fintechviet.mobileplatforms.ui.profile.ProfileFragment;
import vn.fintechviet.mobileplatforms.ui.reminder.ReminderFragment;
import vn.fintechviet.mobileplatforms.ui.widget.BottomNavigationViewEx;
import vn.fintechviet.mobileplatforms.utils.AppConstants;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private ActivityMainBinding mActivityMainBinding;
    private DrawerLayout mDrawer;
    private MainViewModel mMainViewModel;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    private HomeFragment homeFragment;
    private MessagesFragment messageFragment;
    private ChangePasswordFragment changePasswordFragment;
    private ReminderFragment reminderFragment;
    private LookupRegisterFragment lookupRegisterFragment;
    private ProcessRegisterFragment processRegisterFragment;
    private ProfileFragment profileFragment;

    private int hideFragment = AppConstants.TYPE_HOME_FRAGMENT;
    private int showFragment = AppConstants.TYPE_MESSAGE_FRAGMENT;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    /**
     * @param item
     * @return
     */
    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case AppConstants.TYPE_HOME_FRAGMENT:
                return homeFragment;
            case AppConstants.TYPE_MESSAGE_FRAGMENT:
                return messageFragment;
            case AppConstants.TYPE_CHANGE_PASSWORD_FRAGMENT:
                return changePasswordFragment;
            case AppConstants.TYPE_REMINDER_FRAGMENT:
                return reminderFragment;
            case AppConstants.TYPE_REGISTRATION_LOOKUP:
                return lookupRegisterFragment;
            case AppConstants.TYPE_REGISTRATION_PROCESSING:
                return processRegisterFragment;
            case AppConstants.TYPE_PROFILE_FRAGMENT:
                return profileFragment;
            default:
                return homeFragment;
        }
    }


    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }


    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(MainActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void setUp() {
        mDrawer = mActivityMainBinding.drawerView;
        mToolbar = mActivityMainBinding.toolbar;
        mNavigationView = mActivityMainBinding.navigationView;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        setupBottomNavigation();
        setupNavigationMenu();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mMainViewModel.updateAppVersion(version);
        subscribeToLiveData();
        mMainViewModel.loadUserProfile();
        TextView textView = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.textViewNavigationHeader);
    }

    /**
     *
     */
    private void setupBottomNavigation() {
        homeFragment = HomeFragment.newInstance();
        messageFragment = MessagesFragment.newInstance();
        changePasswordFragment = ChangePasswordFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        reminderFragment = ReminderFragment.newInstance();
        lookupRegisterFragment = LookupRegisterFragment.newInstance();
        processRegisterFragment = ProcessRegisterFragment.newInstance();
        mActivityMainBinding.bottomNavigationViewEx.getMenu().clear();
        mActivityMainBinding.bottomNavigationViewEx.inflateMenu(R.menu.bottom_navigation);
        mActivityMainBinding.bottomNavigationViewEx.enableShiftingMode(false);
        mActivityMainBinding.bottomNavigationViewEx.enableItemShiftingMode(false);
        mActivityMainBinding.bottomNavigationViewEx.setTextVisibility(true);
        //bottomNavigationViewEx.setIconSize(28, 28);
        mActivityMainBinding.bottomNavigationViewEx.setTextSize(9f);
        showFragment = AppConstants.TYPE_HOME_FRAGMENT;
        hideFragment = AppConstants.TYPE_HOME_FRAGMENT;
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        mActivityMainBinding.bottomNavigationViewEx.setCurrentItem(0);
        mActivityMainBinding.bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        showFragment = AppConstants.TYPE_HOME_FRAGMENT;
                        break;
                    case R.id.navigation_message:
                        showFragment = AppConstants.TYPE_MESSAGE_FRAGMENT;
                        break;
                    default:
                        showFragment = AppConstants.TYPE_HOME_FRAGMENT;
                        break;
                }
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            }
        });
        loadMultipleRootFragment(R.id.fl_main_content, 0, homeFragment, messageFragment,
                changePasswordFragment, reminderFragment, lookupRegisterFragment, profileFragment,
                processRegisterFragment);
    }

    private void setupNavigationMenu() {
        LayoutNavigationHeaderMainBinding layoutNavigationHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.layout_navigation_header_main, mActivityMainBinding.navigationView, false);
        mActivityMainBinding.navigationView.addHeaderView(layoutNavigationHeaderMainBinding.getRoot());
        layoutNavigationHeaderMainBinding.setViewModel(mMainViewModel);
        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.navigation_profile_id:
                            showFragment = AppConstants.TYPE_PROFILE_FRAGMENT;
                            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                            hideFragment = showFragment;
                            return true;
                        case R.id.navigation_change_your_password_id:
                            showFragment = AppConstants.TYPE_CHANGE_PASSWORD_FRAGMENT;
                            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                            hideFragment = showFragment;
                            return true;
                        case R.id.navigation_reminder_register_id:
                            showFragment = AppConstants.TYPE_REMINDER_FRAGMENT;
                            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                            hideFragment = showFragment;
                            return true;
                        case R.id.navigation_registration_lookup_id:
                            showFragment = AppConstants.TYPE_REGISTRATION_LOOKUP;
                            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                            hideFragment = showFragment;
                            return true;
                        case R.id.navigation_registration_process_id:
                            showFragment = AppConstants.TYPE_REGISTRATION_PROCESSING;
                            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                            hideFragment = showFragment;
                            return true;
                        case R.id.navigation_share_information_id:
                            return true;
                        case R.id.navigation_help_tutorial_id:
                            return true;
                        case R.id.navigation_sign_out_id:
                            mMainViewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void showAboutFragment() {
        lockDrawer();
    }

    private void subscribeToLiveData() {
        mMainViewModel.getUserProfileData().observe(this, userProfile -> mMainViewModel.onNavigationMenuUpdateUserProfile(userProfile));
    }

    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
