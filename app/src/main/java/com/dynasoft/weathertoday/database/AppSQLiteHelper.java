package com.dynasoft.weathertoday.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dynasoft.weathertoday.model.City;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sundeep on 10/12/17.
 */

public class AppSQLiteHelper extends SQLiteOpenHelper implements DatabaseConstants {

    public static final int DB_VERSION = 3;

    public AppSQLiteHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CITY + "(" + COL_CITY_ID
                + " INTEGER PRIMARY KEY NOT NULL, " + " " + COL_CITY_NAME
                + " VARCHAR(50) NOT NULL, " + " " +COL_CITY_TEMP
                + " VARCHAR(20) NOT NULL) ;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older city table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CITY);

        // create fresh city table
        this.onCreate(db);
    }

    /**
     * CRUD operations (create "add", read "get", update, delete) city + get all city + delete all city
     */


    public void insertCity(City city){
        Log.d("add City name to DB", city.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COL_CITY_NAME, city.getCityName()); // get city name
        values.put(COL_CITY_TEMP, city.getCityTemprature()); // get city name

        // 3. insert
        db.insert(TABLE_CITY, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    // Get All Books
    public List<City> getAllCities() {
        List<City> cities = new LinkedList<City>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_CITY;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        City city = null;
        if (cursor.moveToFirst()) {
            do {
                city = new City();
                city.setCityID(Integer.parseInt(cursor.getString(0)));
                city.setCityName(cursor.getString(1));

                // Add city to cities
                cities.add(city);
            } while (cursor.moveToNext());
        }

        Log.d("Get All cities()", cities.toString());

        // return books
        return cities;
    }

    // Deleting single City
    public void deleteCity(City city) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_CITY,
                COL_CITY_ID+" = ?",
                new String[] { String.valueOf(city.getCityID()) });

        // 3. close
        db.close();

        Log.d("City deleted", city.toString());

    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_CITY);

        db.close();
    }

}
