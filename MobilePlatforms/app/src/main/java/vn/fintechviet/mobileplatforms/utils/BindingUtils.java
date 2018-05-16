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
package vn.fintechviet.mobileplatforms.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import vn.fintechviet.mobileplatforms.data.model.api.OBR;
import vn.fintechviet.mobileplatforms.data.model.others.QuestionCardData;
import vn.fintechviet.mobileplatforms.ui.main.MainViewModel;
import vn.fintechviet.mobileplatforms.ui.organizational.budget.adapter.OBRAdapter;

import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

/**
 * Created by long_tran on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addOBRItems(RecyclerView recyclerView, List<OBR> obrs) {
        OBRAdapter adapter = (OBRAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(obrs);
        }
    }

//    @BindingAdapter({"adapter"})
//    public static void addOpenSourceItems(RecyclerView recyclerView, List<OpenSourceItemViewModel> openSourceItems) {
//        OpenSourceAdapter adapter = (OpenSourceAdapter) recyclerView.getAdapter();
//        if (adapter != null) {
//            adapter.clearItems();
//            adapter.addItems(openSourceItems);
//        }
//    }

    @BindingAdapter({"adapter", "action"})
    public static void addQuestionItems(SwipePlaceHolderView mCardsContainerView, List<QuestionCardData> mQuestionList, int mAction) {
        if (mAction == MainViewModel.ACTION_ADD_ALL) {
            if (mQuestionList != null) {
                mCardsContainerView.removeAllViews();
                for (QuestionCardData question : mQuestionList) {
                    if (question != null && question.options != null && question.options.size() == 3) {
//                        mCardsContainerView.addView(new QuestionCard(question));
                    }
                }
                ViewAnimationUtils.scaleAnimateView(mCardsContainerView);
            }
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }
}