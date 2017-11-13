package com.dynasoft.weathertoday.interfaces;

import com.dynasoft.weathertoday.model.OpenWeatherMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sundeep on 10/12/17.
 */

/*
 * APIInterface to decalre OpenweatherMap URI to get waether.
 */
public interface ApiInterface {

    @GET("/data/2.5/weather")
    Call<OpenWeatherMap> doGetWeatherReport(@Query(value = "lat") double lat, @Query(value = "lon") double lon, @Query(value = "apikey") String API_KEY);

    @GET("/data/2.5/weather")
    Call<OpenWeatherMap> doGetWeatherByCityName(@Query(value = "q") String q, @Query(value = "apikey") String API_KEY);

    @GET("/data/2.5/weather")
    Call<OpenWeatherMap> doGetWeatherByZipCode(@Query(value = "zip") String zip, @Query(value = "apikey") String API_KEY);

}
