package com.dynasoft.weathertoday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by sundeep on 11/06/17.
 */

public class Clouds extends SugarRecord<Clouds> implements Parcelable {


    private int all;

    /**
     * No args constructor for use in serialization
     *
     */
    public Clouds() {
    }

    /**
     *
     * @param all
     */
    public Clouds(int all) {
        super();
        this.all = all;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.all);
    }

    protected Clouds(Parcel in) {
        this.all = (int) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Clouds> CREATOR = new Parcelable.Creator<Clouds>() {
        @Override
        public Clouds createFromParcel(Parcel source) {
            return new Clouds(source);
        }

        @Override
        public Clouds[] newArray(int size) {
            return new Clouds[size];
        }
    };
}
