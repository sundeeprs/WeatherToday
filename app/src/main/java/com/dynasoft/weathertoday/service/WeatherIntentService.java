package com.dynasoft.weathertoday.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dynasoft.weathertoday.client.RetrofitAPIClient;
import com.dynasoft.weathertoday.database.Constants;
import com.dynasoft.weathertoday.interfaces.ApiInterface;
import com.dynasoft.weathertoday.model.OpenWeatherMap;
import com.dynasoft.weathertoday.util.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sundeep on 10/24/17.
 */

public class WeatherIntentService extends IntentService {

    public static String TAG = WeatherIntentService.class.getSimpleName();

    private OpenWeatherMap mOpenWeatherMap = new OpenWeatherMap();

    public WeatherIntentService() {
        super("WeatherIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String datString = intent.getStringExtra(Constants.EXTENDED_DATA_STATUS);
        Log.d(TAG, " Sundeep string from activity are ..."+ datString+ " JHD" );
        getWeatherByCity(datString);

    }


    private void getWeatherByCity(String query){

        ApiInterface apiInterface = RetrofitAPIClient.getClient().create(ApiInterface.class);
        Call call = null;

        // Check weather query is by zipcode or by city name
        if(Helper.containsDigit(query)) {
            call = apiInterface.doGetWeatherByZipCode(query, Constants.API_KEY);
        } else {
            call = apiInterface.doGetWeatherByCityName(query, Constants.API_KEY);
        }


        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {
                if (response.code() == 200) {
                    mOpenWeatherMap = response.body();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.EXTENDED_DATA_STATUS, mOpenWeatherMap);

                    //send Broadcast with weather data in OpenWeatherMap object
                    Intent localIntent = new Intent();
                    localIntent.setAction(Constants.ACTION_RESP);
                    localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    localIntent.putExtra(Constants.EXTENDED_DATA_STATUS,bundle);
                    sendBroadcast(localIntent);

                }else {
                    Log.d("TAG", call.toString() + " Server response failed::: ");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("TAG", call.toString() + " Server response failed::: ");
                call.cancel();

            }
        });
    }
}
