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
package vn.fintechviet.mobileplatforms.ui.account.balance.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.AccountBalance;
import vn.fintechviet.mobileplatforms.utils.ProgressViewHolder;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnItemClickListener;

/**
 * Created by longtran on 17/01/2017.
 */

public class AccountBalanceViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AccountBalance> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /**
     * @param context
     * @param recyclerViewOnItemClickListener
     */
    public AccountBalanceViewerAdapter(Context context,
                                       RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.itemList = new ArrayList<>();
        this.context = context;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    /**
     * @param objects
     */
    public void setData(List<AccountBalance> objects) {
        if (null != objects && objects.size() > 0) {
            this.itemList.clear();
            this.itemList.addAll(objects);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_balance_recycler_view, parent, false);
            AccountBalanceViewerViewHolder viewHolder = new AccountBalanceViewerViewHolder(view, recyclerViewOnItemClickListener);
            return viewHolder;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progress_bar, parent, false);
            ProgressViewHolder progressViewHolder = new ProgressViewHolder(view);
            return progressViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AccountBalanceViewerViewHolder) {
            final AccountBalanceViewerViewHolder conversationViewHolder
                    = (AccountBalanceViewerViewHolder) holder;
            final AccountBalance articlesItem = itemList.get(position);
            if (null != articlesItem) {
                if (!StringUtils.isBlank(articlesItem.getChangeDate())) {
                    conversationViewHolder.textViewDateTime.setText(articlesItem.getChangeDate().trim());
                } else {
                    conversationViewHolder.textViewDateTime.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getAfterMoney())) {
                    conversationViewHolder.textViewBalance.setText(articlesItem.getAfterMoney().trim());
                } else {
                    conversationViewHolder.textViewBalance.setText("");
                }
                if (position % 2 == 0) {
                    conversationViewHolder.relativeLayoutItem.setBackgroundColor(context.getResources().getColor(R.color.black05));
                } else {
                    conversationViewHolder.relativeLayoutItem.setBackgroundColor(context.getResources().getColor(R.color.black25));
                }
            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
