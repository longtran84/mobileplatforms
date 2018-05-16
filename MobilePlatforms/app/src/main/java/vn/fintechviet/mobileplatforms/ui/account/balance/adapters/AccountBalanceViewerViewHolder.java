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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnItemClickListener;

/**
 * Created by longtran on 17/01/2017.
 */

public class AccountBalanceViewerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public RelativeLayout relativeLayoutItem;

    public TextView textViewOrder;

    public TextView textViewDateTime;

    public TextView textViewBalance;

    public AccountBalanceViewerViewHolder(View itemView,
                                          RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(itemView);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        relativeLayoutItem = (RelativeLayout) itemView.findViewById(R.id.item_account_balance_recycler_view_id);
        textViewOrder = (TextView) itemView.findViewById(R.id.text_view_order_id);
        textViewDateTime = (TextView) itemView.findViewById(R.id.text_view_date_time_id);
        textViewBalance = (TextView) itemView.findViewById(R.id.text_view_balance_id);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
