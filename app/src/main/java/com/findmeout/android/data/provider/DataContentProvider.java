package com.findmeout.android.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.findmeout.android.Preferences;
import com.findmeout.android.data.provider.sql.DbHelper;

import java.util.List;

/**
 * Created by umesh0492 on 15/02/16.
 */

public class DataContentProvider extends ContentProvider {

    public static final int APP_LIST = 1;
    public static final int APP_LIST_ID = 2;
    public static final int DICTIONARY_WORD_LIST = 3;
    public static final int DICTIONARY_WORD_LIST_ID = 4;
    public static final int DICTIONARY_WORD_MEANING_LIST = 5;
    public static final int DICTIONARY_WORD_MEANING_LIST_ID = 6;
    public static final int DICTIONARY_WORD_MEANING_CATEGORY_LIST = 7;
    public static final int DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID = 8;
    public static final String APP_URI = "app_list";
    public static final String DICTIONARY_WORD_URI = "dictionary_word_list";
    public static final String DICTIONARY_WORD_MEANING_URI = "dictionary_word_meaning_list";
    public static final String DICTIONARY_WORD_MEANING_CATEGORY_URI = "dictionary_word_meaning_category_list";


    private static final UriMatcher uriMatcher = new UriMatcher (UriMatcher.NO_MATCH);
    private static final String AUTHORITY = DataContract.CONTENT_AUTHORITY;
    private static final String TAG = "DataContentProvider";

