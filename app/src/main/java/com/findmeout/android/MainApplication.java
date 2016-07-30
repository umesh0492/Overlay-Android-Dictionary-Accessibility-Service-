package com.findmeout.android;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.findmeout.android.data.client.DataClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by umesh0492 on 19/08/15.
 */
public class MainApplication extends Application {

    public static final String TAG = "FMO - ";

    public static Context context;

    @Override
    public void onCreate () {
        super.onCreate ();
        context = getApplicationContext ();

        // [START subscribe_topics]
        FirebaseMessaging.getInstance ().subscribeToTopic ("news");
        Log.d (TAG, "Subscribed to news topic");
        Log.d (TAG, "InstanceID token: " + FirebaseInstanceId.getInstance ().getToken ());

        DataClient.insertInitApp ();

    }

    @Override
    protected void attachBaseContext (Context base) {
        super.attachBaseContext (base);
        MultiDex.install (this);
    }


}
