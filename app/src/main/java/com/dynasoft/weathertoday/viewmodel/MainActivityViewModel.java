package com.dynasoft.weathertoday.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.dynasoft.weathertoday.R;
import com.dynasoft.weathertoday.activity.MasterFragment;
import com.dynasoft.weathertoday.activity.MainActivity;
import com.dynasoft.weathertoday.database.AppSQLiteHelper;
import com.dynasoft.weathertoday.database.Constants;
import com.dynasoft.weathertoday.model.City;
import com.dynasoft.weathertoday.model.Clouds;
import com.dynasoft.weathertoday.model.Coord;
import com.dynasoft.weathertoday.model.Main;
import com.dynasoft.weathertoday.model.OpenWeatherMap;
import com.dynasoft.weathertoday.model.Rain;
import com.dynasoft.weathertoday.model.Sys;
import com.dynasoft.weathertoday.model.Weather;
import com.dynasoft.weathertoday.model.Wind;
import com.dynasoft.weathertoday.service.WeatherIntentService;
import com.dynasoft.weathertoday.util.ObservableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by sundeep on 11/3/17.
 */

public class MainActivityViewModel extends ViewModel {

    public static String TAG = MainActivityViewModel.class.getSimpleName();

    private static MainActivity mMainActivity;
    public WeatherReceiver weatherReceiver = null;
    public static FrameLayout mContainer;
    private Context mContext;
    private static AppSQLiteHelper mAppSQLiteHelper;

    ViewPager mViewPager;

    public MainActivityViewModel () { }

    public MainActivityViewModel(Context context, Bundle savedViewModelState, MainActivity mainActivity) {

        this.mContext = context;
        this.mMainActivity = (MainActivity) mainActivity;
        //Creating Database instance
        mAppSQLiteHelper = new AppSQLiteHelper(mMainActivity.getApplicationContext());

        if(weatherReceiver == null) {
            weatherReceiver = new WeatherReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.ACTION_RESP);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            mMainActivity.registerReceiver(weatherReceiver, intentFilter);
        }

    }

    @BindingAdapter("setContainer")
    public static void setContainer(final FrameLayout frameLayout, boolean status) {
        mContainer = frameLayout;

    }

    public void callIntentService(String query) {
        Intent mWeatherIntent = new Intent(mMainActivity.getApplicationContext(), WeatherIntentService.class);
        mWeatherIntent.putExtra(Constants.EXTENDED_DATA_STATUS, query);
        mMainActivity.startService(mWeatherIntent);
    }


    //BradCast Receiver class to receive the weather info from backend
    public static class WeatherReceiver extends BroadcastReceiver {

        private OpenWeatherMap mOpenWeatherMap;

        @Override
        public void onReceive(Context context, Intent intent) {
            //callFragment();

            Bundle bundle = intent.getParcelableExtra(Constants.EXTENDED_DATA_STATUS);
            mOpenWeatherMap = bundle.getParcelable(Constants.EXTENDED_DATA_STATUS);
            saveWeatherDataToDataBase(mOpenWeatherMap);
            ObservableObject.getInstance().updateValue(mOpenWeatherMap);

            //storing weather information

            Log.d(TAG," Sundeep Response receive ..........");


        }
    }




    /*
    * This method Store weather information to sqlite database using SugarORM
    * @param openWeatherMap
    */
    public static void saveWeatherDataToDataBase(OpenWeatherMap openWeatherMap) {

        City city = new City();
        city.setCityName(openWeatherMap.getName());
        city.setCityTemprature(openWeatherMap.getMain().getTemp().toString());

        //to clear all data from table
        mAppSQLiteHelper.deleteAll();

        // add City to database;
        mAppSQLiteHelper.insertCity(city);

    }

}
