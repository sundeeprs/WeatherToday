package com.dynasoft.weathertoday.activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by sundeep on 11/8/17.
 */

public class MyApplication extends Application {

    private static Context mConext;

    @Override
    public void onCreate() {
        super.onCreate();

        MyApplication.mConext = getApplicationContext();
    }
}
