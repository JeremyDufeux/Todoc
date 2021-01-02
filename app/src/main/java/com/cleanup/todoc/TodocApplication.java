package com.cleanup.todoc;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.Stetho;

public class TodocApplication extends Application {

    public void onCreate() {
        super.onCreate();

        boolean isDebuggable =  ( 0 != ( getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );
        if (isDebuggable) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
