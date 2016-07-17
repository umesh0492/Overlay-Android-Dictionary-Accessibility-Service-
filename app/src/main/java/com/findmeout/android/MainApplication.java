package com.findmeout.android;

import android.app.Application;
import android.content.Context;

/**
 * Created by umesh0492 on 19/08/15.
 */
public class MainApplication extends Application {

    public static final String TAG = "Frainz - ";

    public static Context context;
    @Override
    public void onCreate () {
        super.onCreate ();
        context = getApplicationContext ();
    }


}
