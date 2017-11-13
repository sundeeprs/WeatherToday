package com.dynasoft.weathertoday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by sundeep on 10/11/17.
 */

public class Sys extends SugarRecord<Sys> implements Parcelable {
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;


    /**
     * No args constructor for use in serialization
     *
     */
    public Sys() {
    }

    public Sys(int type, Double message, String country, Integer sunrise, Integer sunset) {
        this.type = type;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeValue(this.message);
        dest.writeString(this.country);
        dest.writeValue(this.sunrise);
        dest.writeValue(this.sunset);
    }

    protected Sys(Parcel in) {
        this.type = in.readInt();
        this.message = (Double) in.readValue(Double.class.getClassLoader());
        this.country = in.readString();
        this.sunrise = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sunset = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Sys> CREATOR = new Parcelable.Creator<Sys>() {
        @Override
        public Sys createFromParcel(Parcel source) {
            return new Sys(source);
        }

        @Override
        public Sys[] newArray(int size) {
            return new Sys[size];
        }
    };
}
