package com.ooad.practice.sticker.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by fuyiru on 2017/6/18.
 */

public class ReminderAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("123", "123");
        Reminder reminder = new Reminder(intent);
        reminder.createNotification();
    }
}
