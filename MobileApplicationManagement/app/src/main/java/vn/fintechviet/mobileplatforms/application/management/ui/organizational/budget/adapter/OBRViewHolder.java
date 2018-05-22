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

package vn.fintechviet.mobileplatforms.application.management.ui.organizational.budget.adapter;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import vn.fintechviet.mobileplatforms.application.management.data.model.api.OBR;
import vn.fintechviet.mobileplatforms.application.management.databinding.ItemOrganizationalBudgetRelationshipViewBinding;
import vn.fintechviet.mobileplatforms.application.management.ui.base.BaseViewHolder;
import vn.fintechviet.mobileplatforms.application.management.ui.organizational.budget.OBRItemViewModel;
import vn.fintechviet.mobileplatforms.application.management.utils.AppLogger;

public class OBRViewHolder extends BaseViewHolder implements OBRItemViewModel.OBRItemViewModelListener {

    private ItemOrganizationalBudgetRelationshipViewBinding mBinding;

    private OBRItemViewModel mOBRItemViewModel;
    private List<OBR> itemResponseList;

    public OBRViewHolder(ItemOrganizationalBudgetRelationshipViewBinding mBinding, List<OBR> itemResponseList) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
        this.itemResponseList = itemResponseList;
    }

    @Override
    public void onBind(int position) {
        final OBR objectResponse = itemResponseList.get(position);
        mOBRItemViewModel = new OBRItemViewModel(objectResponse, this);
        mBinding.setViewModel(mOBRItemViewModel);

        // Immediate Binding
        // When a variable or observable changes, the binding will be scheduled to change before
        // the next frame. There are times, however, when binding must be executed immediately.
        // To force execution, use the executePendingBindings() method.
        mBinding.executePendingBindings();
    }

    @Override
    public void onItemClick(String blogUrl) {
        if (blogUrl != null) {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(blogUrl));
                itemView.getContext().startActivity(intent);
            } catch (Exception e) {
                AppLogger.d("url error");
            }
        }
    }
}
