package com.findmeout.android.data.client;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.findmeout.android.MainApplication;
import com.findmeout.android.data.provider.DataContract;
import com.findmeout.android.data.provider.DataContract.Dictionary;
import com.findmeout.android.model.AppListModel;
import com.findmeout.android.model.DictionaryWordModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static com.findmeout.android.data.provider.DataContentProvider.APP_LIST_ID;
import static com.findmeout.android.data.provider.DataContentProvider.APP_URI;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_LIST_ID;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_URI;


/**
 * Created by umesh0492 on 29/03/15.
 */
public final class DataClient {
    private static final String AUTHORITY = DataContract.CONTENT_AUTHORITY;
    private static final String PREFIX = "content://" + AUTHORITY + "/";

    private static ContentResolver contentResolver = MainApplication.context.getContentResolver ();

    public static void insertInitApp () {
       /*
                HIKE ("com.bsb.hike"),
                GMAIL ("com.google.android.gm"), HANGOUT ("com.google.android.talk"),
                MESSENGER ("com.google.android.apps.messaging"),
                WHATSAPP("com.whatsapp"), FACEBOOK("com.facebook.katana");
*/
        AppListModel app = new AppListModel ();
        app.setPackage_name ("com.medium.reader");
        app.setIs_active (true);
        app.setUpdated_on (UTCString ());
        //Log.e ("db ins 0", String.valueOf (insertApp (app)));

        AppListModel app1 = new AppListModel ();
        app1.setPackage_name ("com.quora.android");
        app1.setIs_active (true);
        app1.setUpdated_on (UTCString ());
        //Log.e ("db ins 1", String.valueOf (insertApp (app1)));


        AppListModel app2 = new AppListModel ();
        app2.setPackage_name ("com.android.chrome");
        app2.setIs_active (true);
        app2.setUpdated_on (UTCString ());
        //Log.e ("db ins 2", String.valueOf (insertApp (app2)));

        AppListModel app3 = new AppListModel ();
        app3.setPackage_name ("com.tumblr");
        app3.setIs_active (true);
        app3.setUpdated_on (UTCString ());
        //Log.e ("db ins 3", String.valueOf (insertApp (app3)));

        AppListModel app4 = new AppListModel ();
        app4.setPackage_name ("org.wordpress.android");
        app4.setIs_active (true);
        app4.setUpdated_on (UTCString ());
        //Log.e ("db ins 4", String.valueOf (insertApp (app4)));

    }

    public static String UTCString () {
        TimeZone tz = TimeZone.getTimeZone ("UTC");
        DateFormat df = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone (tz);
        return df.format (new Date ());
    }

    public static Uri insertApp (AppListModel model) {

        ContentValues contentValues = new ContentValues ();

        contentValues.put (DataContract.AppList.COLUMN_NAME_APP_PACKAGE,
                model.getPackage_name ());
        contentValues.put (DataContract.AppList.COLUMN_NAME_IS_ACTIVE,
                String.valueOf (model.getIs_active ()));
        contentValues.put (DataContract.AppList.COLUMN_NAME_UPDATED_ON,
                model.getUpdated_on ());

        return contentResolver.insert (Uri.parse (PREFIX + "/" + APP_URI + "/"
                + APP_LIST_ID), contentValues);
    }

    public static Uri insertDictionary (DictionaryWordModel.Word model) {

        ContentValues contentValues = new ContentValues ();

        contentValues.put (Dictionary._ID, model.getId ());
        contentValues.put (Dictionary.COLUMN_NAME_WORD,
                model.getWord ());
        contentValues.put (Dictionary
                .COLUMN_NAME_MEANINGS, model.getMeanings ());
        contentValues.put (Dictionary
                .COLUMN_NAME_PRONUNCIATION, model.getPronunciation ());
        contentValues.put (Dictionary
                .COLUMN_NAME_PRONUNCIATION_SOUND, model.getPronunciationSound ());
        contentValues.put (Dictionary
                .COLUMN_NAME_UPDATED_ON, model.getUpdatedOn ());

        return contentResolver.insert (Uri.parse (PREFIX + "/" + DICTIONARY_URI + "/"
                + DICTIONARY_LIST_ID), contentValues);
    }

    public static int getAppCount () {

        String[] columns = {DataContract.AppList.COLUMN_NAME_APP_PACKAGE};

        Cursor cursor = contentResolver.query (Uri.parse (PREFIX + "/" + DICTIONARY_URI),
                columns, null, null, null);

        assert cursor != null;
        int count = cursor.getCount ();
        cursor.close ();

        return count;
    }


