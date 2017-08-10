package com.sekeww.www.auaraiy;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sekeww.www.auaraiy.data.Channel;
import com.sekeww.www.auaraiy.data.Item;
import com.sekeww.www.auaraiy.service.WeatherSeviceCallback;
import com.sekeww.www.auaraiy.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherSeviceCallback {

    private ImageView mWeatherIconImageView;
    private TextView mTemperatureTextView;
    private TextView mConditionTextView;
    private TextView mLocationTextView;

    private YahooWeatherService mService;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        mTemperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        mConditionTextView = (TextView) findViewById(R.id.conditionTextView);
        mLocationTextView = (TextView) findViewById(R.id.locationTextView);


        mService = new YahooWeatherService(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        mService.refreshWeather("Shymkent, Kazakhstan");

    }

    @Override
    public void serviceSuccess(Channel channel) {
        mProgressDialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        mWeatherIconImageView.setImageDrawable(weatherIconDrawable);
        mTemperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        mConditionTextView.setText(item.getCondition().getDescription());
        mLocationTextView.setText(mService.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {
        mProgressDialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
