package com.findmeout.android;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.findmeout.android.network.ApiClient;
import com.findmeout.android.network.ApiInterface;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by umesh0492 on 19/08/15.
 */
public class MainApplication extends Application {

    public static final String TAG = "FMO - ";

    public static Context context;

    public static ApiInterface apiService;

    @Override
    public void onCreate () {
        super.onCreate ();
        context = getApplicationContext ();
        com.facebook.stetho.Stetho.initializeWithDefaults (this);
        apiService = ApiClient.getClient ().create (ApiInterface.class);
      /*  // [START subscribe_topics]
        FirebaseMessaging.getInstance ().subscribeToTopic ("news");
        Log.d (TAG, "Subscribed to news topic");
        Log.d (TAG, "InstanceID token: " + FirebaseInstanceId.getInstance ().getToken ());
*/
        //DataClient.insertInitApp ();

        FlowManager.init (new FlowConfig.Builder (this).build ());

        //:// TODO: 21/09/16 DB backup


    }

    @Override
    protected void attachBaseContext (Context base) {
        super.attachBaseContext (base);
        MultiDex.install (this);
    }


}
