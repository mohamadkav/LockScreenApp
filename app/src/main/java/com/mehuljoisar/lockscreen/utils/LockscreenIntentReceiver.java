package com.mehuljoisar.lockscreen.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.mehuljoisar.lockscreen.R;

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
	// Display lock screen
	private void startLockScreen(Context context) {
		try {
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
			wm.addView(mTopView, params);
		}catch (Exception e) {
		}
//		Intent mIntent = new Intent(context, LockScreenActivity.class);
//		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(mIntent);
	}

}
