package com.mehuljoisar.lockscreen.utils;


/**
 * Created by Mohammad-PC on 2015-07-02.
 */

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;

public final class PolicyManager
{
    private static PolicyManager singleton;

    DevicePolicyManager devicePolicyManager;
    ComponentName deviceAdminReceiver;

    private PolicyManager(Context context)
    {
        this.devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.deviceAdminReceiver = new ComponentName(context, PolicyReceiver.class);
    }

    public static PolicyManager getInstance(Context c)
    {
        if(PolicyManager.singleton == null)
        {
            synchronized(PolicyManager.class)
            {
                if(PolicyManager.singleton == null)
                {
                    PolicyManager.singleton = new PolicyManager(c);
                }
            }
        }
        return PolicyManager.singleton;
    }

    public void showAdminScreen(Activity parent)
    {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdminReceiver);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"");
        parent.startActivityForResult(intent, 1);
    }

    public boolean isAdminActive()
    {
        return this.devicePolicyManager.isAdminActive(this.deviceAdminReceiver);
    }

    public void unLock()
    {
        if(this.isAdminActive())
        {
            this.devicePolicyManager.resetPassword("",0);
        }
        else
            throw new SecurityException("PolicySecurityException");
    }


    public static class PolicyReceiver extends DeviceAdminReceiver
    {
        @Override
        public void onEnabled(Context context, Intent intent) {
            PolicyManager.getInstance(context).unLock();
        }

    }
}