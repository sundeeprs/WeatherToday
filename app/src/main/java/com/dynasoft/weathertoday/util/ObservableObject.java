package com.dynasoft.weathertoday.util;

import android.util.Log;

import java.util.Observable;

/**
 * Created by sundeep on 11/9/17.
 */

public class ObservableObject extends Observable {

    public String TAG = ObservableObject.class.getSimpleName();

    private static ObservableObject instance = new ObservableObject();

    public static ObservableObject getInstance() {
        return instance;
    }

    private ObservableObject() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
