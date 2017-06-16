package com.ooad.practice.sticker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by fuyiru on 2017/5/5.
 */

public class MainApplication extends Application{

    private static Context mContext;
    private static Activity mActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mActivity = getActivity();
    }

    public static Context getContext(){
        return mContext;
    }

    public static Activity getActivity(){
        return mActivity;
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
