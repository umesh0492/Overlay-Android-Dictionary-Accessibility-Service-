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
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.findmeout.android.MainApplication;
import com.findmeout.android.R;
import com.findmeout.android.data.client.DataClient;
import com.findmeout.android.model.DictionaryWordModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHeadService extends Service {
    private static final String TAG = "ChatHeadService";

    private WindowManager windowManager;
    private View viewOverlay;
    private LayoutInflater layoutInflater;
    private WindowManager.LayoutParams params;

    private Handler mHandler;
    private Display mDisplay;
    private int mWidth;
    private int mHeight;
    TextView tvWord;
    TextView tvWordType;
    TextView tvMeaning;
    String sentence = "";
    String word = "";

    boolean requestingServer = false;

    @Override
    public void onCreate () {
        super.onCreate ();

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

        Log.e (TAG, params.toString ());
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        viewOverlay = layoutInflater.inflate (R.layout.overlay, null);

        tvWord = (TextView) viewOverlay.findViewById (R.id.word);
        tvMeaning = (TextView) viewOverlay.findViewById (R.id.meaning);
        tvWordType = (TextView) viewOverlay.findViewById (R.id.word_type);

        viewOverlay.findViewById (R.id.close).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                word = "";
                sentence = "";
                stopSelf ();
            }
        });

        viewOverlay.setOnTouchListener (new View.OnTouchListener () {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch (View v, MotionEvent event) {
                Log.e (TAG, event.getAction () + "");
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
                showMeaning (word);
            }
        }
        else {
            stopSelf ();
        }
        return START_NOT_STICKY;
    }

    void showMeaning (String word) {
        if (word.length () > 0) {
            if (tvWord != null) {
                String firstLetter = word.substring (0, 1);
                word = word.toLowerCase ();
                word = word.replace (firstLetter, firstLetter.toUpperCase ());

                tvWord.setText (word);

                ArrayList<DictionaryWordModel.Meaning> wordMeaning = DataClient.getWordMeaning (word);

                if (null != wordMeaning) {
                    tvMeaning.setText (wordMeaning.get (0).getMeaning ());

                    String categoryName
                            = DataClient.getCategoryName (wordMeaning.get (0).getCategoryId ());
                    if (!categoryName.equals ("none")) {
                        tvWordType.setVisibility (View.VISIBLE);
                        tvWordType.setText (categoryName);
                    }
                    else {
                        tvWordType.setVisibility (View.GONE);
                    }
                }
                else {
                    //tvWordType.setVisibility (View.GONE);
                    requestMeaning (word);
                    tvMeaning.setText ("Requesting meaning");
                }
            }
        }
        else {
            requestMeaning (word);
        }
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
        if (viewOverlay != null) {
            windowManager.removeView (viewOverlay);
        }

    }

    void requestMeaning (final String word) {

        if (!requestingServer) {
            requestingServer = true;
            Call<DictionaryWordModel> call = MainApplication.apiService.getWordMeaning (word);

            call.enqueue (new Callback<DictionaryWordModel> () {

                @Override
                public void onResponse (Call<DictionaryWordModel> call,
                                        Response<DictionaryWordModel> response) {

                    requestingServer = false;

                    try {

                        DataClient.insertDictionaryWord (response.body ().getWords ().get (0));

                        for (DictionaryWordModel.Category wordMeaning : response.body ().getCategories ()) {
                            DataClient.insertDictionaryWordMeaningCategory (wordMeaning);
                        }

                        for (DictionaryWordModel.Meaning wordMeaning : response.body ().getMeanings ()) {
                            DataClient.insertDictionaryWordMeaning (wordMeaning);
                        }
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                    showMeaning (word);
                }

                @Override
                public void onFailure (Call<DictionaryWordModel> call, Throwable t) {

                    requestingServer = false;

                    tvMeaning.setText ("Word doesn't exist in our database");
                    Log.e (TAG, t.toString ());
                }
            });
        }
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

}
