package com.example.cw1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.cw1.R;

public class SettingsFragment extends Fragment {

    private static final String PREF_THEME_MODE = "pref_theme_mode";
    private static final int THEME_MODE_LIGHT = 0;
    private static final int THEME_MODE_DARK = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        RadioGroup temperatureUnitRadioGroup = rootView.findViewById(R.id.temperatureUnitRadioGroup);
        RadioButton darkModeRadioButton = rootView.findViewById(R.id.darkModeRadioButton);
        RadioButton lightModeRadioButton = rootView.findViewById(R.id.lightModeRadioButton);

        // Retrieve the saved theme mode
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        int savedThemeMode = sharedPreferences.getInt(PREF_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        if (savedThemeMode == AppCompatDelegate.MODE_NIGHT_YES) {
            darkModeRadioButton.setChecked(true);
        } else {
            lightModeRadioButton.setChecked(true);
        }

        temperatureUnitRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkedId == R.id.darkModeRadioButton) {
                    // Set the app's theme to dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putInt(PREF_THEME_MODE, AppCompatDelegate.MODE_NIGHT_YES);
                } else if (checkedId == R.id.lightModeRadioButton) {
                    // Set the app's theme to light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putInt(PREF_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO);
                }
                editor.apply();
            }
        });
//        temperatureUnitRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                int nightMode;
//                if (checkedId == R.id.darkModeRadioButton) {
//                    // Set the app's theme to dark mode for this activity only
//                    nightMode = AppCompatDelegate.MODE_NIGHT_YES;
//                    editor.putInt(PREF_THEME_MODE, nightMode);
//                } else {
//                    // Set the app's theme to light mode for this activity only
//                    nightMode = AppCompatDelegate.MODE_NIGHT_NO;
//                    editor.putInt(PREF_THEME_MODE, nightMode);
//                }
//                // Apply the theme mode without recreating the activity
//                ((AppCompatActivity) requireActivity()).getDelegate().setLocalNightMode(nightMode);
//                editor.apply();
//            }
//        });


        return rootView;
    }
}
