package com.mehuljoisar.lockscreen.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mehuljoisar.lockscreen.pattern.PatternListener;
import com.mehuljoisar.lockscreen.R;
import com.mehuljoisar.lockscreen.db.DataBaseUtility;
import com.mehuljoisar.lockscreen.lib.ResideLayout;
import com.mehuljoisar.lockscreen.shimmer.Shimmer;
import com.mehuljoisar.lockscreen.shimmer.ShimmerTextView;

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
            if(patternViewGroup!=null){
                wm.removeViewImmediate(patternViewGroup);
                patternViewGroup=null;
            }
			WindowManager.LayoutParams params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,//OVERLAY,
					WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN ,
					PixelFormat.TRANSLUCENT);
			params.gravity = Gravity.RIGHT | Gravity.TOP;
			mTopView = (ViewGroup) LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.alarm, null);
            patternViewGroup = (ViewGroup) LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.pl_base_pattern_activity_view, null);
            TextView messageText;
            PatternView patternView;
            LinearLayout buttonContainer;
            Button leftButton;
            Button rightButton;
            messageText = (TextView)patternViewGroup.findViewById(me.zhanghai.patternlock.R.id.pl_message_text);
            patternView = (PatternView)patternViewGroup.findViewById(me.zhanghai.patternlock.R.id.pl_pattern);
            messageText.setText(me.zhanghai.patternlock.R.string.pl_draw_pattern_to_unlock);
            PatternListener pl=new PatternListener(patternView,messageText,wm,patternViewGroup,context);
            patternView.setInStealthMode(pl.isStealthModeEnabled());
            patternView.setOnPatternListener(pl);
            ViewAccessibilityCompat.announceForAccessibility(messageText, messageText.getText());
            ResideLayout resideLayout=(ResideLayout)mTopView.findViewById(R.id.reside_layout);
            resideLayout.setPanelSlideListener(new ResideLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    if (slideOffset == 1) {
                        patternViewGroup.removeView(mTopView);
                        mTopView = null;
                    }
                }

                @Override
                public void onPanelOpened(View panel) {

                }

                @Override
                public void onPanelClosed(View panel) {

                }
            });
            ShimmerTextView tv=(ShimmerTextView)mTopView.findViewById(R.id.shimmer_tv);
            Shimmer s=new Shimmer();
            s.start(tv);
//            setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    patternViewGroup.removeView(mTopView);
//                    mTopView = null;
//                }
//            });
            wm.addView(patternViewGroup,params);
            patternViewGroup.addView(mTopView, params);
		}catch (Exception e) {
            e.printStackTrace();
		}
//		Intent mIntent = new Intent(context, LockScreenActivity.class);
//		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(mIntent);
	}

}
