package com.ooad.practice.sticker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Model.StickerList;

import java.util.List;

/**
 * Created by mousecat1 on 2017/5/13.
 */

public class Settings extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new Prefs1Fragment()).commit();
    }

    public static class Prefs1Fragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        private String notificationKey;
        private String calendarTypeKey;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            notificationKey = getResources().getString(R.string.remind);
            calendarTypeKey = getResources().getString(R.string.calendars);
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(notificationKey)){
                switchNotification(sharedPreferences.getBoolean(key, true));
            }
        }

        private void switchNotification(boolean notificationValue){
            List<Sticker> stickerList = new StickerList().getEmergentList();
            NotificationManager manager = (NotificationManager) MainApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);

            if(notificationValue){
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainApplication.getContext());

                Intent notifyIntent = new Intent(this.getActivity(), MainActivity.class);
                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent appIntent
                        = PendingIntent.getActivity(this.getActivity(), 0, notifyIntent, 0);

                for(Sticker sticker: stickerList){
                    builder.setSmallIcon(R.drawable.abc_ab_share_pack_mtrl_alpha)
                            .setContentTitle("有即將到期的便利貼")
                            .setContentText(sticker.getTitle() + " 的Deadline快到囉")
                            .setWhen(sticker.calculateDate(sticker.getRemindTime()))
                            .setTicker("有即將到期的便利貼")
                            .setContentIntent(appIntent)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL);

                    Notification notification = builder.build();
                    manager.notify(sticker.getStickerID(), notification);
                }
            }
            else{
                for(Sticker sticker: stickerList){
                    manager.cancel(sticker.getStickerID());
                }
            }
        }
    }

    public SharedPreferences getSettingsPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }
}
