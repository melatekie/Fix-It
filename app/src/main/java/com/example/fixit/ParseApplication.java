package com.example.fixit;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("TDpMzUdHU66dHW1rLq3RqGbXEpRIbWJZBOmrtBGC")
                .clientKey("KITmSzFq6PRI6m2mbxfCwtLGAqtYHTg6jeLfkPll")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
