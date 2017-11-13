package com.dynasoft.weathertoday.activity;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dynasoft.weathertoday.R;
import com.dynasoft.weathertoday.adapters.CityNameListRecyclerAdaptor;
import com.dynasoft.weathertoday.database.AppSQLiteHelper;
import com.dynasoft.weathertoday.model.City;
import com.dynasoft.weathertoday.model.OpenWeatherMap;
import com.dynasoft.weathertoday.util.ObservableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sundeep on 11/6/17.
 */

public class MasterFragment extends Fragment implements Observer {

    public static String TAG = MasterFragment.class.getSimpleName();

    private TextView mNoCitySerchedTextView;
    private MainActivity mMainActivity;
    private MutableLiveData<ArrayList<City>> mCityList;
    private RecyclerView mCityRecyclerView;
    private CityNameListRecyclerAdaptor mWeatherRecyclerAdaptor;
    private static OpenWeatherMap mOpenWeatherMap;
    private Context mContext;
    // Store instance variables
    private String title;
    private int page;
    private AppSQLiteHelper mAppSQLiteHelper;
    private List<City> cityList = new ArrayList<>();

    public static MasterFragment newInstance(int page, String title) {

        MasterFragment fragment=new MasterFragment();
        Bundle args = new Bundle();
        args.putInt("Master", page);
        args.putString("MasterTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObservableObject.getInstance().addObserver(this);
        Bundle bundle = getArguments();
        page = bundle.getInt("Master");
        title = bundle.getString("MasterTitle");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mMainActivity = (MainActivity) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.master_fragment_layout, container, false);
        TextView mTxtViewNoCity = (TextView) view.findViewById(R.id.txtNoCity);
        mTxtViewNoCity.setText(page +"  " +title);

        //Creating Database instance
        mAppSQLiteHelper = new AppSQLiteHelper(mMainActivity.getApplicationContext());
        // get all city from database
        cityList = mAppSQLiteHelper.getAllCities();
        int cityListSize = cityList.size() - 1;

        mCityRecyclerView = (RecyclerView)  view.findViewById(R.id.city_list_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mCityRecyclerView.setLayoutManager(layoutManager);
        mCityRecyclerView.setHasFixedSize(true);
        if(cityList.size() > 0) {
            mWeatherRecyclerAdaptor = new CityNameListRecyclerAdaptor(getContext(), cityList, mMainActivity);
            mCityRecyclerView.setAdapter(mWeatherRecyclerAdaptor);

        }

        return view;
    }

    @Override
    public void update(Observable observable, Object data) {
        mOpenWeatherMap = (OpenWeatherMap) data;

    }

}
