package com.mehuljoisar.lockscreen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

import com.mehuljoisar.lockscreen.pattern.ConfirmPatternActivity;
import com.mehuljoisar.lockscreen.pattern.SetPatternActivity;
import com.mehuljoisar.lockscreen.utils.LockscreenService;
import com.mehuljoisar.lockscreen.utils.LockscreenUtils;
import com.mehuljoisar.lockscreen.utils.PolicyManager;

public class LockScreenActivity extends Activity {

	// User-interface
	private Button btnUnlock;
    private Button deviceAdmin;
	// Member variables

	// Set appropriate flags to make the screen appear over the keyguard
	@Override
	public void onAttachedToWindow() {
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onAttachedToWindow();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lockscreen);
			try {
                if(Build.VERSION.SDK_INT>=23)
                   if (!Settings.canDrawOverlays(this)) {
                        Toast t=Toast.makeText(this, "لطفا به برنامه، اجازه دسترسی بدهید", Toast.LENGTH_LONG);
                        t.show();
                        Intent i=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        startActivity(i);
                    }
				// start service for observing intents
				startService(new Intent(this, LockscreenService.class));

				// listen the events get fired during the call
				//TODO: USE THIS
//				StateListener phoneStateListener = new StateListener();
//				TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//				telephonyManager.listen(phoneStateListener,
//						PhoneStateListener.LISTEN_CALL_STATE);

			} catch (Exception e) {
                e.printStackTrace();
			}
	}

    public void handleAdminButton(View v) {
        if(!PolicyManager.getInstance(v.getContext()).isAdminActive())
            PolicyManager.getInstance(v.getContext()).showAdminScreen(this);
    }

	public void setPattern(View v) {
		Intent i=new Intent(this, SetPatternActivity.class);
        startActivity(i);
	}

    public void confirmPattern(View v) {
        Intent i=new Intent(this, ConfirmPatternActivity.class);
        startActivity(i);
    }

	// Handle events of calls and unlock screen if necessary
//	private class StateListener extends PhoneStateListener {
//		@Override
//		public void onCallStateChanged(int state, String incomingNumber) {
//
//			super.onCallStateChanged(state, incomingNumber);
//			switch (state) {
//			case TelephonyManager.CALL_STATE_RINGING:
//			//	unlockHomeButton();
//				break;
//			case TelephonyManager.CALL_STATE_OFFHOOK:
//				break;
//			case TelephonyManager.CALL_STATE_IDLE:
//				break;
//			}
//		}
//	};

}