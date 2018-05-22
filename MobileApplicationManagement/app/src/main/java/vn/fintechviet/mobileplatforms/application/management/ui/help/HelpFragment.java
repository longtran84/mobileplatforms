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

package vn.fintechviet.mobileplatforms.application.management.ui.help;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.application.management.BR;
import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.Help;
import vn.fintechviet.mobileplatforms.application.management.data.model.api.HelpTreeLeaf;
import vn.fintechviet.mobileplatforms.application.management.databinding.FragmentHelpBinding;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.application.management.ui.help.holder.IconTreeItemHolder;

/**
 * Created by long_tran on 09/07/17.
 */

public class HelpFragment extends BaseFragment<FragmentHelpBinding, HelpViewModel> implements HelpNavigator {

    public static final String TAG = HelpFragment.class.getSimpleName();

    @Inject
    HelpViewModel helpViewModel;

    private FragmentHelpBinding fragmentHelpBinding;
    private AndroidTreeView androidTreeView;
    private ViewGroup containerView;

    public static HelpFragment newInstance() {
        Bundle args = new Bundle();
        HelpFragment fragment = new HelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_help;
    }

    @Override
    public HelpViewModel getViewModel() {
        return helpViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentHelpBinding = getViewDataBinding();
        subscribeToLiveData();
        containerView = (ViewGroup) fragmentHelpBinding.container;
        helpViewModel.dataFetching();
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            System.err.println("Last clicked: " + item.text);
        }
    };

    /**
     *
     */
    private void subscribeToLiveData() {
        helpViewModel.getMutableLiveDataListHelp().observe(this, x -> {
            TreeNode root = TreeNode.root();
            for (int i = 0; i < x.size(); i++) {
                Help help = x.get(i);
                TreeNode treeNodeRoot = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_laptop, help.getTitle(), false));
                for (int j = 0; j < help.getListTreeLeaf().size(); j++) {
                    HelpTreeLeaf helpTreeLeaf = help.getListTreeLeaf().get(j);
                    TreeNode treeNodeHelpTreeLeafTitle = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, helpTreeLeaf.getTitle(), false));
                    TreeNode treeNodeHelpTreeLeaf = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_drive_file, helpTreeLeaf.getContent(), true));
                    treeNodeHelpTreeLeafTitle.addChild(treeNodeHelpTreeLeaf);
                    treeNodeRoot.addChild(treeNodeHelpTreeLeafTitle);
                }
                root.addChildren(treeNodeRoot);
            }
            androidTreeView = new AndroidTreeView(getActivity(), root);
            androidTreeView.setDefaultAnimation(true);
            androidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
            androidTreeView.setDefaultViewHolder(IconTreeItemHolder.class);
            androidTreeView.setDefaultNodeClickListener(nodeClickListener);
            containerView.addView(androidTreeView.getView());
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpViewModel.setNavigator(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            helpViewModel.dataFetching();
        }
    }
}
