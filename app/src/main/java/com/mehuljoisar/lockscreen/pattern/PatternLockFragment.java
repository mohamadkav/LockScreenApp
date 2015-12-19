/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.mehuljoisar.lockscreen.pattern;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mehuljoisar.lockscreen.R;


public class PatternLockFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences_pattern_lock);
    }
}
