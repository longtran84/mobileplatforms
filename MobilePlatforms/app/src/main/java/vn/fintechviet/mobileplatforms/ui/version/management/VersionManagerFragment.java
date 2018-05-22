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

package vn.fintechviet.mobileplatforms.ui.version.management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.BuildConfig;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.CheckUpdateRequest;
import vn.fintechviet.mobileplatforms.data.model.api.VersionManager;
import vn.fintechviet.mobileplatforms.databinding.FragmentVersionManagerBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.ui.version.management.adapters.VersionManagerViewerAdapter;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnObjectClickListener;

/**
 * Created by long_tran on 09/07/17.
 */

public class VersionManagerFragment extends BaseFragment<FragmentVersionManagerBinding, VersionManagerViewModel> implements VersionManagerNavigator {

    public static final String TAG = VersionManagerFragment.class.getSimpleName();

    @Inject
    VersionManagerViewModel messagesViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;

    private VersionManagerViewerAdapter versionManagerViewerAdapter;
    private FragmentVersionManagerBinding fragmentVersionManagerBinding;

    public static VersionManagerFragment newInstance() {
        Bundle args = new Bundle();
        VersionManagerFragment fragment = new VersionManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.versionManagerViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_version_manager;
    }

    @Override
    public VersionManagerViewModel getViewModel() {
        return messagesViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentVersionManagerBinding = getViewDataBinding();
        versionManagerViewerAdapter = new VersionManagerViewerAdapter(getContext(), new RecyclerViewOnObjectClickListener<VersionManager>() {
            @Override
            public void onClick(View v, int position, VersionManager messages) {

            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fragmentVersionManagerBinding.recyclerViewVersionManager.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(getResources().getColor(R.color.black10));
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        fragmentVersionManagerBinding.recyclerViewVersionManager.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity()).paint(paint).build());
        fragmentVersionManagerBinding.recyclerViewVersionManager.setLayoutManager(mLayoutManager);
        fragmentVersionManagerBinding.recyclerViewVersionManager.setItemAnimator(new DefaultItemAnimator());
        fragmentVersionManagerBinding.recyclerViewVersionManager.setAdapter(versionManagerViewerAdapter);
        subscribeToLiveData();
    }

    /**
     *
     */
    private void subscribeToLiveData() {
        messagesViewModel.getMutableLiveDataListVersionManager().observe(this, x -> versionManagerViewerAdapter.setData(x));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagesViewModel.setNavigator(this);
    }

    @Override
    public void onUpdateClick() {
        getViewModel().setIsLoading(true);
        CheckUpdateRequest checkUpdateRequest = new CheckUpdateRequest();
        checkUpdateRequest.setAppCode(getActivity().getPackageName());
        checkUpdateRequest.setVersion(BuildConfig.VERSION_NAME);
        getViewModel().getCompositeDisposable().add(getViewModel().getDataManager()
                .doServerCheckUpdateApiCall(checkUpdateRequest)
                .subscribeOn(getViewModel().getSchedulerProvider().io())
                .observeOn(getViewModel().getSchedulerProvider().ui())
                .subscribe(checkUpdateResponse -> {
                    getViewModel().setIsLoading(false);
                    if (checkUpdateResponse != null) {
                        if (checkUpdateResponse.isUpdate()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setCancelable(false);
                            builder.setTitle(getString(R.string.you_are_not_updated_title));
                            builder.setIcon(R.drawable.android_app_on_google_play);
                            builder.setMessage(getString(R.string.button_text_message_update_apk));
                            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                                    dialog.dismiss();
                                    getActivity().finish();
                                    System.exit(0);
                                }
                            });
                            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    getActivity().finish();
                                }
                            });
                            builder.show();
                        }
                    }
                }, throwable -> {
                    getViewModel().setIsLoading(false);
                }));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            messagesViewModel.dataFetching(getActivity().getPackageName());
        }
    }
}
