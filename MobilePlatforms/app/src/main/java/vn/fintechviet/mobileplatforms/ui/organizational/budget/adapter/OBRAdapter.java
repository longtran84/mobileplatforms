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

package vn.fintechviet.mobileplatforms.ui.organizational.budget.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import vn.fintechviet.mobileplatforms.data.model.api.OBR;
import vn.fintechviet.mobileplatforms.databinding.ItemOrganizationalBudgetRelationshipEmptyViewBinding;
import vn.fintechviet.mobileplatforms.databinding.ItemOrganizationalBudgetRelationshipViewBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by long_tran on 10/07/17.
 */

public class OBRAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<OBR> mOBRResponseList;

    private BlogAdapterListener mListener;

    public OBRAdapter(List<OBR> mOBRResponseList) {
        this.mOBRResponseList = mOBRResponseList;
    }

    @Override
    public int getItemCount() {
        if (mOBRResponseList != null && mOBRResponseList.size() > 0) {
            return mOBRResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mOBRResponseList != null && !mOBRResponseList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemOrganizationalBudgetRelationshipViewBinding itemOrganizationalBudgetRelationshipViewBinding =
                        ItemOrganizationalBudgetRelationshipViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                                parent, false);
                return new OBRViewHolder(itemOrganizationalBudgetRelationshipViewBinding, mOBRResponseList);
            case VIEW_TYPE_EMPTY:
            default:
                ItemOrganizationalBudgetRelationshipEmptyViewBinding itemOrganizationalBudgetRelationshipEmptyViewBinding =
                        ItemOrganizationalBudgetRelationshipEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                                parent, false);
                return new EmptyViewHolder(itemOrganizationalBudgetRelationshipEmptyViewBinding, mListener);
        }
    }

    public void addItems(List<OBR> obrs) {
        mOBRResponseList.addAll(obrs);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mOBRResponseList.clear();
    }

    public void setListener(BlogAdapterListener listener) {
        this.mListener = listener;
    }

    public interface BlogAdapterListener {

        void onRetryClick();
    }
}