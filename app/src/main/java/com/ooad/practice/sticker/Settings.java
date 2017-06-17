package com.ooad.practice.sticker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.ooad.practice.sticker.Bean.Sticker;
import com.ooad.practice.sticker.Model.Reminder;
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
            /*Intent intent = new Intent(MainApplication.getContext(), Reminder.class);
            List<Sticker> stickerList = new StickerList().getRemindList();

            for(Sticker sticker: stickerList){
                Bundle bundle = new Bundle();
                bundle.putLong("stickerRemindTime", sticker.calculateDate(sticker.getRemindTime()));
                bundle.putString("stickerTitle", sticker.getTitle());
                bundle.putInt("stickerID", sticker.getStickerID());
                bundle.putBoolean("operationCode", notificationValue);
                intent.putExtra("Bundle", bundle);
                getActivity().startService(intent);
            }*/
        }
    }

    public SharedPreferences getSettingsPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }
}
