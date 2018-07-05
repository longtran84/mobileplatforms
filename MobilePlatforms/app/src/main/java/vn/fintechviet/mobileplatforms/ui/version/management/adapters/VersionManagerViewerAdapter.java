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
package vn.fintechviet.mobileplatforms.ui.version.management.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
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
import vn.fintechviet.mobileplatforms.BuildConfig;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.VersionManager;
import vn.fintechviet.mobileplatforms.utils.JDateFormat;
import vn.fintechviet.mobileplatforms.utils.ProgressViewHolder;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnObjectClickListener;

/**
 * Created by longtran on 17/01/2017.
 */

public class VersionManagerViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VersionManager> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnObjectClickListener recyclerViewOnObjectClickListener;

    /**
     * @param context
     * @param recyclerViewOnObjectClickListener
     */
    public VersionManagerViewerAdapter(Context context,
                                       RecyclerViewOnObjectClickListener recyclerViewOnObjectClickListener) {
        this.itemList = new ArrayList<>();
        this.context = context;
        this.recyclerViewOnObjectClickListener = recyclerViewOnObjectClickListener;
    }

    /**
     * @param objects
     */
    public void setData(List<VersionManager> objects) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_version_manager_recycler_view, parent, false);
            VersionManagerViewerViewHolder viewHolder = new VersionManagerViewerViewHolder(view);
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
        if (holder instanceof VersionManagerViewerViewHolder) {
            final VersionManagerViewerViewHolder conversationViewHolder
                    = (VersionManagerViewerViewHolder) holder;
            final VersionManager articlesItem = itemList.get(position);
            if (null != articlesItem) {
                if (!StringUtils.isBlank(articlesItem.getVersion())) {
                    conversationViewHolder.textViewVersion.setText(articlesItem.getVersion().trim());
                    if(articlesItem.getVersion().trim().equalsIgnoreCase(BuildConfig.VERSION_NAME)){
                        conversationViewHolder.itemImage.setColorFilter(0xFF32c24d, PorterDuff.Mode.SRC_ATOP);
                    }else{
                        conversationViewHolder.itemImage.setColorFilter(0x80000000, PorterDuff.Mode.SRC_ATOP);
                    }
                } else {
                    conversationViewHolder.textViewVersion.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getDescription())) {
                    conversationViewHolder.textViewDescription.setText(articlesItem.getDescription());
                } else {
                    conversationViewHolder.textViewDescription.setText("");
                }
                if (!StringUtils.isBlank(articlesItem.getCreateDate())) {
                    conversationViewHolder.textViewPublishDate.setText(JDateFormat.formatDate(articlesItem.getCreateDate().trim(), "dd/MM/yyyy"));
                } else {
                    conversationViewHolder.textViewPublishDate.setText("");
                }
                if (articlesItem.getForceUpdate() > 0) {
                    conversationViewHolder.imageViewForceUpdate.setVisibility(View.VISIBLE);
                } else {
                    conversationViewHolder.imageViewForceUpdate.setVisibility(View.GONE);
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
