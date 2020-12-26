package com.cleanup.todoc;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.Stetho;

public class TodocApplication extends Application {

    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
