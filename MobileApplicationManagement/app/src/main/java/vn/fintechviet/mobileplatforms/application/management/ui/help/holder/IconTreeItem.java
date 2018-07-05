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

package vn.fintechviet.mobileplatforms.application.management.ui.help.holder;

import android.os.Parcel;
import android.os.Parcelable;

public class IconTreeItem implements Parcelable {

    private int icon;
    private String text;
    private String body;
    private boolean leaf = false;

    public static final Creator CREATOR = new Creator() {
        public IconTreeItem createFromParcel(Parcel in) {
            return new IconTreeItem(in);
        }

        public IconTreeItem[] newArray(int size) {
            return new IconTreeItem[size];
        }
    };


    public IconTreeItem() {

    }

    // Parcelling part
    public IconTreeItem(Parcel in) {
        this.icon = in.readInt();
        this.text = in.readString();
        this.body = in.readString();
        this.leaf = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.icon);
        dest.writeString(this.text);
        dest.writeString(this.body);
        dest.writeInt(this.leaf ? 1 : 0);
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}