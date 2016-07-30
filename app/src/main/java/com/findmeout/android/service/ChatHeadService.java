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

import com.findmeout.android.R;
import com.findmeout.android.data.client.DataClient;
import com.findmeout.android.model.DictionaryWordModel;

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
                Log.e (TAG,event.getAction ()+"");
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
        word = intent.getStringExtra ("word");
        sentence = intent.getStringExtra ("sentence");
        if (word.trim ().length () > 0) {
            if (tvWord != null) {
                tvWord.setText (word);

                DictionaryWordModel.Word wordMeaning= DataClient.getWordMeaning (word);
                if(wordMeaning != null)
                {
                    tvMeaning.setText (wordMeaning.getMeaning_1 ());
                    tvWordType.setText (wordMeaning.getPronunciation ());
                }
                else {
                    tvMeaning.setText ("Can't find the meaning");
                }
            }
        }else {
            stopSelf ();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
        if (viewOverlay != null) {
            windowManager.removeView (viewOverlay);
        }

    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

}
