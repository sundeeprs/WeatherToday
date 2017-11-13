package com.dynasoft.weathertoday.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sundeep on 10/16/17.
 */

public class City implements Parcelable {

    public int cityID;
    public String cityName;
    public String cityTemprature;


    public City(int cityID, String cityName, String cityTemprature) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.cityTemprature = cityTemprature;
    }

    public City(String cityName, String cityTemprature) {
        this.cityName = cityName;
        this.cityTemprature = cityTemprature;
    }
    public City() {

    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityTemprature() {
        return cityTemprature;
    }

    public void setCityTemprature(String cityTemprature) {
        this.cityTemprature = cityTemprature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cityID);
        dest.writeString(this.cityName);
        dest.writeString(this.cityTemprature);
    }

    protected City(Parcel in) {
        this.cityID = in.readInt();
        this.cityName = in.readString();
        this.cityTemprature = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
