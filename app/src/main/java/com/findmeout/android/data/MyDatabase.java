package com.findmeout.android.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by umesh0492 on 27/09/16.
 */

@Database (name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {

    public static final String NAME = "FMO";

    public static final int VERSION = 1;

}
