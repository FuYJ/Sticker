package com.ooad.practice.sticker.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by fuyiru on 2017/6/18.
 */

public class ReminderAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Reminder reminder = new Reminder(intent);
        reminder.createNotification();
    }
}
