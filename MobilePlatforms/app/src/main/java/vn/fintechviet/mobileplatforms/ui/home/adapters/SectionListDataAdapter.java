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
package vn.fintechviet.mobileplatforms.ui.home.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.UserApplicationModules;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnItemClickListener;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnObjectClickListener;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private List<UserApplicationModules> itemsList;
    private Context mContext;
    private RecyclerViewOnObjectClickListener<UserApplicationModules> recyclerViewOnObjectClickListener;

    public RecyclerViewOnObjectClickListener<UserApplicationModules> getRecyclerViewOnObjectClickListener() {
        return recyclerViewOnObjectClickListener;
    }

    public void setRecyclerViewOnObjectClickListener(RecyclerViewOnObjectClickListener<UserApplicationModules> recyclerViewOnObjectClickListener) {
        this.recyclerViewOnObjectClickListener = recyclerViewOnObjectClickListener;
    }

    public SectionListDataAdapter(Context context, List<UserApplicationModules> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_application_recycler_view_item_recycler_view, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        UserApplicationModules userApplicationModules = itemsList.get(i);
        holder.tvTitle.setText(userApplicationModules.getName());
        if (!StringUtils.isBlank(userApplicationModules.getIcon())) {
            byte[] imageByteArray = Base64.decode(userApplicationModules.getIcon(), Base64.DEFAULT);
            Glide.with(mContext)
                    .load(imageByteArray)
                    .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                    .into(holder.itemImage);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != getRecyclerViewOnObjectClickListener()) {
                    getRecyclerViewOnObjectClickListener().onClick(v, i, userApplicationModules);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    /**
     *
     */
    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected View view;
        protected TextView tvTitle;
        protected ImageView itemImage;

        /**
         * @param view
         */
        public SingleItemRowHolder(View view) {
            super(view);
            this.view = view;
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
        }

    }

}