package com.dynasoft.weathertoday.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by sundeep on 10/11/17.
 */

public class Helper {

    static String stream = null;

    public Helper(){

    }

    public static String unixTimeStampToTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long) unixTimeStamp*1000);
        return dateFormat.format(date);

    }

    public static String getIcon(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png", icon);

    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static boolean containsDigit(final String s)
    {
        if (s != null && !s.isEmpty())
        {
            for (char c : s.toCharArray())
            {
                if (Character.isDigit(c))
                {
                    return true;
                }
            }
        }

        return false;
    }


    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
