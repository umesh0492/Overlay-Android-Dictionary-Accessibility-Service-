package com.findmeout.android.network;

import com.findmeout.android.model.DictionaryWordModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by umesh0492 on 26/07/16.
 */
public interface ApiInterface {

    @GET("dictionary/meaning")
    Call<DictionaryWordModel> getWordMeaning(@Query("word") String word);

    @GET ("dictionary/{start_word_id}")
    Call<DictionaryWordModel> getWords(@Path ("start_word_id") String id);

}
