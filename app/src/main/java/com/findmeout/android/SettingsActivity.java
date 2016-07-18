package com.findmeout.android;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.findmeout.android.accessibility.AccessibilityUtils;

import java.lang.reflect.Method;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.setting_activity);
        requestAccessibilityServicePermission ();
    }

    void requestAccessibilityServicePermission () {

        if (!AccessibilityUtils.isAccessibilitySettingsOn (this)) {
            Intent intent = new Intent (android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult (intent, 0);
        }
        else {
            Preferences.setAccessibilityPermission (true);
            checkDrawOverlayPermission ();
        }

    }

    public void checkDrawOverlayPermission () {
        /** check if we already  have permission to draw over other apps */
        if (!canDrawOverlayViews ()) {
            /** if not construct intent to request permission */

            try {
                requestOverlayDrawPermission (1);
            } catch (ActivityNotFoundException e) {

            }

        }
        else {
            showSupportedAppList ();
        }
    }

    public boolean canDrawOverlayViews () {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        try {
            return Settings.canDrawOverlays (this);
        } catch (NoSuchMethodError e) {
            e.printStackTrace ();
            return canDrawOverlaysUsingReflection (this);
        }

    }


    public boolean canDrawOverlaysUsingReflection (Context context) {

        try {
            AppOpsManager manager = (AppOpsManager) context.getSystemService (Context.APP_OPS_SERVICE);
            Class clazz = AppOpsManager.class;
            Method dispatchMethod = clazz.getMethod ("checkOp", new Class[]{int.class, int.class, String.class});
            //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
            int mode = (Integer) dispatchMethod.invoke (manager, new Object[]{24, Binder.getCallingUid (), context.getApplicationContext ().getPackageName ()});
          /* int modee = manager.checkOp(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                    Binder.getCallingUid(), context.getPackageName());

          */
            Log.e ("mode", mode + "");

            return AppOpsManager.MODE_ALLOWED == mode;


        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }

    }

    @SuppressLint ("InlinedApi")
    public void requestOverlayDrawPermission (int requestCode) {
        Intent intent = new Intent (Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse ("package:" + getPackageName ()));

        startActivityForResult (intent, requestCode);

    }



    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */

        switch (requestCode) {
            case 0:
                requestAccessibilityServicePermission ();
                break;
            case 1:

                if(resultCode == RESULT_CANCELED)
                {
                    Toast.makeText (this,"show permission not granted automatically, kindly grant manuallly",Toast.LENGTH_LONG).show ();

                    //:// TODO: 17/07/16  show permission not granted automatically, kindly grant manuallly
                }

                break;
        }
    }

    void showSupportedAppList () {

        Toast.makeText (this,"wowwww",Toast.LENGTH_LONG).show ();
    }
}
