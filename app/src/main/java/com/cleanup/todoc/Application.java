package com.cleanup.todoc;

import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.Stetho;

public class Application extends android.app.Application {

    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
