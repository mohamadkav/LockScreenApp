/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.mehuljoisar.lockscreen.pattern;

import android.os.Bundle;
import android.view.MenuItem;

import com.mehuljoisar.lockscreen.utils.AppUtils;
import com.mehuljoisar.lockscreen.utils.PatternLockUtils;

import java.util.List;

import me.zhanghai.patternlock.PatternView;

public class SetPatternActivity extends me.zhanghai.patternlock.SetPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtils.setupActionBarDisplayUp(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppUtils.navigateUp(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        PatternLockUtils.setPattern(pattern, this);
    }
}
