package com.findmeout.android.accessibility;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by umesh0492 on 04/01/16.
 */
public class AccessibilityUtils {

    private static final String TAG = "AccessibilityUtils";

    // To check if service is enabled
    public boolean isAccessibilitySettingsOn (Context mContext) {
        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName () + "/" + NewAccessibilityService.class.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (
                    mContext.getApplicationContext ().getContentResolver (),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v (TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e (TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage ());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            Log.v (TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString (
                    mContext.getApplicationContext ().getContentResolver (),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString (settingValue);
                while (mStringColonSplitter.hasNext ()) {
                    String accessibilityService = mStringColonSplitter.next ();

                    Log.v (TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase (service)) {
                        Log.v (TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        }
        else {
            Log.v (TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }
}
