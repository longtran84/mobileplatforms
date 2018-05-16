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

import vn.fintechviet.mobileplatforms.databinding.ItemOrganizationalBudgetRelationshipEmptyViewBinding;
import vn.fintechviet.mobileplatforms.ui.base.BaseViewHolder;
import vn.fintechviet.mobileplatforms.ui.organizational.budget.OBREmptyItemViewModel;

public class EmptyViewHolder extends BaseViewHolder implements OBREmptyItemViewModel.BlogEmptyItemViewModelListener {

    private ItemOrganizationalBudgetRelationshipEmptyViewBinding mBinding;
    private OBRAdapter.BlogAdapterListener mListener;

    public EmptyViewHolder(ItemOrganizationalBudgetRelationshipEmptyViewBinding mBinding, OBRAdapter.BlogAdapterListener mListener) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
        this.mListener = mListener;
    }

    @Override
    public void onBind(int position) {
        OBREmptyItemViewModel emptyItemViewModel = new OBREmptyItemViewModel(this);
        mBinding.setViewModel(emptyItemViewModel);
    }

    @Override
    public void onRetryClick() {
        mListener.onRetryClick();
    }
}