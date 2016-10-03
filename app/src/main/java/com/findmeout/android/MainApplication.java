package com.findmeout.android;

import android.app.Application;

import com.findmeout.android.network.ApiClient;
import com.findmeout.android.network.ApiInterface;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by umesh0492 on 19/08/15.
 */
public class MainApplication extends Application {

    public static final String TAG = "FMO - ";

    public static ApiInterface apiService;

    @Override
    public void onCreate () {
        super.onCreate ();
        //com.facebook.stetho.Stetho.initializeWithDefaults (this);
        apiService = ApiClient.getClient ().create (ApiInterface.class);
        FlowManager.init (new FlowConfig.Builder (this).build ());
        //:// TODO: 21/09/16 DB backup
    }


}
