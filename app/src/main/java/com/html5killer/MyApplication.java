package com.html5killer;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by Melody Lam on 2/21/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        OneSignal.startInit(this).init();

    }

}
