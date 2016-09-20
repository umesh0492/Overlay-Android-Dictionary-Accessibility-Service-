package com.findmeout.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {

    private static final String PREF = "find_me_out";
    private static final String ACCESSIBILITY_PERMISSION = "ACCESSIBILITY_PERMISSION";
    private static final String APP_VERSION = "version";
    private static final String APP_STATE = "state";
    private static final String PREF_PUB_KEY = "PREF_PUB_KEY";
    private static final String PREF_PRI_KEY = "PREF_PRI_KEY";
    private static final String REGISTRATION_DONE = "REGISTRATION_DONE";
    private static final String GCM_ID = "gcm_id";
    private static final String NEXT_DOWNLOAD_WORD_ID = "NEXT_DOWNLOAD_WORD_ID";
    private static final String NEXT_DOWNLOAD_CATEGORY_ID = "NEXT_DOWNLOAD_CATEGORY_ID";
    private static final String NEXT_DOWNLOAD_WORD_MEANING_ID = "NEXT_DOWNLOAD_WORD_MEANING_ID";


    private static SharedPreferences getSharedPref (Context ctx) {
        return ctx.getSharedPreferences (PREF, Context.MODE_PRIVATE);
    }


    public static String getPubKey () {
        return getSharedPref (MainApplication.context).getString (PREF_PUB_KEY, null);
    }

    public static void setPubKey (String key) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putString (PREF_PUB_KEY, key);
        edit.commit ();
    }

    public static String getGcmId () {
        return getSharedPref (MainApplication.context).getString (GCM_ID, "");
    }

    public static void setGcmId (String key) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putString (GCM_ID, key);
        edit.commit ();
    }

    public static String getPriKey () {

        if (null != getSharedPref (MainApplication.context).getString (PREF_PRI_KEY, null)) {
            setRegistration (true);
        }
        return getSharedPref (MainApplication.context).getString (PREF_PRI_KEY, null);
    }

    public static void setPriKey (String key) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putString (PREF_PRI_KEY, key);
        edit.commit ();

        setRegistration (true);
    }

    public static Integer getAppVersion () {
        return getSharedPref (MainApplication.context).getInt (APP_VERSION, Integer.MIN_VALUE);
    }

    public static void setAppVersion (int version) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putInt (APP_VERSION, version);
        edit.commit ();
    }

    // 0= At Information Screen, 1 = Set User Profile, 2 = Chat Activity
    public static int getAppState () {
        return getSharedPref (MainApplication.context).getInt (APP_STATE, 0);
    }

    public static void setAppState (int state) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putInt (APP_STATE, state);
        edit.commit ();
    }

    public static void setRegistration (boolean isRegistered) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putBoolean (REGISTRATION_DONE, isRegistered);
        edit.commit ();
    }

    public static boolean isRegistered () {
        return getSharedPref (MainApplication.context).getBoolean (REGISTRATION_DONE, false);
    }


    public static String getNextDownloadWordId () {
        return getSharedPref (MainApplication.context).getString (NEXT_DOWNLOAD_WORD_ID, "0");
    }

    public static void setNextDownloadWordId (String id) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putString (NEXT_DOWNLOAD_WORD_ID, id);
        edit.commit ();
    }

    public static String getNextDownloadWordMeaningId () {
        return getSharedPref (MainApplication.context).getString (NEXT_DOWNLOAD_WORD_MEANING_ID, "0");
    }

    public static void setNextDownloadWordMeaningId (String id) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putString (NEXT_DOWNLOAD_WORD_MEANING_ID, id);
        edit.commit ();
    }

    public static String getNextDownloadWordMeaningCategoryId () {
        return getSharedPref (MainApplication.context).getString (NEXT_DOWNLOAD_CATEGORY_ID, "0");
    }

    public static void setNextDownloadWordMeaningCategoryId (String id) {
        Editor edit = getSharedPref (MainApplication.context).edit ();
        edit.putString (NEXT_DOWNLOAD_CATEGORY_ID, id);
        edit.commit ();
    }


    public static void onAuthFail(){

        //on app auth fail
        setAppState (2);// on registration fragment
        setPubKey (null);
        setPriKey (null);

    }
}
