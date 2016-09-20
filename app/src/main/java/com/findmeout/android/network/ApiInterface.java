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

    @GET("words/{word}")
    Call<DictionaryWordModel> getWordMeaning(@Path ("word") String word);

    @GET ("words")
    Call<DictionaryWordModel> getWords(@Query ("id") String nexId);

    @GET ("meanings")
    Call<DictionaryWordModel> getWordMeanings(@Query ("id") String nexId);

    @GET ("categories")
    Call<DictionaryWordModel> getWordMeaningCategories(@Query ("id") String nexId);

}
