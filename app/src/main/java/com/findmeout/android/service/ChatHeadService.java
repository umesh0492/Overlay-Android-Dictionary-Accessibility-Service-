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
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.findmeout.android.R;

public class ChatHeadService extends Service {
    private static final String TAG = "ChatHeadService";

    private WindowManager windowManager;
    private View viewOverlay;
    private LayoutInflater layoutInflater;
    private WindowManager.LayoutParams params;

    private Handler mHandler;
    private Display mDisplay;
    TextView tvWord;

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

        params = new WindowManager.LayoutParams (
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        Log.e (TAG, params.toString ());
        params.x = 20;
        params.y = 120;

        viewOverlay = layoutInflater.inflate (R.layout.overlay, null);

        tvWord = (TextView) viewOverlay.findViewById (R.id.word);

       /* if (word.trim ().length () > 0) {
            tvWord.setText (word);
        }*/

        viewOverlay.findViewById (R.id.close).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                word = "";
                sentence = "";
                stopSelf ();
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
            }
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
