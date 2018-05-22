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
package vn.fintechviet.mobileplatforms.application.management.ui.messages.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.fintechviet.mobileplatforms.application.management.R;

/**
 * Created by longtran on 17/01/2017.
 */

public class MessagesViewerViewHolder extends RecyclerView.ViewHolder{

    public View itemView;

    public ImageView itemImage;

    public TextView textViewTitle;

    public TextView textViewDescription;

    public TextView textViewPublishData;

    /**
     *
     * @param itemView
     */
    public MessagesViewerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.itemImage = (ImageView) itemView.findViewById(R.id.image_view_app_icon_id);
        this.textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title_id);
        this.textViewDescription = (TextView) itemView.findViewById(R.id.text_view_description_id);
        this.textViewPublishData = (TextView) itemView.findViewById(R.id.text_view_publish_date_id);
    }
}
