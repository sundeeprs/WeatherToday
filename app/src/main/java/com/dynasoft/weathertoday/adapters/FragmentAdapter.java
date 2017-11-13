package com.dynasoft.weathertoday.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dynasoft.weathertoday.activity.DetailWeatherFragment;
import com.dynasoft.weathertoday.activity.MapsFragment;
import com.dynasoft.weathertoday.activity.MasterFragment;
import com.dynasoft.weathertoday.database.Constants;
import com.dynasoft.weathertoday.model.OpenWeatherMap;


/**
 * Created by sundeep on 11/12/17.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

        private static int NUM_ITEMS = 3;
        private OpenWeatherMap mOpenWeatherMap;
        private String title = "Tab";

        public FragmentAdapter(FragmentManager fragmentManager, OpenWeatherMap openWeatherMap) {
            super(fragmentManager);
            mOpenWeatherMap = openWeatherMap;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment

                    MasterFragment masterFragment = MasterFragment.newInstance(0, title);
                    bundle.putInt("Master", 0);
                    bundle.putString("MasterTitle", title);
                    masterFragment.setArguments(bundle);
                    return masterFragment;

                case 1: // Fragment # 0 - This will show FirstFragment different title

                    DetailWeatherFragment detailWeatherFragment = DetailWeatherFragment.newInstance(1, title);
                    bundle.putInt("Master", 1);
                    bundle.putString("MasterTitle", title);
                    detailWeatherFragment.setArguments(bundle);
                    return detailWeatherFragment;
                case 2: // Fragment # 1 - This will show SecondFragment

                    MapsFragment mapsFragment = MapsFragment.newInstance(2, title);
                    bundle.putInt("Master", 2);
                    bundle.putString("MasterTitle", title);
                    mapsFragment.setArguments(bundle);
                    return mapsFragment;
                default:
                    return null;
            }
        }



        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return title + position;
        }



}
