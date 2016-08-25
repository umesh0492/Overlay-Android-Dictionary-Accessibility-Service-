package com.findmeout.android.data.client;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.findmeout.android.MainApplication;
import com.findmeout.android.data.provider.DataContract;
import com.findmeout.android.data.provider.DataContract.DictionaryMeaningCategories;
import com.findmeout.android.data.provider.DataContract.DictionaryMeanings;
import com.findmeout.android.data.provider.DataContract.DictionaryWords;
import com.findmeout.android.model.AppListModel;
import com.findmeout.android.model.DictionaryWordModel;
import com.findmeout.android.model.GcmModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static com.findmeout.android.data.provider.DataContentProvider.APP_LIST_ID;
import static com.findmeout.android.data.provider.DataContentProvider.APP_URI;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_WORD_LIST_ID;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_WORD_MEANING_CATEGORY_URI;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_WORD_MEANING_LIST_ID;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_WORD_MEANING_URI;
import static com.findmeout.android.data.provider.DataContentProvider.DICTIONARY_WORD_URI;


/**
 * Created by umesh0492 on 29/03/15.
 */
public final class DataClient {
    private static final String AUTHORITY = DataContract.CONTENT_AUTHORITY;
    private static final String PREFIX = "content://" + AUTHORITY + "/";

    private static ContentResolver contentResolver = MainApplication.context.getContentResolver ();

   /* public static void insertInitApp () {
       *//*
                HIKE ("com.bsb.hike"),
                GMAIL ("com.google.android.gm"), HANGOUT ("com.google.android.talk"),
                MESSENGER ("com.google.android.apps.messaging"),
                WHATSAPP("com.whatsapp"), FACEBOOK("com.facebook.katana");
*//*
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

    }*/

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

