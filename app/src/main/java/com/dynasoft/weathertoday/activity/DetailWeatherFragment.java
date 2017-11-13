package com.dynasoft.weathertoday.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynasoft.weathertoday.R;
import com.dynasoft.weathertoday.database.Constants;
import com.dynasoft.weathertoday.model.City;
import com.dynasoft.weathertoday.model.OpenWeatherMap;
import com.dynasoft.weathertoday.model.Weather;
import com.dynasoft.weathertoday.util.Helper;
import com.dynasoft.weathertoday.util.ObservableObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sundeep on 11/6/17.
 */

public class DetailWeatherFragment extends Fragment implements Observer {

    public String TAG = DetailWeatherFragment.class.getSimpleName();

    @BindView(R.id.txtCity)
    public TextView mTxtCity;
    @BindView(R.id.txtDescription)
    public TextView mTxtDescription;
    @BindView(R.id.txtCelsius)
    public TextView mTxtCelsius;
    @BindView(R.id.txtLastUpdate)
    public TextView mTxtLastUpdate;
    @BindView(R.id.txtHumidity)
    public TextView mTxtHumidity;
    @BindView(R.id.txtTime)
    public TextView mTxtTime;
    @BindView(R.id.imageView)
    public ImageView mImageView;

    @BindView(R.id.txtNoCity)
    public TextView mTxtViewNoCity;

    private OpenWeatherMap mOpenWeatherMap;
    private String title;
    private int page;

    public static DetailWeatherFragment newInstance(int page, String title) {

        DetailWeatherFragment fragment=new DetailWeatherFragment();
        Bundle args = new Bundle();
         args.putInt("Master", page);
        args.putString("MasterTitle", title);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObservableObject.getInstance().addObserver(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment_layout, container, false);
        TextView textView = (TextView) view.findViewById(R.id.txtNoCity);
        textView.setText(page +"  " +title);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void update(Observable observable, Object data) {
        mOpenWeatherMap = (OpenWeatherMap) data;

        if (mOpenWeatherMap != null) {
            setWeatherInfoToUI(mOpenWeatherMap);
        }
    }

    public void setWeatherInfoToUI(OpenWeatherMap openWeatherMap) {
        mOpenWeatherMap = openWeatherMap;
        mTxtViewNoCity.setVisibility(View.GONE);
        clearAllViews();

        mTxtCity.setText(String.format("%s , %s", mOpenWeatherMap.getName(),
                mOpenWeatherMap.getSys().getCountry()));
        mTxtDescription.setText(String.format("Desc - %s",
                mOpenWeatherMap.getWeather().get(0).describeContents()));
        mTxtLastUpdate.setText(String.format("Last updated: %s", Helper.getDateNow()));
        mTxtHumidity.setText(String.format("Humidity - %s",
                mOpenWeatherMap.getMain().getHumidity()));
        mTxtTime.setText(String.format("Sunrise - %s/ Sunset - %s",
                Helper.unixTimeStampToTime(mOpenWeatherMap.getSys().getSunrise()),
                Helper.unixTimeStampToTime(mOpenWeatherMap.getSys().getSunset())));
        mTxtCelsius.setText(String.format("Temprature - %s.2f",
                mOpenWeatherMap.getMain().getTemp()));
        List<Weather> weatherList = mOpenWeatherMap.getWeather();
        RequestCreator requestCreator = Picasso.with(getActivity()).load(Helper.getIcon(weatherList.get(0).getIcon()));
        requestCreator.into(mImageView);

        City city = new City();
        city.setCityName(mOpenWeatherMap.getName());
        //remove old record from db
        //mAppSQLiteHelper.deleteAll();

        // add City to database;
        //mAppSQLiteHelper.insertCity(city);
    }

    public void clearAllViews() {

        mTxtCity.setText("");
        mTxtDescription.setText("");
        mTxtLastUpdate.setText("");
        mTxtHumidity.setText("");
        mTxtTime.setText("");
        mTxtCelsius.setText("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mImageView.setImageIcon(null);
        }

    }
}
