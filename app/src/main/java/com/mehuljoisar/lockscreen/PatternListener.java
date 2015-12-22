package com.mehuljoisar.lockscreen;

import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.mehuljoisar.lockscreen.utils.LockscreenIntentReceiver;
import com.mehuljoisar.lockscreen.utils.PatternLockUtils;
import com.mehuljoisar.lockscreen.utils.PreferenceContract;
import com.mehuljoisar.lockscreen.utils.PreferenceUtils;

import java.util.List;

import me.zhanghai.patternlock.PatternView;
import me.zhanghai.patternlock.ViewAccessibilityCompat;

/**
 * Created by Mohammad on 12/20/2015.
 */
public class PatternListener implements PatternView.OnPatternListener {
    protected int numFailedAttempts=0;
    PatternView patternView;
    TextView messageText;
    WindowManager wm;
    ViewGroup toRemove;
    Context context;
    public PatternListener(PatternView patternView,TextView messageText,WindowManager wm,ViewGroup toRemove,Context c){
        this.patternView=patternView;
        this.messageText=messageText;
        this.wm=wm;
        this.toRemove=toRemove;
    }
    @Override
    public void onPatternStart() {

        removeClearPatternRunnable();

        // Set display mode to correct to ensure that pattern can be in stealth mode.
        patternView.setDisplayMode(PatternView.DisplayMode.Correct);
    }
    protected void removeClearPatternRunnable() {
        patternView.removeCallbacks(clearPatternRunnable);
    }
    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            patternView.clearPattern();
        }
    };
    @Override
    public void onPatternCellAdded(List<PatternView.Cell> pattern) {}

    @Override
    public void onPatternDetected(List<PatternView.Cell> pattern) {
        if (isPatternCorrect(pattern)) {
            onConfirmed();
        } else {
            messageText.setText(me.zhanghai.patternlock.R.string.pl_wrong_pattern);
            patternView.setDisplayMode(PatternView.DisplayMode.Wrong);
            postClearPatternRunnable();
            ViewAccessibilityCompat.announceForAccessibility(messageText, messageText.getText());
            onWrongPattern();
        }
    }
    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        patternView.postDelayed(clearPatternRunnable, 2000);
    }

    @Override
    public void onPatternCleared() {
        removeClearPatternRunnable();
    }

    public boolean isStealthModeEnabled() {
        return false;
//        return !PreferenceUtils.getBoolean(PreferenceContract.KEY_PATTERN_VISIBLE,
//                PreferenceContract.DEFAULT_PATTERN_VISIBLE, context.getApplicationContext());
    }

    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        return PatternLockUtils.isPatternCorrect(pattern, context);
    }

    protected void onConfirmed() {
        wm.removeViewImmediate(toRemove);
        //TODO: TOF
        LockscreenIntentReceiver.patternViewGroup=null;
    }

    protected void onWrongPattern() {
        ++numFailedAttempts;
    }

    protected void onCancel() {

    }

    protected void onForgotPassword() {

    }
}
