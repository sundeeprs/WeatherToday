package com.dynasoft.weathertoday.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dynasoft.weathertoday.R;
import com.dynasoft.weathertoday.database.Constants;
import com.dynasoft.weathertoday.model.OpenWeatherMap;
import com.dynasoft.weathertoday.util.Helper;
import com.dynasoft.weathertoday.util.ObservableObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Observable;
import java.util.Observer;

public class MapsFragment extends Fragment implements OnMapReadyCallback, Observer {

    private GoogleMap mMap;
    private static OpenWeatherMap mOpenWeatherMap;
    private String title;
    private int page;
    private LatLng latlng;
    private Context mContext;

    public static MapsFragment newInstance(int page, String title) {

        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putInt("Master", page);
        args.putString("MasterTitle", title);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        page = bundle.getInt("Master");
        title = bundle.getString("MasterTitle");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.txtNoCity);
        textView.setText(page +"  " +title);

        ObservableObject.getInstance().addObserver(this);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null)
            mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(mMap != null) {
            mMap.getUiSettings().isMapToolbarEnabled();
            mMap.getUiSettings().isZoomControlsEnabled();
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Sydney and move the camera
        if(latlng == null)
            latlng = new LatLng(-34, 151);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latlng).title("My current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }

    @Override
    public void update(Observable observable, Object data) {
        mOpenWeatherMap = (OpenWeatherMap) data;
        if (mOpenWeatherMap != null) {

            latlng = Helper.getLocationFromAddress(mContext,
                    mOpenWeatherMap.getName().toString());
        }


    }
}
