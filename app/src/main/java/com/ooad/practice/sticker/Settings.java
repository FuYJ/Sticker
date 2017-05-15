package com.ooad.practice.sticker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by mousecat1 on 2017/5/13.
 */

public class Settings extends PreferenceActivity implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new Prefs1Fragment()).commit();
    }

    public static class Prefs1Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }

    public SharedPreferences getSettingsPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return true;
    }
}