    public static String insertDictionaryWord (DictionaryWordModel.Word model) {

        ContentValues contentValues = new ContentValues ();

        contentValues.put (DictionaryWords._ID, model.getWordId ());
        contentValues.put (DictionaryWords.COLUMN_NAME_WORD,
                model.getWord ());
        contentValues.put (DictionaryWords
                .COLUMN_NAME_PHONETIC, model.getPhonetic ());
        contentValues.put (DictionaryWords
                .COLUMN_NAME_PHONETIC_SOUND, model.getPhoneticSound ());
        contentValues.put (DictionaryWords
                .COLUMN_NAME_UPDATED_ON, model.getUpdatedOn ());

        return contentResolver.insert (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_URI + "/"
                + DICTIONARY_WORD_LIST_ID), contentValues).getLastPathSegment ();
    }

    public static int updateDictionaryWord (GcmModel model) {
        ContentValues contentValues = new ContentValues ();

        contentValues.put (DictionaryWords._ID, model.getWord ());
        contentValues.put (DictionaryWords.COLUMN_NAME_WORD,
                model.getWord ());
        contentValues.put (DictionaryWords
                .COLUMN_NAME_PHONETIC, model.getPhonetic ());
        contentValues.put (DictionaryWords
                .COLUMN_NAME_PHONETIC_SOUND, model.getPhoneticSound ());
        contentValues.put (DictionaryWords
                .COLUMN_NAME_UPDATED_ON, model.getUpdatedOn ());

        return contentResolver.update (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_URI + "/"
                + DICTIONARY_WORD_LIST_ID), contentValues, DictionaryWords._ID + " =? ", new String[]{model.getWordId ()});
    }

    public static int deleteDictionaryWord (GcmModel model) {
        return contentResolver.delete (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_URI + "/"
                + DICTIONARY_WORD_LIST_ID), DictionaryWords._ID + " =? ", new String[]{model.getWordId ()});
    }

    public static String insertDictionaryWordMeaning (DictionaryWordModel.Meaning model) {

        ContentValues contentValues = new ContentValues ();

        contentValues.put (DictionaryMeanings._ID, model.getMeaningId ());
        contentValues.put (DictionaryMeanings.COLUMN_NAME_MEANING,
                model.getMeaning ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_MEANING_USAGE, model.getMeaningUsage ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_WORD_ID, model.getWordId ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_CATEGORY_ID, model.getCategoryId ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_UPDATED_ON, model.getUpdatedOn ());

        return contentResolver.insert (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_MEANING_URI + "/"
                + DICTIONARY_WORD_MEANING_LIST_ID), contentValues).getLastPathSegment ();
    }

    public static int updateDictionaryWordMeaning (GcmModel model) {
        ContentValues contentValues = new ContentValues ();

        contentValues.put (DictionaryMeanings._ID, model.getMeaningId ());
        contentValues.put (DictionaryMeanings.COLUMN_NAME_MEANING,
                model.getMeaning ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_MEANING_USAGE, model.getMeaningUsage ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_WORD_ID, model.getWordId ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_CATEGORY_ID, model.getCategoryId ());
        contentValues.put (DictionaryMeanings
                .COLUMN_NAME_UPDATED_ON, model.getUpdatedOn ());

        return contentResolver.update (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_MEANING_URI + "/"
                + DICTIONARY_WORD_MEANING_LIST_ID), contentValues, DictionaryMeanings._ID + " =? ", new String[]{model.getMeaningId ()});

    }

    public static int deleteDictionaryWordMeaning (GcmModel model) {

        return contentResolver.delete (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_MEANING_URI + "/"
                + DICTIONARY_WORD_MEANING_LIST_ID), DictionaryMeanings._ID + " =? ", new String[]{model.getMeaningId ()});

    }

    public static Uri insertDictionaryWordMeaningCategory (DictionaryWordModel.Category model) {

        ContentValues contentValues = new ContentValues ();

        contentValues.put (DictionaryMeaningCategories._ID, model.getCategoryID ());
        contentValues.put (DictionaryMeaningCategories.COLUMN_NAME_CATEGORY_NAME,
                model.getCategoryName ());
        contentValues.put (DictionaryMeaningCategories
                .COLUMN_NAME_UPDATED_ON, model.getUpdatedOn ());

        return contentResolver.insert (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_MEANING_CATEGORY_URI + "/"
                + DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID), contentValues);
    }

    public static int updateDictionaryWordMeaningCategory (GcmModel model) {
        ContentValues contentValues = new ContentValues ();

        contentValues.put (DictionaryMeaningCategories._ID, model.getCategoryId ());
        contentValues.put (DictionaryMeaningCategories.COLUMN_NAME_CATEGORY_NAME,
                model.getCategoryName ());
        contentValues.put (DictionaryMeaningCategories.COLUMN_NAME_UPDATED_ON, model.getUpdatedOn ());

        return contentResolver.update (Uri.parse (PREFIX + "/"
                        + DICTIONARY_WORD_MEANING_CATEGORY_URI + "/"
                        + DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID), contentValues,
                DictionaryMeaningCategories._ID + " =? ", new String[]{model.getCategoryId ()});
    }

    public static int deleteDictionaryWordMeaningCategory (GcmModel model) {
        return contentResolver.delete (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_MEANING_CATEGORY_URI + "/"
                + DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID), DictionaryMeaningCategories._ID + " =? ", new String[]{model.getCategoryId ()});
    }

 /*   public static int getAppCount () {

        String[] columns = {DataContract.AppList.COLUMN_NAME_APP_PACKAGE};

        Cursor cursor = contentResolver.query (Uri.parse (PREFIX + "/" + APP_URI),
                columns, null, null, null);

        assert cursor != null;
        int count = cursor.getCount ();
        cursor.close ();

        return count;
    }*/

    //:// TODO: 31/07/16  need to implement category id with enum

    public static ArrayList<DictionaryWordModel.Meaning> getWordMeaning (String word) {

        String[] columnsMeaning = {
                DictionaryMeanings._ID,
                DictionaryMeanings.COLUMN_NAME_MEANING,
                DictionaryMeanings.COLUMN_NAME_MEANING_USAGE,
                DictionaryMeanings.COLUMN_NAME_CATEGORY_ID


        };

        DictionaryWordModel.Word wordModel = getWordDetail (word);

        if (wordModel.getWordId () != null) {

            ArrayList<DictionaryWordModel.Meaning> mWordMeanings = new ArrayList<> ();


            Cursor cursor = contentResolver.query (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_MEANING_URI),
                    columnsMeaning, DictionaryMeanings.COLUMN_NAME_WORD_ID + " = ? ", new String[]{wordModel.getWordId ()}, null);

            assert cursor != null;
            //if (cursor.getCount () > 0)
            {
                cursor.moveToFirst ();
                while (cursor.moveToNext ()) {

                    DictionaryWordModel.Meaning mWordMeaning = new DictionaryWordModel.Meaning ();

                    mWordMeaning.setMeaningId (cursor.getString (cursor.getColumnIndexOrThrow (
                            DictionaryMeanings._ID)));
                    mWordMeaning.setMeaning (cursor.getString (cursor.getColumnIndexOrThrow (
                            DictionaryMeanings.COLUMN_NAME_MEANING)));
                    mWordMeaning.setMeaningUsage (cursor.getString (cursor.getColumnIndexOrThrow (
                            DictionaryMeanings.COLUMN_NAME_MEANING_USAGE)));
                    mWordMeaning.setCategoryId (cursor.getString (cursor.getColumnIndexOrThrow (
                            DictionaryMeanings.COLUMN_NAME_CATEGORY_ID)));

                    mWordMeanings.add (mWordMeaning);
                }
            }
            cursor.close ();
            return mWordMeanings;

        }

        return null;
    }

    private static DictionaryWordModel.Word getWordDetail (String word) {

        String[] columnsWord = {
                DictionaryWords._ID,
                DictionaryWords.COLUMN_NAME_PHONETIC,
                DictionaryWords.COLUMN_NAME_PHONETIC_SOUND
        };

        DictionaryWordModel.Word wordModel = new DictionaryWordModel.Word ();
        wordModel.setWord (word);
        Cursor cursor = contentResolver.query (Uri.parse (PREFIX + "/" + DICTIONARY_WORD_URI),
                columnsWord, DictionaryWords.COLUMN_NAME_WORD + " = ? ", new String[]{word}, null);

        assert cursor != null;
        if (cursor.moveToFirst ()) {
            wordModel.setWordId (cursor.getString (cursor.getColumnIndexOrThrow (
                    DictionaryWords._ID)));
            wordModel.setPhonetic (cursor.getString (cursor.getColumnIndexOrThrow (
                    DictionaryWords.COLUMN_NAME_PHONETIC)));
            wordModel.setPhoneticSound (cursor.getString (cursor.getColumnIndexOrThrow (
                    DictionaryWords.COLUMN_NAME_PHONETIC_SOUND)));
        }
        cursor.close ();

        return wordModel;
    }






   /* public static void updateAppActivieStatus (String appPackage, boolean isActive) {

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
    }*/

}
