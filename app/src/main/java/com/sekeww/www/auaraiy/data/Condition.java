package com.sekeww.www.auaraiy.data;

import org.json.JSONObject;

public class Condition implements JSONPopulator {

    private int mCode;
    private int mTemperature;
    private String mDescription;

    public int getCode() {
        return mCode;
    }

    public int getTemperature() {
        return mTemperature;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public void populate(JSONObject data) {
        mCode = data.optInt("code");
        mTemperature = data.optInt("temp");
        mDescription = data.optString("text");
    }
}
