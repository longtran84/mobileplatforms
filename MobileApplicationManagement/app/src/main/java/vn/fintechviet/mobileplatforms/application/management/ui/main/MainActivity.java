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

package vn.fintechviet.mobileplatforms.application.management.ui.main;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.leolin.shortcutbadger.ShortcutBadger;
import me.yokeyword.fragmentation.SupportFragment;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import vn.fintechviet.mobileplatforms.application.management.BR;
import vn.fintechviet.mobileplatforms.application.management.BuildConfig;
import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.CheckUpdateRequest;
import vn.fintechviet.mobileplatforms.application.management.data.model.system.DeviceInfoPayload;
import vn.fintechviet.mobileplatforms.application.management.databinding.ActivityMainBinding;
import vn.fintechviet.mobileplatforms.application.management.databinding.LayoutNavigationHeaderMainBinding;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseActivity;
import vn.fintechviet.mobileplatforms.application.management.ui.change.password.ChangePasswordFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.help.HelpFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.home.HomeFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.login.LoginActivity;
import vn.fintechviet.mobileplatforms.application.management.ui.lookup.register.LookupRegisterFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.messages.MessagesFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.process.register.ProcessRegisterFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.profile.ProfileFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.reminder.ReminderFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.widget.BottomNavigationViewEx;
import vn.fintechviet.mobileplatforms.application.management.utils.AppConstants;

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
    private HelpFragment helpFragment;

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
            case AppConstants.TYPE_HELP:
                return helpFragment;
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
    public void accountSuspension() {
        getMaterialDialogAlert(this, getString(R.string.warning), getString(R.string.account_suspended_message), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
                System.exit(0);
            }
        }).show();
    }

    @Override
    public void accountActive() {
        CheckUpdateRequest checkUpdateRequest = new CheckUpdateRequest();
        checkUpdateRequest.setAppCode(getPackageName());
        checkUpdateRequest.setVersion(BuildConfig.VERSION_NAME);
        getViewModel().getCompositeDisposable().add(getViewModel().getDataManager()
                .doServerCheckUpdateApiCall(checkUpdateRequest)
                .subscribeOn(getViewModel().getSchedulerProvider().io())
                .observeOn(getViewModel().getSchedulerProvider().ui())
                .subscribe(checkUpdateResponse -> {
                    if (checkUpdateResponse != null) {
                        getMutableLiveDataCheckUpdateResponse().setValue(checkUpdateResponse);
                    }
                }, throwable -> {

                }));
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
        DeviceInfoPayload deviceInfoPayload = new DeviceInfoPayload(this);
        mMainViewModel.dataFetching(deviceInfoPayload);
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
        helpFragment = HelpFragment.newInstance();
        mActivityMainBinding.bottomNavigationViewEx.getMenu().clear();
        mActivityMainBinding.bottomNavigationViewEx.inflateMenu(R.menu.bottom_navigation);
        mActivityMainBinding.bottomNavigationViewEx.enableShiftingMode(false);
        mActivityMainBinding.bottomNavigationViewEx.enableItemShiftingMode(false);
        mActivityMainBinding.bottomNavigationViewEx.setTextVisibility(true);
        //bottomNavigationViewEx.setIconSize(28, 28);
        mActivityMainBinding.bottomNavigationViewEx.setTextSize(9f);
        showFragment = AppConstants.TYPE_REMINDER_FRAGMENT;
        hideFragment = AppConstants.TYPE_REMINDER_FRAGMENT;
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
//        mActivityMainBinding.bottomNavigationViewEx.setCurrentItem(0);
//        mActivityMainBinding.bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.navigation_home:
//                        showFragment = AppConstants.TYPE_HOME_FRAGMENT;
//                        break;
//                    case R.id.navigation_message:
//                        showFragment = AppConstants.TYPE_MESSAGE_FRAGMENT;
//                        break;
//                    default:
//                        showFragment = AppConstants.TYPE_HOME_FRAGMENT;
//                        break;
//                }
//                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
//                hideFragment = showFragment;
//                return true;
//            }
//        });
        loadMultipleRootFragment(R.id.fl_main_content, 0, reminderFragment, homeFragment, messageFragment,
                changePasswordFragment, lookupRegisterFragment, profileFragment,
                processRegisterFragment, helpFragment);
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
                            showFragment = AppConstants.TYPE_HELP;
                            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                            hideFragment = showFragment;
                            return true;
                        case R.id.navigation_messages_id:
                            showFragment = AppConstants.TYPE_MESSAGE_FRAGMENT;
                            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                            hideFragment = showFragment;
                            return true;
                        case R.id.navigation_sign_out_id:
                            mMainViewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
        bindingBadge();
    }

    private Badge badge;

    private void bindingBadge() {
//        MenuItem item = mNavigationView.getMenu().findItem(R.id.navigation_reminder_register_id);
//        MenuItemCompat.setActionView(item, R.layout.feed_update_count);
//        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
//        TextView tv = (TextView) notifCount.findViewById(R.id.textMenuItemCount);
//        String count = "4";
//        if (count != null) {
//            tv.setText(count);
//        }else{
//            tv.setText("");
//            item.setEnabled(false);
//        }
        TextView textViewBadge = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().
                findItem(R.id.navigation_reminder_register_id));
        RelativeLayout.LayoutParams linearLayoutLayoutParams = new RelativeLayout.LayoutParams(64, 64);
        textViewBadge.setPadding(5,5,5,5);
        textViewBadge.setLayoutParams(linearLayoutLayoutParams);
        textViewBadge.setBackground(getResources().getDrawable(R.drawable.vietnam_state_treasury));
        textViewBadge.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textViewBadge.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewBadge.setTextColor(Color.WHITE);
        textViewBadge.setText("");
        textViewBadge.setBackgroundResource(R.drawable.oval);
        textViewBadge.setVisibility(View.GONE);
//        Menu menuNav = mNavigationView.getMenu();
//        MenuItem element = menuNav.findItem(R.id.navigation_reminder_register_id);
//        String before = element.getTitle().toString();
//
//        String counter = Integer.toString(5);
//        String s = before + "   " + counter + " ";
//        SpannableString sColored = new SpannableString(s);
//
//        sColored.setSpan(new BackgroundColorSpan(Color.RED), s.length() - 3, s.length(), 0);
//        sColored.setSpan(new ForegroundColorSpan(Color.WHITE), s.length() - 3, s.length(), 0);
//        element.setTitle(sColored);

//
//        MenuItem reminderRegister = mNavigationView.getMenu().findItem(R.id.navigation_reminder_register_id);
//        ViewGroup view = (ViewGroup) reminderRegister.getActionView();
//
//        if (null == badge) {
//            badge = new QBadgeView(getApplicationContext()).bindTarget(view);
//        }
//        int count = 10;
//        ShortcutBadger.applyCount(getApplicationContext(), count);
//        badge.setBadgeNumber(count == 0 ? -1 : count);
//        badge.setBadgeTextSize(14, true);
//        badge.setBadgePadding(6, true);
//        badge.setShowShadow(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     */
    private void showAboutFragment() {
        lockDrawer();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        mMainViewModel.getUserProfileData().observe(this, userProfile -> mMainViewModel.onNavigationMenuUpdateUserProfile(userProfile));
        getMutableLiveDataCheckUpdateResponse().observe(this, checkUpdateResponse -> {
            if (checkUpdateResponse.isUpdate()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle(getString(R.string.you_are_not_updated_title));
                builder.setIcon(R.drawable.android_app_on_google_play);
                builder.setMessage(getString(R.string.button_text_message_update_apk));
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        dialog.dismiss();
                        finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            }
        });
    }

    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
