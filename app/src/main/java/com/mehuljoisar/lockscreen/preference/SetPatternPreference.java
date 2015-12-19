/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.mehuljoisar.lockscreen.preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.mehuljoisar.lockscreen.R;
import com.mehuljoisar.lockscreen.utils.PatternLockUtils;
import com.mehuljoisar.lockscreen.utils.PreferenceContract;

public class SetPatternPreference extends Preference
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SetPatternPreference(Context context, AttributeSet attrs, int defStyleAttr,
                                int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SetPatternPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SetPatternPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SetPatternPreference(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPrepareForRemoval() {
        super.onPrepareForRemoval();

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public CharSequence getSummary() {
        Context context = getContext();
        return PatternLockUtils.hasPattern(context) ?
                context.getString(R.string.pref_summary_set_pattern_has) :
                context.getString(R.string.pref_summary_set_pattern_none);
    }

    @Override
    protected void onClick() {
        PatternLockUtils.setPatternByUser(getContext());
    }

    @Override
    public boolean shouldDisableDependents() {
        return super.shouldDisableDependents() || !PatternLockUtils.hasPattern(getContext());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, PreferenceContract.KEY_PATTERN_SHA1)) {
            notifyChanged();
            notifyDependencyChange(shouldDisableDependents());
        }
    }
}
