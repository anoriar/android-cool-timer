package com.example.cooltimer;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment  extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.timer_preference, rootKey);
    }
}
