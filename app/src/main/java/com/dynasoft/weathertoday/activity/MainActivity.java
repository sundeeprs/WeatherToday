package com.dynasoft.weathertoday.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dynasoft.weathertoday.R;
import com.dynasoft.weathertoday.adapters.FragmentAdapter;
import com.dynasoft.weathertoday.database.Constants;
import com.dynasoft.weathertoday.databinding.MainActivityLayoutBinding;
import com.dynasoft.weathertoday.model.City;
import com.dynasoft.weathertoday.model.OpenWeatherMap;
import com.dynasoft.weathertoday.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by sundeep on 11/3/17.
 */

public class MainActivity extends AppCompatActivity implements Observer {

    //TAG
    private static final String TAG = MainActivity.class.getSimpleName();
    public Toolbar mToolbar;

    //Member variables
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<City> mCityList = new ArrayList<>();
    private Intent settingInent;
    private OpenWeatherMap mOpenWeatherMap;
    private FragmentAdapter adapterViewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MainActivityLayoutBinding mainActivityBinding = DataBindingUtil.setContentView(this,
                R.layout.main_activity_layout);

        //mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel = new MainActivityViewModel(getBaseContext(), savedInstanceState, this);
        mainActivityBinding.setLocation(mainActivityViewModel);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        //mainActivityViewModel.callIntentService("Frisco");

        adapterViewPager = new FragmentAdapter(getSupportFragmentManager(), mOpenWeatherMap);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setAdapter(adapterViewPager);

        settingInent = new Intent(this, SettingsActivity.class);
        mToolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        startActivity(settingInent);
                        return true;
                    }
                });


    }

    //Call Master Fragment
    private void setMasterFragment(OpenWeatherMap openWeatherMap) {


        adapterViewPager = new FragmentAdapter(getSupportFragmentManager(), mOpenWeatherMap);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setAdapter(adapterViewPager);


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(mainActivityViewModel != null) {
                    //call to viewModel method to start a IntentService
                    mainActivityViewModel.callIntentService(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(mainActivityViewModel.weatherReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.ACTION_RESP);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            registerReceiver(mainActivityViewModel.weatherReceiver, intentFilter);
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        if(mainActivityViewModel.weatherReceiver != null) {
            unregisterReceiver(mainActivityViewModel.weatherReceiver);
            mainActivityViewModel.weatherReceiver = null;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mainActivityViewModel.weatherReceiver != null) {
            unregisterReceiver(mainActivityViewModel.weatherReceiver);
            mainActivityViewModel.weatherReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {

        try{
            super.onBackPressed();
        }catch (IllegalStateException e){
            // can output some information here
            finish();
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        mOpenWeatherMap = (OpenWeatherMap) data;
        if(mOpenWeatherMap != null) {


            //Toast.makeText(this,"activity observer "+ mOpenWeatherMap.getName().toString(), Toast.LENGTH_SHORT).show();
            //setMasterFragment(mOpenWeatherMap);
        }


    }


}
