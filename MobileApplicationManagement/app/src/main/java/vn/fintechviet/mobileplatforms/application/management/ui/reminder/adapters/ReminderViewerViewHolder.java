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
package vn.fintechviet.mobileplatforms.application.management.ui.reminder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import vn.fintechviet.mobileplatforms.application.management.R;
import vn.fintechviet.mobileplatforms.application.management.utils.RecyclerViewOnItemClickListener;

/**
 * Created by longtran on 17/01/2017.
 */

public class ReminderViewerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public TextView textViewCode;

    public TextView textViewName;

    public TextView textViewNoRegister;

    public ReminderViewerViewHolder(View itemView,
                                    RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(itemView);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        textViewCode = (TextView) itemView.findViewById(R.id.text_view_code_id);
        textViewName = (TextView) itemView.findViewById(R.id.text_view_name_id);
        textViewNoRegister = (TextView) itemView.findViewById(R.id.text_view_no_register_id);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
