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

package vn.fintechviet.mobileplatforms.ui.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import vn.fintechviet.mobileplatforms.BR;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.Help;
import vn.fintechviet.mobileplatforms.data.model.api.HelpTreeLeaf;
import vn.fintechviet.mobileplatforms.databinding.FragmentHelpBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseFragment;
import vn.fintechviet.mobileplatforms.ui.help.details.HelpDetailActivity;
import vn.fintechviet.mobileplatforms.ui.help.holder.IconTreeItem;
import vn.fintechviet.mobileplatforms.ui.help.holder.IconTreeItemHolder;

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
    private TreeNode root;

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
        containerView = (ViewGroup) fragmentHelpBinding.container;
        root = TreeNode.root();
        androidTreeView = new AndroidTreeView(getActivity(), root);
        androidTreeView.setDefaultAnimation(false);
        androidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        androidTreeView.setDefaultViewHolder(IconTreeItemHolder.class);
        androidTreeView.setDefaultNodeClickListener(nodeClickListener);
        containerView.addView(androidTreeView.getView());
        subscribeToLiveData();
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItem item = (IconTreeItem) value;
            if(!StringUtils.isBlank(item.getBody())){
                Intent intent = HelpDetailActivity.newIntent(getActivity(), item);
                startActivity(intent);
            }
        }
    };

    /**
     *
     */
    private void subscribeToLiveData() {
        androidTreeView.removeNode(root);
        helpViewModel.getMutableLiveDataListHelp().observe(this, x -> {
            for (int i = 0; i < x.size(); i++) {
                Help help = x.get(i);
                IconTreeItem iconTreeItemHolderIconTreeItemRoot = new IconTreeItem();
                iconTreeItemHolderIconTreeItemRoot.setIcon(R.string.ic_laptop);
                iconTreeItemHolderIconTreeItemRoot.setText(help.getTitle());
                iconTreeItemHolderIconTreeItemRoot.setLeaf(false);
                TreeNode treeNodeRoot = new TreeNode(iconTreeItemHolderIconTreeItemRoot);
                for (int j = 0; j < help.getListTreeLeaf().size(); j++) {
                    HelpTreeLeaf helpTreeLeaf = help.getListTreeLeaf().get(j);
//                    IconTreeItem iconTreeItemHolderIconTreeItemLeafTitle = new IconTreeItem();
//                    iconTreeItemHolderIconTreeItemLeafTitle.setIcon(R.string.ic_folder);
//                    iconTreeItemHolderIconTreeItemLeafTitle.setText(helpTreeLeaf.getTitle());
//                    iconTreeItemHolderIconTreeItemLeafTitle.setLeaf(false);
//                    TreeNode treeNodeHelpTreeLeafTitle = new TreeNode(iconTreeItemHolderIconTreeItemLeafTitle);
                    IconTreeItem iconTreeItemHolderIconTreeItemLeaf = new IconTreeItem();
                    iconTreeItemHolderIconTreeItemLeaf.setIcon(R.string.ic_drive_file);
                    iconTreeItemHolderIconTreeItemLeaf.setText(helpTreeLeaf.getTitle());
                    iconTreeItemHolderIconTreeItemLeaf.setBody(helpTreeLeaf.getContent());
                    iconTreeItemHolderIconTreeItemLeaf.setLeaf(true);
                    TreeNode treeNodeHelpTreeLeaf = new TreeNode(iconTreeItemHolderIconTreeItemLeaf);
//                    treeNodeHelpTreeLeafTitle.addChild(treeNodeHelpTreeLeaf);
                    treeNodeRoot.addChild(treeNodeHelpTreeLeaf);
                }
                System.err.println("-------------" + help.getTitle());
                androidTreeView.addNode(root, treeNodeRoot);
            }
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
