package com.sekeww.www.auaraiy.data;

import org.json.JSONObject;

public class Item implements JSONPopulator {

    private Condition mCondition;

    public Condition getCondition() {
        return mCondition;
    }

    @Override
    public void populate(JSONObject data) {
        mCondition = new Condition();
        mCondition.populate(data.optJSONObject("condition"));
    }
}
