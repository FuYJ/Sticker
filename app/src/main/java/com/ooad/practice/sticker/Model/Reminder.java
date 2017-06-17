package com.ooad.practice.sticker.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.MainActivity;
import com.ooad.practice.sticker.MainApplication;
import com.ooad.practice.sticker.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fuyiru on 2017/6/15.
 */

public class Reminder{
    private Handler mHandler;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private PendingIntent appIntent;
    private Boolean operationCode;
    private Integer stickerID;
    private String stickerTitle;
    private Long stickerRemindTime;
    private Timer timer;

    public Reminder(Intent intent){
        timer = new Timer();
        manager = (NotificationManager) MainApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(MainApplication.getContext());
        Intent notifyIntent = new Intent(MainApplication.getContext(), MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appIntent = PendingIntent.getActivity(MainApplication.getContext(), 0, notifyIntent, 0);
        this.operationCode = intent.getBundleExtra("Bundle").getBoolean("operationCode");
        this.stickerID = intent.getBundleExtra("Bundle").getInt("stickerID");
        this.stickerTitle = intent.getBundleExtra("Bundle").getString("stickerTitle");
        this.stickerRemindTime = intent.getBundleExtra("Bundle").getLong("stickerRemindTime");
    }

/*    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        manager = (NotificationManager) MainApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(MainApplication.getContext());
        Intent notifyIntent = new Intent(MainApplication.getContext(), MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appIntent = PendingIntent.getActivity(MainApplication.getContext(), 0, notifyIntent, 0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        this.operationCode = intent.getBundleExtra("Bundle").getBoolean("operationCode");
        this.stickerID = intent.getBundleExtra("Bundle").getInt("stickerID");
        this.stickerTitle = intent.getBundleExtra("Bundle").getString("stickerTitle");
        this.stickerRemindTime = intent.getBundleExtra("Bundle").getLong("stickerRemindTime");

        if(!operationCode){
            timer.cancel();
            timer.purge();
            return START_NOT_STICKY;
        }

        long delayTime = stickerRemindTime - System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                createNotification();
            }
        }, delayTime);

        return START_NOT_STICKY;
    }*/

    public void createNotification(){
        builder.setSmallIcon(R.drawable.abc_ab_share_pack_mtrl_alpha)
                .setContentTitle("有即將到期的便利貼")
                .setContentText(stickerTitle + " 的Deadline快到囉")
                .setWhen(System.currentTimeMillis())
                .setTicker("有即將到期的便利貼")
                .setContentIntent(appIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        manager.notify(stickerID, notification);
    }

    private void cancelNotification(){
        manager.cancel(stickerID);
    }
}
