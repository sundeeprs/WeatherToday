package com.dynasoft.weathertoday.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynasoft.weathertoday.R;
import com.dynasoft.weathertoday.activity.MainActivity;
import com.dynasoft.weathertoday.databinding.RowListLayoutBinding;
import com.dynasoft.weathertoday.model.City;
import com.dynasoft.weathertoday.model.OpenWeatherMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sundeep on 11/6/17.
 */

public class CityNameListRecyclerAdaptor extends RecyclerView.Adapter<CityNameListRecyclerAdaptor.MyViewHolder> {

    public static MainActivity mMainActivity;
    private Context mContext;
    private static OpenWeatherMap mOpenWeatherMap;
    private List<City> mCity = new ArrayList<>();

    public CityNameListRecyclerAdaptor(Context context, List<City> city, MainActivity mainActivity) {
        this.mContext = context;
        this.mCity = city;
        this.mMainActivity = mainActivity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowListLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_list_layout, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (mCity.size() > 0) {
            holder.cityName.setText("City : "+mCity.get(position).getCityName());
            holder.cityTemprature.setText(" Temp : " + mCity.get(position).getCityTemprature());
        } else {
            holder.cityName.setText("Enter city name in searcg bar");
        }

    }


    @Override
    public int getItemCount() {
        return mCity.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public RowListLayoutBinding cityNameListBind;

        public TextView cityName;
        public TextView cityTemprature;
        public CardView cityListCardView;

        public MyViewHolder(ViewGroup parent) {
            super(parent);
        }

        public MyViewHolder(RowListLayoutBinding binding) {
            super(binding.cityListCardview);

            cityListCardView = (CardView) binding.cityListCardview;
            cityName = (TextView) binding.cityName;
            cityTemprature = (TextView) binding.cityTemprature;

        }

    }


}
