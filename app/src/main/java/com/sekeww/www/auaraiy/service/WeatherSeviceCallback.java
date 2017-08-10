package com.sekeww.www.auaraiy.service;

import com.sekeww.www.auaraiy.data.Channel;

public interface WeatherSeviceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
