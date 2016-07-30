package com.findmeout.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.findmeout.android.Preferences;
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

    public DownloadCompleteDictionaryService () {
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        requestServer (Preferences.getNextDownloadWordId ());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    void requestServer (String start_id) {

        ApiInterface apiService =
                ApiClient.getClient ().create (ApiInterface.class);

        Call<DictionaryWordModel> call = apiService.getWords (start_id);
        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call, Response<DictionaryWordModel> response) {
                ArrayList<DictionaryWordModel.Word> words = response.body ().getWords ();
                Log.d (TAG, "Number of words received: " + words.size ());
                insertWordResultInDB (words);
                String nextId = response.body ().getNextId ();
                Preferences.setNextDownloadWordId (nextId);
                if (nextId != null) {
                    requestServer (nextId);
                }
                else {
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

    void insertWordResultInDB (ArrayList<DictionaryWordModel.Word> wordModelArrayList) {

        for (DictionaryWordModel.Word wordModel : wordModelArrayList) {
            DataClient.insertDictionary (wordModel);
        }
    }

}
