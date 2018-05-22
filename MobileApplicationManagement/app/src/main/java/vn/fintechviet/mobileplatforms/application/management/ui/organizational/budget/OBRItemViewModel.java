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

package vn.fintechviet.mobileplatforms.application.management.ui.organizational.budget;

import android.databinding.ObservableField;

import vn.fintechviet.mobileplatforms.application.management.data.model.api.OBR;

/**
 * OBR = OrganizationalBudgetRelationship
 */
public class OBRItemViewModel {

    public final ObservableField<String> author;

    public final ObservableField<String> content;

    public final ObservableField<String> date;

    public final ObservableField<String> imageUrl;

    public final OBRItemViewModelListener mListener;

    public final ObservableField<String> title;

    private final OBR mBlog;

    public OBRItemViewModel(OBR blog, OBRItemViewModelListener listener) {
        this.mBlog = blog;
        this.mListener = listener;
        imageUrl = new ObservableField<>("mBlog.getCoverImgUrl()");
        title = new ObservableField<>(mBlog.getOrganizationalBudgetRelationshipName());
        author = new ObservableField<>(mBlog.getOrganizationalBudgetRelationshipAddress());
        date = new ObservableField<>(mBlog.getOrganizationalBudgetRelationshipCode());
        content = new ObservableField<>(mBlog.getOrganizationalBudgetRelationshipFax());
    }

    public void onItemClick() {
        mListener.onItemClick(mBlog.getUuid());
    }

    public interface OBRItemViewModelListener {

        void onItemClick(String blogUrl);
    }
}
