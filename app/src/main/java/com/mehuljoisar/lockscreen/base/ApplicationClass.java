package com.mehuljoisar.lockscreen.base;

import android.app.Application;

/**
 * Created by Mohammad on 12/22/2015.
 */
public class ApplicationClass extends Application {
    private static ApplicationClass singleton;

    public static ApplicationClass getInstance(){
        return singleton;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
