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
package vn.fintechviet.mobileplatforms.utils.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vn.fintechviet.mobileplatforms.data.model.others.ISpinnerData;

public class SpinnerCustomAdapter<T extends ISpinnerData> extends ArrayAdapter<T> {
    /**
     * Our MatchTypeEvent
     */
    private List<T> values;
    /**
     * Layout
     */
    private int dropDownResource;

    /**
     * The super constructor we need
     *
     * @param context
     * @param textViewResourceId
     */
    public SpinnerCustomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.values = new ArrayList<>();
    }

    /**
     * The super constructor we need
     *
     * @param context
     * @param textViewResourceId
     * @param objects
     */
    public SpinnerCustomAdapter(Context context, int textViewResourceId, List<T> objects) {
        super(context, textViewResourceId, objects);
        this.values = objects;
    }

    /**
     * @param objects
     */
    public void setData(List<T> objects) {
        this.values.clear();
        this.values.addAll(objects);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values != null ? values.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return values != null ? values.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        T item = getItem(position);
        return item.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, android.R.layout.simple_spinner_item);
    }


    @Override
    public void setDropDownViewResource(int resource) {
        this.dropDownResource = resource;
    }

    /**
     * We had to override the creation of the composant to display your own label
     *
     * @param position
     * @param convertView
     * @param parent
     * @param layout
     * @return
     */
    protected View getCustomView(int position, View convertView, ViewGroup parent, int layout) {
//        View view;
//        TextView label;
//
//        if (convertView == null) {
//            LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = li.inflate(layout, parent, false);
//        } else {
//            view = convertView;
//        }
//
//        label = (TextView) view;
//        label.setText(getItem(position).getLabel());
//
//        return label;

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            viewHolder.label = (TextView) convertView.findViewById(android.R.id.text1);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.label.setText(getItem(position).getLabel().trim());
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, dropDownResource);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView label;
    }
}
