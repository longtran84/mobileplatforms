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
package vn.fintechviet.mobileplatforms.ui.messages.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.Messages;
import vn.fintechviet.mobileplatforms.utils.JDateFormat;
import vn.fintechviet.mobileplatforms.utils.ProgressViewHolder;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnItemClickListener;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnObjectClickListener;

/**
 * Created by longtran on 17/01/2017.
 */

public class MessagesViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Messages> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnObjectClickListener recyclerViewOnObjectClickListener;

    /**
     * @param context
     * @param recyclerViewOnObjectClickListener
     */
    public MessagesViewerAdapter(Context context,
                                 RecyclerViewOnObjectClickListener recyclerViewOnObjectClickListener) {
        this.itemList = new ArrayList<>();
        this.context = context;
        this.recyclerViewOnObjectClickListener = recyclerViewOnObjectClickListener;
    }

    /**
     * @param objects
     */
    public void setData(List<Messages> objects) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messages_recycler_view, parent, false);
            MessagesViewerViewHolder viewHolder = new MessagesViewerViewHolder(view);
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
        if (holder instanceof MessagesViewerViewHolder) {
            final MessagesViewerViewHolder conversationViewHolder
                    = (MessagesViewerViewHolder) holder;
            final Messages articlesItem = itemList.get(position);
            if (null != articlesItem) {
                if (!StringUtils.isBlank(articlesItem.getTitle())) {
                    conversationViewHolder.textViewTitle.setText(articlesItem.getTitle().trim());
                } else {
                    conversationViewHolder.textViewTitle.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getApproveDate())) {
                    conversationViewHolder.textViewPublishData.setText(JDateFormat.formatDate(articlesItem.getApproveDate().trim(), "dd/MM/yyyy"));
                } else {
                    conversationViewHolder.textViewPublishData.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getIcon())) {
                    byte[] imageByteArray = Base64.decode(articlesItem.getIcon(), Base64.DEFAULT);
                    Glide.with(context)
                            .load(imageByteArray)
                            .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                                    new RoundedCornersTransformation(360, 0, RoundedCornersTransformation.CornerType.ALL))))
                            .into(conversationViewHolder.itemImage);
                }
                conversationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewOnObjectClickListener.onClick(v, position, articlesItem);
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
}
