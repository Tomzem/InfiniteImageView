package com.example.tomze.infiniteimageview;

import android.app.Application;

/**
 * Created by Administrator on 2018/10/31.
 * <p>
 * Description:
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
