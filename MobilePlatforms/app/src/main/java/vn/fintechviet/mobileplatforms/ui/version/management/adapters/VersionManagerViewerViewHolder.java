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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.fintechviet.mobileplatforms.R;

/**
 * Created by longtran on 17/01/2017.
 */

public class VersionManagerViewerViewHolder extends RecyclerView.ViewHolder {

    public View itemView;

    public ImageView itemImage;

    public TextView textViewVersion;

    public TextView textViewDescription;

    public ImageView imageViewForceUpdate;

    public TextView textViewPublishDate;

    /**
     * @param itemView
     */
    public VersionManagerViewerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.itemImage = (ImageView) itemView.findViewById(R.id.image_view_app_icon_id);
        this.textViewVersion = (TextView) itemView.findViewById(R.id.text_view_version_id);
        this.textViewDescription = (TextView) itemView.findViewById(R.id.text_view_description_id);
        this.imageViewForceUpdate = (ImageView) itemView.findViewById(R.id.image_view_force_update_id);
        this.textViewPublishDate = (TextView) itemView.findViewById(R.id.text_view_publish_date_id);
    }
}
