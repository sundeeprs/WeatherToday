package com.dynasoft.weathertoday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundeep on 10/11/17.
 */

public class WeatherList extends SugarRecord<WeatherList> implements Parcelable {

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;

    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherList() {
    }

    /**
     * @param base
     * @param weather
     */
    public WeatherList(List<Weather> weather, String base) {
        super();

        this.weather = weather;
        this.base = base;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.weather);
        dest.writeString(this.base);
    }

    protected WeatherList(Parcel in) {
        this.weather = new ArrayList<Weather>();
        in.readList(this.weather, Weather.class.getClassLoader());
        this.base = in.readString();
    }

    public static final Parcelable.Creator<WeatherList> CREATOR = new Parcelable.Creator<WeatherList>() {
        @Override
        public WeatherList createFromParcel(Parcel source) {
            return new WeatherList(source);
        }

        @Override
        public WeatherList[] newArray(int size) {
            return new WeatherList[size];
        }
    };
}
