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

    //:// TODO: 19/08/16 need to check run in backgroud from gcm
    public DownloadCompleteDictionaryService () {
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        requestCategories ();
        requestWords ();
        requestMeanings ();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    void requestCategories () {

        ApiInterface apiService =
                ApiClient.getClient ().create (ApiInterface.class);

        Call<DictionaryWordModel> call = apiService.getWordMeaningCategories ();
        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call, Response<DictionaryWordModel> response) {
                //insertWordResultInDB (response.body ().getWords ());
               // insertWordMeaningResultInDB (response.body ().getMeanings ());
                insertWordMeaningCategoryResultInDB (response.body ().getCategories ());

            }

            @Override
            public void onFailure (Call<DictionaryWordModel> call, Throwable t) {
                // Log error here since request failed
                Log.e (TAG, t.toString ());
            }
        });
    }

    void requestWords () {

        String start_id = Preferences.getNextDownloadWordId ();
        ApiInterface apiService =
                ApiClient.getClient ().create (ApiInterface.class);

        Call<DictionaryWordModel> call = apiService.getWords (start_id);
        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call, Response<DictionaryWordModel> response) {
                insertWordResultInDB (response.body ().getWords ());

                String nextId = response.body ().getNextId ();
                Preferences.setNextDownloadWordId (nextId);
                if (nextId != null) {
                    requestWords ();
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

    void requestMeanings () {

        final String[] start_id = {Preferences.getNextDownloadWordMeaningId ()};
        ApiInterface apiService =
                ApiClient.getClient ().create (ApiInterface.class);

        Call<DictionaryWordModel> call = apiService.getWordMeanings (start_id[0]);
        call.enqueue (new Callback<DictionaryWordModel> () {
            @Override
            public void onResponse (Call<DictionaryWordModel> call, Response<DictionaryWordModel> response) {
                insertWordMeaningResultInDB (response.body ().getMeanings ());

                start_id[0] = response.body ().getNextId ();
                Preferences.setNextDownloadWordMeaningId (start_id[0]);
                if (start_id[0] != null) {
                    requestMeanings ();
                }
                else if(null == Preferences.getNextDownloadWordId ()) {
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

    private void insertWordMeaningResultInDB (ArrayList<DictionaryWordModel.Meaning> wordMeanings) {

        for (DictionaryWordModel.Meaning wordMeaning : wordMeanings) {
            DataClient.insertDictionaryWordMeaning (wordMeaning);
        }
    }

    void insertWordResultInDB (ArrayList<DictionaryWordModel.Word> wordModelArrayList) {

        for (DictionaryWordModel.Word wordModel : wordModelArrayList) {
            DataClient.insertDictionaryWord (wordModel);
        }
    }

}
