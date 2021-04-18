package com.example.fixit;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.MYAPPID)
                .clientKey(BuildConfig.CLIENTKEY)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
