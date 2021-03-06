package com.findmeout.android.service;

/**
 * Created by umesh0492 on 11/06/16.
 */

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.findmeout.android.MainApplication;
import com.findmeout.android.R;
import com.findmeout.android.data.tables.Categories;
import com.findmeout.android.data.tables.Categories_Table;
import com.findmeout.android.data.tables.Meanings;
import com.findmeout.android.data.tables.Meanings_Table;
import com.findmeout.android.data.tables.Words;
import com.findmeout.android.data.tables.Words_Table;
import com.findmeout.android.model.DictionaryWordModel;
import com.findmeout.android.utils.Utils;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHeadService extends Service implements TextToSpeech.OnInitListener {
    private static final String TAG = "ChatHeadService";
    TextView tvWord;
    TextView tvWordType;
    TextView tvMeaning;
    ImageView tts_button;
    String sentence = "";
    String word = "";
    boolean requestingServer = false;
    private WindowManager windowManager;
    private View viewOverlay;
    private LayoutInflater layoutInflater;
    private WindowManager.LayoutParams params;
    private Handler mHandler;
    private Display mDisplay;
    private int mWidth;
    private int mHeight;

    private TextToSpeech tts;
    Call<DictionaryWordModel> call;

    @Override
    public void onCreate () {
        super.onCreate ();
        tts = new TextToSpeech (this, this);
        windowManager = (WindowManager) getSystemService (WINDOW_SERVICE);
        layoutInflater = (LayoutInflater) getSystemService (LAYOUT_INFLATER_SERVICE);

        new Thread () {
            @Override
            public void run () {
                Looper.prepare ();
                mHandler = new Handler ();
                Looper.loop ();
            }
        }.start ();

        mDisplay = windowManager.getDefaultDisplay ();
        Point size = new Point ();
        mDisplay.getSize (size);
        mWidth = size.x;
        mHeight = size.y;

        params = new WindowManager.LayoutParams (
                mWidth,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Log.e (TAG, params.toString ());
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        viewOverlay = layoutInflater.inflate (R.layout.overlay, null);

        tvWord = (TextView) viewOverlay.findViewById (R.id.word);
        tvMeaning = (TextView) viewOverlay.findViewById (R.id.meaning);
        tvWordType = (TextView) viewOverlay.findViewById (R.id.word_type);
        tts_button = (ImageView) viewOverlay.findViewById (R.id.tts_button);

        viewOverlay.findViewById (R.id.close).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                word = "";
                sentence = "";
                stopSelf ();
            }
        });

        tts_button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (!TextUtils.isEmpty (word)) {
                    speakOut ();
                }
            }
        });

        viewOverlay.setOnTouchListener (new View.OnTouchListener () {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch (View v, MotionEvent event) {
                //Log.e (TAG, event.getAction () + "");
                switch (event.getAction ()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX ();
                        initialTouchY = event.getRawY ();
                        return true;
                    case MotionEvent.ACTION_UP:

                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX ();
                        initialTouchY = event.getRawY ();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                       /* if (mWidth / 2 - mWidth / 5 < event.getRawX () &&
                                mWidth / 2 + mWidth / 5 > event.getRawX () &&
                                mHeight - mHeight / 5 < event.getRawY ()) {

                            params.x = initialX - (int) (event.getRawX () - initialTouchX);
                            params.y = initialY - (int) (event.getRawY () - initialTouchY);
                        }
                        else {*/
                        params.x = initialX - (int) (event.getRawX () - initialTouchX);
                        params.y = initialY - (int) (event.getRawY () - initialTouchY);
                        //}
                        windowManager.updateViewLayout (viewOverlay, params);
                        return true;
                }
                return false;
            }
        });
        windowManager.addView (viewOverlay, params);
    }


    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {

        if (intent.hasExtra ("word")) {
            if (!word.equalsIgnoreCase (intent.getStringExtra ("word").trim ())) {
                word = intent.getStringExtra ("word").trim ();
                sentence = intent.getStringExtra ("sentence");

                if (null != call) {
                    call.cancel ();
                }
                    showMeaning (word);

                tvWordType.setVisibility (View.GONE);
            }
        }
        else {
            stopSelf ();
        }
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    void showMeaning (String word) {
            if (tvWord != null) {
                tvWord.setText (word);

                // regex to check for s  and matching words
                if (word.trim ().matches ("([a-zA-Z]+?)(s\b|\b)")) {
                    Log.e (TAG, "matched");
                }

                // :// TODO: 27/09/16 implement joins
                Words wordId = new Select ().from (Words.class)
                        .where (Words_Table.word.is (word))
                        .querySingle ();
                if (null != wordId) {
                    Log.e (TAG,"word ava");
                    Meanings meanings = new Select ().from (Meanings.class)
                            .where (Meanings_Table.wordId.is (wordId.getId ()))
                            .querySingle ();

                    if (null != meanings) {
                        tvMeaning.setText (meanings.getMeaning ());
                        Categories category = new Select ().from (Categories.class)
                                .where (Categories_Table.id.is (meanings.getCategoryId ()))
                                .querySingle ();
                        speakOut ();
                        assert category != null;
                        if (!category.getCategoryName ().equals ("none")) {
                            tvWordType.setVisibility (View.VISIBLE);
                            tvWordType.setText (category.getCategoryName ());
                        }
                        else {
                            tvWordType.setVisibility (View.GONE);
                        }
                    }
                    else {
                        requestMeaning (word);
                    }
                }

                else {
                    //tvWordType.setVisibility (View.GONE);
                    requestMeaning (word);
                }
            }
    }

    void requestMeaning (final String word) {

        tvMeaning.setText ("Requesting meaning ...");

        if (!requestingServer) {
            requestingServer = true;
            call = MainApplication.apiService.getWordMeaning (word);

            call.enqueue (new Callback<DictionaryWordModel> () {

                @Override
                public void onResponse (Call<DictionaryWordModel> call,
                                        Response<DictionaryWordModel> response) {

                    requestingServer = false;

                    try {
                        Utils.insertWordResultInDB (response.body ().getWords ());
                        Utils.insertWordMeaningCategoryResultInDB (response.body ().getCategories ());
                        Utils.insertWordMeaningResultInDB (response.body ().getMeanings ());

                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                    showMeaning (word);
                }

                @Override
                public void onFailure (Call<DictionaryWordModel> call, Throwable t) {

                    requestingServer = false;

                    tvMeaning.setText ("Word doesn't exist in our database");
                    //Log.e (TAG, t.toString ());
                }
            });
        }
    }

    @Override
    public void onDestroy () {

        if (viewOverlay != null) {
            windowManager.removeView (viewOverlay);
        }

        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop ();
            tts.shutdown ();
        }
        super.onDestroy ();
    }

    /**
     * Called to signal the completion of the TextToSpeech engine initialization.
     *
     * @param status {@link TextToSpeech#SUCCESS} or {@link TextToSpeech#ERROR}.
     */
    @Override
    public void onInit (int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage (Locale.US);
            tts.setSpeechRate (0.7f);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e ("TTS", "This Language is not supported");
            }
            else {
                tts_button.setEnabled (true);
            }

        }
        else {
            Log.e ("TTS", "Initilization Failed!");
        }
    }

    private void speakOut () {

        tts.speak (word, TextToSpeech.QUEUE_FLUSH, null);
    }

}
