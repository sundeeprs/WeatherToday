package com.dynasoft.weathertoday.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.widget.TextView;

import java.util.Observable;

/**
 * Created by sundeep on 11/8/17.
 */

public class FragmentViewModel extends ViewModel {


    public static TextView noCitySearchedTextView;

    public FragmentViewModel() {

    }

    @BindingAdapter("settxtNoCity")
    public static void settxtNoCity(final TextView textView, boolean status){
        noCitySearchedTextView = textView;
    }

    public interface OnCityDetailsListener {
    }
}
