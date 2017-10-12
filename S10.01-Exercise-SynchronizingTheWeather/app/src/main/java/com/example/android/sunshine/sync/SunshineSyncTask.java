package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

//  DONE (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {
//   DONE (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    synchronized public static void syncWeather(Context context){
//      DONE (3) Within syncWeather, fetch new weather data
        try{
            //Build the URL you you you you
            URL weatherRequestUrl = NetworkUtils.getUrl(context);

            //Get the JSON string from the URL
            String jsonResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            //Get the values from the JSON string and store them as ContentValues
            ContentValues[] weatherValues = OpenWeatherJsonUtils
                    .getWeatherContentValuesFromJson(context, jsonResponse);

//      DONE (4) If we have valid results, delete the old data and insert the new
            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver sunshineContentResolver = context.getContentResolver();

                //Delete the old data
                sunshineContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                //Use bulk insert method to insert data
                sunshineContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        weatherValues);
            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}