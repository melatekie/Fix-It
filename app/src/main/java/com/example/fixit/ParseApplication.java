package com.example.fixit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Registering Post class
        ParseObject.registerSubclass(Post.class);
        //Registering User class
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Professional.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.MYAPPID)
                .clientKey(BuildConfig.CLIENTKEY)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
