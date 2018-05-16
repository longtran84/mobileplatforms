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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.model.api.UserApplicationModules;
import vn.fintechviet.mobileplatforms.data.model.api.UserModules;
import vn.fintechviet.mobileplatforms.utils.RecyclerViewOnObjectClickListener;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private List<UserModules> dataList;
    private Context mContext;
    private RecyclerViewOnObjectClickListener<UserApplicationModules> recyclerViewOnObjectClickListener;

    public RecyclerViewOnObjectClickListener<UserApplicationModules> getRecyclerViewOnObjectClickListener() {
        return recyclerViewOnObjectClickListener;
    }

    public void setRecyclerViewOnObjectClickListener(RecyclerViewOnObjectClickListener<UserApplicationModules> recyclerViewOnObjectClickListener) {
        this.recyclerViewOnObjectClickListener = recyclerViewOnObjectClickListener;
    }

    public RecyclerViewDataAdapter(Context context, List<UserModules> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_application_recycler_view, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        final String sectionName = dataList.get(i).getName();
        List<UserApplicationModules> singleSectionItems = dataList.get(i).getListUserApplicationModules();
        itemRowHolder.itemTitle.setText(sectionName);
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);
        itemListDataAdapter.setRecyclerViewOnObjectClickListener(getRecyclerViewOnObjectClickListener());
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new GridLayoutManager(mContext, 3));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "click event on more, " + sectionName, Toast.LENGTH_SHORT).show();
            }
        });
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    /**
     *
     */
    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;

        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnMore = (Button) view.findViewById(R.id.btnMore);
        }
    }
}
