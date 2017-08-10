package com.sekeww.www.auaraiy.data;

import org.json.JSONObject;

public class Channel implements JSONPopulator {

    private Item mItem;
    private Units mUnits;

    public Item getItem() {
        return mItem;
    }

    public Units getUnits() {
        return mUnits;
    }

    @Override
    public void populate(JSONObject data) {
        mUnits = new Units();
        mUnits.populate(data.optJSONObject("units"));

        mItem = new Item();
        mItem.populate(data.optJSONObject("item"));
    }
}
