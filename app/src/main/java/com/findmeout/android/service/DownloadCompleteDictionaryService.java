package com.findmeout.android.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.findmeout.android.MainApplication;
import com.findmeout.android.R;
import com.findmeout.android.model.DictionaryWordModel;
import com.findmeout.android.utils.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.findmeout.android.utils.Utils.insertWordMeaningCategoryResultInDB;
import static com.findmeout.android.utils.Utils.insertWordMeaningResultInDB;
import static com.findmeout.android.utils.Utils.insertWordResultInDB;

public class DownloadCompleteDictionaryService extends Service {

    private static final String TAG = DownloadCompleteDictionaryService.class.getSimpleName ();

    //:// TODO: 19/08/16 need to check run in backgroud from gcm
    public DownloadCompleteDictionaryService () {
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {

        if (!intent.hasExtra ("close")) {
            if (!"-1".equals (Preferences
                    .getNextDownloadWordMeaningCategoryId (getApplicationContext ()))) {
                requestCategories ();
            }
            else if (!"-1".equals (Preferences
                    .getNextDownloadWordId (getApplicationContext ()))) {
                showNotification (5);
                requestWords ();
            }
            else if (!"-1".equals (Preferences
                    .getNextDownloadWordMeaningId (getApplicationContext ()))) {
                showNotification (45);
                requestMeanings ();
            }
            else {
                //stopSelf ();
                return START_NOT_STICKY;
            }
            return START_STICKY;
        }
        else {
            stopSelf ();
            return START_NOT_STICKY;
        }

    }

    @Override
    public void onDestroy () {
        super.onDestroy ();

    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    void requestCategories () {

        showNotification (0);

        Call<DictionaryWordModel> call = MainApplication.apiService.getWordMeaningCategories ("0");
        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call,
                                    Response<DictionaryWordModel> response) {
                insertWordMeaningCategoryResultInDB (response.body ().getCategories ());
                Preferences.setNextDownloadWordMeaningCategoryId ("-1",getApplicationContext ());
                requestWords ();
            }

            @Override
            public void onFailure (Call<DictionaryWordModel> call, Throwable t) {
                // Log error here since request failed
                Log.e (TAG, t.toString ());

                //:// TODO: 20/09/16 handle no internet connection
            }
        });
    }

    void requestWords () {

        String nextId = Preferences.getNextDownloadWordId (getApplicationContext ());
        int nextIntID = Integer.parseInt (nextId);
        if (nextIntID < 1000) {
            showNotification (5);
        }
        else if (nextIntID < 5000) {
            showNotification (10);
        }
        else if (nextIntID < 10000) {
            showNotification (15);
        }
        else if (nextIntID < 20000) {
            showNotification (20);
        }
        else if (nextIntID < 40000) {
            showNotification (25);
        }
        else if (nextIntID < 60000) {
            showNotification (30);
        }
        else if (nextIntID < 80000) {
            showNotification (35);
        }
        else if (nextIntID < 100000) {
            showNotification (40);
        }

        Call<DictionaryWordModel> call = MainApplication.apiService.getWords (nextId);

        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call,
                                    Response<DictionaryWordModel> response) {

                insertWordResultInDB (response.body ().getWords ());

                if (response.body ().getIsNext ()) {
                    Preferences.setNextDownloadWordId (
                            (String.valueOf (response.body ().getWords ()
                                    .get (response.body ().getWords ().size ()-1)
                                    .getId ())),getApplicationContext ());
                    requestWords ();
                }
                else {
                    Preferences.setNextDownloadWordId ("-1",getApplicationContext ());
                    showNotification (45);
                    requestMeanings ();
                }
            }

            @Override
            public void onFailure (Call<DictionaryWordModel> call, Throwable t) {
                // Log error here since request failed
                Log.e (TAG, t.toString ());
            }
        });
    }

    void requestMeanings () {

        String nextId = Preferences.getNextDownloadWordMeaningId (getApplicationContext ());
        int nextIntID = Integer.parseInt (nextId);
        if (nextIntID < 10000) {
            showNotification (50);
        }
        else if (nextIntID < 40000) {
            showNotification (60);
        }
        else if (nextIntID < 60000) {
            showNotification (70);
        }
        else if (nextIntID < 80000) {
            showNotification (80);
        }
        else if (nextIntID < 100000) {
            showNotification (90);
        }
        else if (nextIntID > 120000) {
            showNotification (95);
        }
        else if (nextIntID > 140000) {
            showNotification (99);
        }

        Call<DictionaryWordModel> call = MainApplication.apiService.getWordMeanings (nextId);

        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call, Response<DictionaryWordModel> response) {

                insertWordMeaningResultInDB (response.body ().getMeanings ());

                if (response.body ().getIsNext ()) {
                    Preferences.setNextDownloadWordMeaningId
                            (String.valueOf (response.body ().getMeanings ()
                                    .get (response.body ().getMeanings ().size ()-1)
                                    .getId ()),getApplicationContext ());

                    requestMeanings ();
                }
                else {
                    Preferences.setNextDownloadWordMeaningId ("-1",getApplicationContext ());
                    showNotification (100);
                    stopSelf ();
                }
            }

            @Override
            public void onFailure (Call<DictionaryWordModel> call, Throwable t) {
                // Log error here since request failed
                Log.e (TAG, t.toString ());
            }
        });
    }

    void showNotification (int progress) {
        final int id = 1;

        Intent intent = new Intent ("download_dictionary_intent");
        // You can also include some extra data.
        intent.putExtra ("progress", progress);
        LocalBroadcastManager.getInstance (this).sendBroadcast (intent);

        NotificationManager mNotifyManager =
                (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder (this);
        mBuilder.setContentTitle ("Downloading Dictionary")
                .setContentText ("Download in progress")
                .setSmallIcon (R.mipmap.ic_launcher)
                .setContentInfo (progress + "%");

        if (progress != 100) {
            //mBuilder.setAutoCancel (false);
            //mBuilder.setOngoing (true);
            mBuilder.setProgress (100, progress, false);
            // Displays the progress bar for the first time.
            mNotifyManager.notify (id, mBuilder.build ());
        }
        else {
            mBuilder.setContentText ("Download complete")
                    // Removes the progress bar
                    .setProgress (0, 0, false);
            mNotifyManager.notify (id, mBuilder.build ());
        }
/*// Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            mNotifyManager.notify(id, mBuilder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(5*1000);
                            } catch (InterruptedException e) {
                                //Log.d(TAG, "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        mNotifyManager.notify(id, mBuilder.build());
                    }
                }
// Starts the thread by calling the run() method in its Runnable
        ).start();*/
    }
}
