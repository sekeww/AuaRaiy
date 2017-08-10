package com.sekeww.www.auaraiy.service;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.sekeww.www.auaraiy.data.Channel;


public class YahooWeatherService {

    private WeatherSeviceCallback mCallback;
    private String  mLocation;
    private Exception mException;

    public YahooWeatherService(WeatherSeviceCallback callback) {
        mCallback = callback;
    }

    public String getLocation() {
        return mLocation;
    }

    public void refreshWeather(String location){
        this.mLocation = location;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'",strings[0]);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();

                } catch (Exception e) {
                    mException = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if(s == null && mException != null){
                    mCallback.serviceFailure(mException);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");
                    if (count == 0) {
                        mCallback.serviceFailure(new LocationWeatherException("No weather information found for " + mLocation));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    mCallback.serviceSuccess(channel);
                } catch (JSONException e) {
                    mCallback.serviceFailure(e);
                }
            }

        }.execute(mLocation);
    }

    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String message) {
            super(message);
        }
    }


}
