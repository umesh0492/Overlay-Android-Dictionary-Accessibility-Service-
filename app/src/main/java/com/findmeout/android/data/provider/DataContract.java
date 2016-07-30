package com.findmeout.android.data.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.findmeout.android.BuildConfig;

/**
 * Created by umesh0492 on 15/02/16.
 */

public final class DataContract {
    // If the database schema changes, this version must be incremented.
    public static final int DATABASE_VERSION = 1;

    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = BuildConfig.content_provider;

    /**
     * Base URI. (content://com.example.android.network.sync.basicsyncadapter)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse ("content://" + CONTENT_AUTHORITY);

    /**
     * Path component for "entry"-type resources..
     */
    private static final String PATH_ENTRIES = "entries";

    private DataContract () {

    }

    public static abstract class AppList implements BaseColumns {
        public static final String TABLE_NAME = "app_list";
        public static final String COLUMN_NAME_APP_PACKAGE = "app_package";
        public static final String COLUMN_NAME_IS_ACTIVE = "is_active";
        public static final String COLUMN_NAME_UPDATED_ON = "updated_on";
    }

    public static abstract class Dictionary implements BaseColumns {
        public static final String TABLE_NAME = "dictionary";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_MEANINGS = "meanings";
        public static final String COLUMN_NAME_PRONUNCIATION = "pronunciation";
        public static final String COLUMN_NAME_PRONUNCIATION_SOUND = "pronunciation_sound";
        public static final String COLUMN_NAME_UPDATED_ON = "updated_on";
    }
}
