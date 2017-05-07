package com.ooad.practice.sticker;

import android.app.Application;
import android.content.Context;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class MainApplication extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

    }

    public static Context getContext(){
        return mContext;
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