    public static DictionaryWordModel.Word getWordMeaning (String word) {

        String[] columns = {
                Dictionary._ID,
                Dictionary.COLUMN_NAME_WORD,
                Dictionary.COLUMN_NAME_MEANINGS,
                Dictionary.COLUMN_NAME_UPDATED_ON
        };

        Cursor cursor = contentResolver.query (Uri.parse (PREFIX + "/" + DICTIONARY_URI),
                columns, Dictionary.COLUMN_NAME_WORD + " = ? ", new String[]{word}, null);

        assert cursor != null;
        if (cursor.getCount () > 0) {
            cursor.moveToFirst ();

            DictionaryWordModel.Word mWordMeaning = new DictionaryWordModel.Word ();

            mWordMeaning.setId (cursor.getString (cursor.getColumnIndexOrThrow (
                    Dictionary._ID)));
            mWordMeaning.setMeanings (cursor.getString (cursor.getColumnIndexOrThrow (
                    Dictionary.COLUMN_NAME_MEANINGS)));
            mWordMeaning.setWord (cursor.getString (cursor.getColumnIndexOrThrow (
                    Dictionary.COLUMN_NAME_WORD)));
            mWordMeaning.setUpdatedOn (cursor.getString (cursor.getColumnIndexOrThrow (
                    Dictionary.COLUMN_NAME_UPDATED_ON)));

            cursor.close ();
            return mWordMeaning;
        }
        cursor.close ();

        return null;
    }


    public static void updateAppActivieStatus (String appPackage, boolean isActive) {

        ContentValues contentValues = new ContentValues ();

        contentValues.put (DataContract.AppList.COLUMN_NAME_IS_ACTIVE, isActive);

        contentResolver.update (Uri.parse (PREFIX + "/" + APP_URI + "/" + APP_LIST_ID), contentValues,
                DataContract.AppList.COLUMN_NAME_APP_PACKAGE + " =? ", new String[]{appPackage});

    }


    public static ArrayList<AppListModel> getAppList () {

        String[] columns = {
                DataContract.AppList.COLUMN_NAME_APP_PACKAGE,
                DataContract.AppList.COLUMN_NAME_IS_ACTIVE,
                DataContract.AppList.COLUMN_NAME_UPDATED_ON
        };

        Cursor cursor = contentResolver.query (Uri.parse (PREFIX + "/" + APP_URI),
                columns, DataContract.AppList.COLUMN_NAME_IS_ACTIVE + "=?",
                new String[]{"true"}, null);

        ArrayList<AppListModel> mAppList = new ArrayList<> ();

        assert cursor != null;
        if (cursor.getCount () > 0) {
            cursor.moveToFirst ();
            do {

                AppListModel appListModel = new AppListModel ();

                appListModel.setUpdated_on ((cursor.getString (cursor.getColumnIndexOrThrow (
                        DataContract.AppList.COLUMN_NAME_UPDATED_ON))));
                appListModel.setIs_active (Boolean.parseBoolean ((cursor.getString (cursor.getColumnIndexOrThrow (
                        DataContract.AppList.COLUMN_NAME_IS_ACTIVE)))));
                appListModel.setPackage_name ((cursor.getString (cursor.getColumnIndexOrThrow (
                        DataContract.AppList.COLUMN_NAME_APP_PACKAGE))));

                mAppList.add (appListModel);

            } while (cursor.moveToNext ());
        }

        cursor.close ();

        return mAppList;
    }

    public static ArrayList<String> getAppPackageList () {

        String[] columns = {
                DataContract.AppList.COLUMN_NAME_APP_PACKAGE};

        Cursor cursor = contentResolver
                .query (
                        Uri.parse (PREFIX + "/" + APP_URI),
                columns, DataContract.AppList.COLUMN_NAME_IS_ACTIVE + "=?",
                new String[]{"true"}, null);

        ArrayList<String> appPackageList = new ArrayList<> ();
        assert cursor != null;
        if (cursor.getCount () > 0) {
            cursor.moveToFirst ();
            do {
                //Log.e ("pac count",cursor.getCount ()+"");
                appPackageList.add ((cursor.getString (cursor.getColumnIndexOrThrow (
                        DataContract.AppList.COLUMN_NAME_APP_PACKAGE))));
            } while (cursor.moveToNext ());
        }
        else {
           // Log.e ("pac count","zero");

        }

        cursor.close ();

        return appPackageList;
    }

}
