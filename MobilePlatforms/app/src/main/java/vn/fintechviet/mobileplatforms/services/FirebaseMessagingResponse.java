package vn.fintechviet.mobileplatforms.services;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by longtran on 10/04/2018.
 */

public class FirebaseMessagingResponse implements Parcelable {

    private String id;
    private String type;
    private String message;
    private String icon;

    public FirebaseMessagingResponse() {

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FirebaseMessagingResponse createFromParcel(Parcel in) {
            return new FirebaseMessagingResponse(in);
        }

        public FirebaseMessagingResponse[] newArray(int size) {
            return new FirebaseMessagingResponse[size];
        }
    };

    // Parcelling part
    public FirebaseMessagingResponse(Parcel in) {
        this.id = in.readString();
        this.type = in.readString();
        this.message = in.readString();
        this.icon = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.message);
        dest.writeString(this.icon);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
