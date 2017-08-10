package com.sekeww.www.auaraiy.data;

import org.json.JSONObject;

public class Units implements JSONPopulator {

    private String mTemperature;

    public String getTemperature() {
        return mTemperature;
    }

    @Override
    public void populate(JSONObject data) {
        mTemperature = data.optString("temperature");
    }
}
