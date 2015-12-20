package com.mehuljoisar.lockscreen.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mehuljoisar.lockscreen.PatternListener;
import com.mehuljoisar.lockscreen.R;
import com.mehuljoisar.lockscreen.db.DataBaseUtility;

import me.zhanghai.patternlock.PatternView;
import me.zhanghai.patternlock.ViewAccessibilityCompat;

public class LockscreenIntentReceiver extends BroadcastReceiver {

	// Handle actions and display Lockscreen
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
				|| intent.getAction().equals(Intent.ACTION_SCREEN_ON)
				|| intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            startLockScreen(context);
		}

	}
	public static ViewGroup mTopView =null;
    public static ViewGroup patternViewGroup =null;
	// Display lock screen
	private void startLockScreen(Context context) {
		try {
            DataBaseUtility.getInstance(context);
			if(mTopView!=null)
				return;
			final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			WindowManager.LayoutParams params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,//OVERLAY,
					WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN ,
					PixelFormat.TRANSLUCENT);
			params.gravity = Gravity.RIGHT | Gravity.TOP;
			mTopView = (ViewGroup) LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.alarm, null);
            patternViewGroup = (ViewGroup) LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.pl_base_pattern_activity, null);
            TextView messageText;
            PatternView patternView;
            LinearLayout buttonContainer;
            Button leftButton;
            Button rightButton;
            messageText = (TextView)patternViewGroup.findViewById(me.zhanghai.patternlock.R.id.pl_message_text);
            patternView = (PatternView)patternViewGroup.findViewById(me.zhanghai.patternlock.R.id.pl_pattern);
            buttonContainer = (LinearLayout)patternViewGroup.findViewById(me.zhanghai.patternlock.R.id.pl_button_container);
            leftButton = (Button)patternViewGroup.findViewById(me.zhanghai.patternlock.R.id.pl_left_button);
            rightButton = (Button)patternViewGroup.findViewById(me.zhanghai.patternlock.R.id.pl_right_button);
            messageText.setText(me.zhanghai.patternlock.R.string.pl_draw_pattern_to_unlock);
            PatternListener pl=new PatternListener(patternView,messageText,wm,patternViewGroup,context);
            patternView.setInStealthMode(pl.isStealthModeEnabled());
            patternView.setOnPatternListener(pl);
            leftButton.setText(me.zhanghai.patternlock.R.string.pl_cancel);
            rightButton.setText(me.zhanghai.patternlock.R.string.pl_forgot_pattern);
            ViewAccessibilityCompat.announceForAccessibility(messageText, messageText.getText());

            mTopView.findViewById(R.id.unlockbutton).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					wm.removeViewImmediate(mTopView);
					mTopView = null;
				}
			});
            if(Build.VERSION.SDK_INT>=19)
            mTopView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            wm.addView(patternViewGroup,params);
			wm.addView(mTopView, params);
		}catch (Exception e) {
            e.printStackTrace();
		}
//		Intent mIntent = new Intent(context, LockScreenActivity.class);
//		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(mIntent);
	}

}
