package com.findmeout.android.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.findmeout.android.Preferences;
import com.findmeout.android.R;
import com.findmeout.android.data.client.DataClient;
import com.findmeout.android.model.DictionaryWordModel;
import com.findmeout.android.network.ApiClient;
import com.findmeout.android.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadCompleteDictionaryService extends Service {

    private static final String TAG = DownloadCompleteDictionaryService.class.getSimpleName ();

    //:// TODO: 19/08/16 need to check run in backgroud from gcm
    public DownloadCompleteDictionaryService () {
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {

        if (null != Preferences.getNextDownloadWordMeaningCategoryId ()) {
            requestCategories ();
        }
        else if (null != Preferences.getNextDownloadWordId ()) {
            requestWords ();
        }
        else if (null != Preferences.getNextDownloadWordMeaningId ()) {
            requestMeanings ();
        }
        else {
            //stopSelf ();
            return START_NOT_STICKY;
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    void requestCategories () {

        showNotification (0);
        ApiInterface apiService =
                ApiClient.getClient ().create (ApiInterface.class);

        Call<DictionaryWordModel> call = apiService.getWordMeaningCategories ("0");
        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call,
                                    Response<DictionaryWordModel> response) {
                insertWordMeaningCategoryResultInDB (response.body ().getCategories ());
                Preferences.setNextDownloadWordMeaningCategoryId (null);
                requestWords ();
            }

            @Override
            public void onFailure (Call<DictionaryWordModel> call, Throwable t) {
                // Log error here since request failed
                Log.e (TAG, t.toString ());
            }
        });
    }

    void requestWords () {

        String nextId = Preferences.getNextDownloadWordId ();
        int nextIntID = Integer.parseInt (nextId);
        if (nextIntID < 1000) {
            showNotification (1);
        }
        else if (nextIntID < 5000) {
            showNotification (2);
        }
        else if (nextIntID < 10000) {
            showNotification (5);
        }
        else if (nextIntID < 20000) {
            showNotification (10);
        }
        else if (nextIntID < 40000) {
            showNotification (15);
        }
        else if (nextIntID < 60000) {
            showNotification (20);
        }
        else if (nextIntID < 80000) {
            showNotification (22);
        }
        else if (nextIntID < 100000) {
            showNotification (25);
        }


        Call<DictionaryWordModel> call = ApiClient.getClient ().create (ApiInterface.class)
                .getWords (nextId);

        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call,
                                    Response<DictionaryWordModel> response) {
                if (response.body ().getIsNext ()) {
                    Preferences.setNextDownloadWordId
                            (insertWordResultInDB (response.body ().getWords ()));
                    requestWords ();
                }
                else {
                    Preferences.setNextDownloadWordId (null);
                    showNotification (30);
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

        String nextId = Preferences.getNextDownloadWordMeaningId ();
        int nextIntID = Integer.parseInt (nextId);
        if (nextIntID < 1000) {
            showNotification (31);
        }
        else if (nextIntID < 5000) {
            showNotification (32);
        }
        else if (nextIntID < 10000) {
            showNotification (34);
        }
        else if (nextIntID < 20000) {
            showNotification (37);
        }
        else if (nextIntID < 40000) {
            showNotification (40);
        }
        else if (nextIntID < 60000) {
            showNotification (44);
        }
        else if (nextIntID < 80000) {
            showNotification (48);
        }
        else if (nextIntID < 100000) {
            showNotification (53);
        }
        else if (nextIntID > 130000) {
            showNotification (58);
        }
        else if (nextIntID > 130000) {
            showNotification (58);
        }
        else if (nextIntID > 160000) {
            showNotification (62);
        }
        else if (nextIntID > 180000) {
            showNotification (58);
        }
        else if (nextIntID > 240000) {
            showNotification (65);
        }
        else if (nextIntID > 300000) {
            showNotification (70);
        }
        else if (nextIntID > 400000) {
            showNotification (75);
        }
        else if (nextIntID > 500000) {
            showNotification (78);
        }
        else if (nextIntID > 700000) {
            showNotification (85);
        }
        else if (nextIntID > 900000) {
            showNotification (90);
        }
        else if (nextIntID > 1000000) {
            showNotification (95);
        }

        Call<DictionaryWordModel> call = ApiClient.getClient ().create (ApiInterface.class)
                .getWordMeanings (nextId);
        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call, Response<DictionaryWordModel> response) {

                if (response.body ().getIsNext ()) {
                    Preferences.setNextDownloadWordMeaningId
                            (insertWordMeaningResultInDB (response.body ().getMeanings ()));
                    requestMeanings ();
                }
                else {
                    Preferences.setNextDownloadWordMeaningId (null);
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


    private void insertWordMeaningCategoryResultInDB (ArrayList<DictionaryWordModel.Category> wordMeaningCategories) {

        for (DictionaryWordModel.Category wordMeaningCategory : wordMeaningCategories) {
            DataClient.insertDictionaryWordMeaningCategory (wordMeaningCategory);
        }
    }

    private String insertWordMeaningResultInDB (ArrayList<DictionaryWordModel.Meaning> wordMeanings) {

        String lastInsetId = null;

        for (DictionaryWordModel.Meaning wordMeaning : wordMeanings) {
            lastInsetId = DataClient.insertDictionaryWordMeaning (wordMeaning);
        }
        return lastInsetId;

    }

    private String insertWordResultInDB (ArrayList<DictionaryWordModel.Word> wordModelArrayList) {

        String lastInsetId = null;
        for (DictionaryWordModel.Word wordModel : wordModelArrayList) {
            lastInsetId = DataClient.insertDictionaryWord (wordModel);
        }

        return lastInsetId;
    }

    void showNotification (int progress) {
        final int id = 1;

        NotificationManager mNotifyManager =
                (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder (this);
        mBuilder.setContentTitle ("Downloading Dictionary")
                .setContentText ("Download in progress")
                .setSmallIcon (R.mipmap.ic_launcher);

        if (progress != 100) {
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
