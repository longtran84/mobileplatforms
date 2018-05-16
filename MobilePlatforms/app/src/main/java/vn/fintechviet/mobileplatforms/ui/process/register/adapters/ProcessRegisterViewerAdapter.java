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
package vn.fintechviet.mobileplatforms.ui.process.register.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.LookupRegister;
import vn.fintechviet.mobileplatforms.data.model.api.ProcessRegister;
import vn.fintechviet.mobileplatforms.utils.ProgressViewHolder;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnItemClickListener;

/**
 * Created by longtran on 17/01/2017.
 */

public class ProcessRegisterViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProcessRegister> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /**
     * @param context
     * @param recyclerViewOnItemClickListener
     */
    public ProcessRegisterViewerAdapter(Context context,
                                        RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.itemList = new ArrayList<>();
        this.context = context;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    /**
     * @param objects
     */
    public void setData(List<ProcessRegister> objects) {
        this.itemList.clear();
        this.itemList.addAll(objects);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process_register_recycler_view, parent, false);
            ProcessRegisterViewerViewHolder viewHolder = new ProcessRegisterViewerViewHolder(view, recyclerViewOnItemClickListener);
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
        if (holder instanceof ProcessRegisterViewerViewHolder) {
            final ProcessRegisterViewerViewHolder conversationViewHolder
                    = (ProcessRegisterViewerViewHolder) holder;
            final ProcessRegister articlesItem = itemList.get(position);
            if (null != articlesItem) {
                conversationViewHolder.textViewOrderNumber.setText(String.valueOf(position));
                if (!StringUtils.isBlank(articlesItem.getRegistrationDate())) {
                    conversationViewHolder.textViewRegistrationDate.setText(articlesItem.getRegistrationDate().trim());
                } else {
                    conversationViewHolder.textViewRegistrationDate.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getFullName())) {
                    conversationViewHolder.textViewFullName.setText(articlesItem.getFullName().trim());
                } else {
                    conversationViewHolder.textViewFullName.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getUserName())) {
                    conversationViewHolder.textViewUserName.setText(articlesItem.getUserName().trim());
                } else {
                    conversationViewHolder.textViewUserName.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getOrganizationName())) {
                    conversationViewHolder.textViewOrganizationName.setText(articlesItem.getOrganizationName().trim());
                } else {
                    conversationViewHolder.textViewOrganizationName.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getStatus())) {
                    conversationViewHolder.textViewStatus.setText(articlesItem.getStatus().trim());
                } else {
                    conversationViewHolder.textViewStatus.setText("");
                }
                if (position % 2 == 0) {
                    conversationViewHolder.relativeLayoutItem.setBackgroundColor(context.getResources().getColor(R.color.black05));
                } else {
                    conversationViewHolder.relativeLayoutItem.setBackgroundColor(context.getResources().getColor(R.color.black25));
                }
                conversationViewHolder.checkBox.setChecked(false);
                conversationViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        articlesItem.setSelected(cb.isChecked());
                        itemList.get(position).setSelected(cb.isChecked());
                    }
                });

            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    /**
     *
     * @return
     */
    public List<ProcessRegister> getItemList() {
        return itemList;
    }
}