    static {

        uriMatcher.addURI (AUTHORITY, APP_URI, APP_LIST);
        uriMatcher.addURI (AUTHORITY, APP_URI + "/#", APP_LIST_ID);
        uriMatcher.addURI (AUTHORITY, DICTIONARY_WORD_URI, DICTIONARY_WORD_LIST);
        uriMatcher.addURI (AUTHORITY, DICTIONARY_WORD_URI + "/#", DICTIONARY_WORD_LIST_ID);
        uriMatcher.addURI (AUTHORITY, DICTIONARY_WORD_MEANING_URI, DICTIONARY_WORD_MEANING_LIST);
        uriMatcher.addURI (AUTHORITY, DICTIONARY_WORD_MEANING_URI + "/#", DICTIONARY_WORD_MEANING_LIST_ID);
        uriMatcher.addURI (AUTHORITY, DICTIONARY_WORD_MEANING_CATEGORY_URI, DICTIONARY_WORD_MEANING_CATEGORY_LIST);
        uriMatcher.addURI (AUTHORITY, DICTIONARY_WORD_MEANING_CATEGORY_URI + "/#", DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID);


    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate () {
        dbHelper = new DbHelper (getContext ());
        return true;
    }

    @Override
    public Cursor query (@NonNull Uri uri, String[] columns, String selection,
                         String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getWritableDatabase ();
        assert db != null;

        switch (uriMatcher.match (uri)) {

            case APP_LIST_ID:
                return db.query (DataContract.AppList.TABLE_NAME, columns, selection,
                        selectionArgs, null,
                        null, DataContract.AppList.COLUMN_NAME_APP_PACKAGE + " ASC",
                        "1");

            case APP_LIST:
                return db.query (DataContract.AppList.TABLE_NAME, columns, selection,
                        selectionArgs, null, null, null);

            /*case DICTIONARY_WORD_LIST_ID:
                return db.query (DataContract.DictionaryWords.TABLE_NAME, columns, selection,
                        selectionArgs, null, null, null);
*/
            case DICTIONARY_WORD_LIST:
                return db.query (DataContract.DictionaryWords.TABLE_NAME, columns, selection,
                        selectionArgs, null, null,null);

            case DICTIONARY_WORD_MEANING_LIST_ID:
                return db.query (DataContract.DictionaryMeanings.TABLE_NAME, columns, selection,
                        selectionArgs, null, null, null);

            case DICTIONARY_WORD_MEANING_LIST:
                return db.query (DataContract.DictionaryMeanings.TABLE_NAME, columns, selection,
                        selectionArgs, null, null, null);

            case DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID:
                return db.query (DataContract.DictionaryMeaningCategories.TABLE_NAME, columns, selection,
                        selectionArgs, null,
                        null, null);

            case DICTIONARY_WORD_MEANING_CATEGORY_LIST:
                return db.query (DataContract.DictionaryMeaningCategories.TABLE_NAME, columns, selection,
                        selectionArgs, null, null, null);

            default:
                throw new RuntimeException ("No content provider URI match.");
        }
    }

    @Override
    public String getType (@NonNull Uri uri) {

        switch (uriMatcher.match (uri)) {

            case APP_LIST:
                return "vnd.android.cursor.dir/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        APP_URI;

            case APP_LIST_ID:
                return "vnd.android.cursor.item/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        APP_URI;

            case DICTIONARY_WORD_LIST:
                return "vnd.android.cursor.dir/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        DICTIONARY_WORD_URI;

            case DICTIONARY_WORD_LIST_ID:
                return "vnd.android.cursor.item/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        DICTIONARY_WORD_URI;

            case DICTIONARY_WORD_MEANING_LIST:
                return "vnd.android.cursor.dir/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        DICTIONARY_WORD_MEANING_URI;

            case DICTIONARY_WORD_MEANING_LIST_ID:
                return "vnd.android.cursor.item/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        DICTIONARY_WORD_MEANING_URI;

            case DICTIONARY_WORD_MEANING_CATEGORY_LIST:
                return "vnd.android.cursor.dir/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        DICTIONARY_WORD_MEANING_CATEGORY_URI;

            case DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID:
                return "vnd.android.cursor.item/vnd." + DataContract.CONTENT_AUTHORITY + "." +
                        DICTIONARY_WORD_MEANING_CATEGORY_URI;

            default:
                throw new RuntimeException ("No content provider URI match.");
        }
    }

    @Override
    public Uri insert (@NonNull Uri uri, ContentValues contentValues) {
        List<String> segments;
        segments = uri.getPathSegments ();
        assert segments != null;
        long insert_id ;
        SQLiteDatabase db = dbHelper.getWritableDatabase ();

        switch (uriMatcher.match (uri)) {
            case APP_LIST_ID:
                try {
                    insert_id = db.insertOrThrow (DataContract.AppList.TABLE_NAME,
                            null, contentValues);
                } catch (SQLException ignored) {
                    insert_id = Long.parseLong (Preferences.getNextDownloadWordMeaningCategoryId ());
                }
                return Uri.parse ("content://" + AUTHORITY + "/" + APP_URI + "/"
                        + insert_id);

            case DICTIONARY_WORD_LIST_ID:
                try {
                    insert_id = db.insertOrThrow (DataContract.DictionaryWords.TABLE_NAME,
                            null, contentValues);
                } catch (SQLException ignored) {
                    insert_id = Long.parseLong (Preferences.getNextDownloadWordId ());
                }
                return Uri.parse ("content://" + AUTHORITY + "/" + DICTIONARY_WORD_URI + "/"
                        + insert_id);

            case DICTIONARY_WORD_MEANING_LIST_ID:
                try {
                    insert_id = db.insertOrThrow (DataContract.DictionaryMeanings.TABLE_NAME,
                            null, contentValues);
                } catch (SQLException ignored) {
                    insert_id = Long.parseLong (Preferences.getNextDownloadWordMeaningId ());
                }
                return Uri.parse ("content://" + AUTHORITY + "/" + DICTIONARY_WORD_MEANING_URI + "/"
                        + insert_id);

            case DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID:
                try {
                    insert_id = db.insertOrThrow (DataContract.DictionaryMeaningCategories.TABLE_NAME,
                            null, contentValues);
                } catch (SQLException ignored) {
                    insert_id = Long.parseLong (Preferences.getNextDownloadWordMeaningCategoryId ());
                }
                return Uri.parse ("content://" + AUTHORITY + "/" + DICTIONARY_WORD_MEANING_CATEGORY_URI + "/"
                        + insert_id);

            default:
                throw new RuntimeException ("No content provider URI match.");
        }
    }

    @Override
    public int delete (@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase ();
        assert db != null;

        switch (uriMatcher.match (uri)) {
            /*case DICTIONARY_LIST_ID:
                return db.delete (DataContract.Dictionary.TABLE_NAME, selection,
                        selectionArgs);

            case APP_LIST:
                db.delete (DataContract.AppList.TABLE_NAME, null, null);
                db.delete (DataContract.Dictionary.TABLE_NAME, null,
                        null);
                return 1;*/

            default:
                throw new RuntimeException ("No content provider URI match.");
        }
    }

    @Override
    public int update (@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {


        //:// TODO: 17/08/16 if update fail insert the values

        SQLiteDatabase db = dbHelper.getWritableDatabase ();
        assert db != null;

        switch (uriMatcher.match (uri)) {

            case APP_LIST_ID:
                return db.update (DataContract.AppList.TABLE_NAME, contentValues,
                        s, strings);

            case DICTIONARY_WORD_LIST_ID:
                return db.update (DataContract.DictionaryWords.TABLE_NAME, contentValues,
                        s, strings);

            case DICTIONARY_WORD_MEANING_LIST_ID:
                return db.update (DataContract.DictionaryMeanings.TABLE_NAME, contentValues,
                        s, strings);

            case DICTIONARY_WORD_MEANING_CATEGORY_LIST_ID:
                return db.update (DataContract.DictionaryMeaningCategories.TABLE_NAME, contentValues,
                        s, strings);
            default:
                throw new RuntimeException ("No content provider URI match.");
        }
    }

    /*private String buildSelection (String baseSelection, String selection) {
        if (TextUtils.isEmpty (selection)) {
            return baseSelection;
        }
        return TextUtils.concat (baseSelection, " AND (", selection, ")").toString ();
    }

    private String[] buildSelectionArgs (String[] baseArgs, String[] selectionArgs) {
        if (selectionArgs == null || selectionArgs.length == 0) {
            return baseArgs;
        }
        return ArrayUtils.addAll (baseArgs, selectionArgs);
    }*/

}
