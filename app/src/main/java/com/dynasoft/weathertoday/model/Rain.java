package com.dynasoft.weathertoday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by sundeep on 10/11/17.
 */

public class Rain extends SugarRecord<Rain> implements Parcelable {

    @SerializedName("3h")
    @Expose
    private String rainHours;
    public Rain() {}

    public Rain(String rainHours) {
        this.rainHours = rainHours;
    }
    public String getRainHours() {
        return rainHours;
    }

    public void setRainHours(String rainHours) {
        this.rainHours = rainHours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rainHours);
    }

    protected Rain(Parcel in) {
        this.rainHours = in.readString();
    }

    public static final Parcelable.Creator<Rain> CREATOR = new Parcelable.Creator<Rain>() {
        @Override
        public Rain createFromParcel(Parcel source) {
            return new Rain(source);
        }

        @Override
        public Rain[] newArray(int size) {
            return new Rain[size];
        }
    };
}
