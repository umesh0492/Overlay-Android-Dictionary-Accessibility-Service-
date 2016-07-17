package com.findmeout.android.service;

/**
 * Created by umesh0492 on 11/06/16.
 */

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.findmeout.android.R;

import java.util.Calendar;

public class ChatHeadService extends Service {
    private static final String TAG = "ChatHeadService";

    private WindowManager windowManager;
    private TextView closeImgBtn;
    private TextView chatHead;
    private WindowManager.LayoutParams params;
    private WindowManager.LayoutParams closeBtnParams;
    private Handler mPressHandler;
    private Intent data;

    private Handler mHandler;
    private Display mDisplay;
    private int mWidth;
    private int mHeight;

    private static final int MAX_CLICK_DURATION = 300;
    private long startClickTime;
    private boolean isInClose = false;
    private AlertDialog dialog;

    @Override
    public void onCreate () {
        super.onCreate ();

        windowManager = (WindowManager) getSystemService (WINDOW_SERVICE);
        mPressHandler = new Handler ();

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

        int chatHeadSize = (int) pxFromDp (this, 56);

        params = new WindowManager.LayoutParams (
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        Log.e (TAG,params.toString ());
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 20;
        params.y = 120;

        chatHead = new TextView (this);
        chatHead.setGravity (Gravity.CENTER);
        chatHead.setTypeface (Typeface.createFromAsset (getAssets (), "logo.ttf"));
        chatHead.setText (R.string.logo_ttf_string);
        chatHead.setTextColor (Color.RED);
        chatHead.setTextSize (24);
        chatHead.setBackground (getResources ().getDrawable (R.drawable.circle_bubble_btn));
        chatHead.setWidth (chatHeadSize);
        chatHead.setHeight (chatHeadSize);

        closeBtnParams = new WindowManager.LayoutParams (
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        closeImgBtn = new TextView (this);
        closeImgBtn.setBackground (getResources ().getDrawable (R.drawable.circle_with_transparent_background));
        closeImgBtn.setText ("x");
        closeImgBtn.setTextSize (22);
        closeImgBtn.setGravity (Gravity.CENTER);
        closeImgBtn.setTextColor (Color.WHITE);
        closeImgBtn.setWidth (chatHeadSize);
        closeImgBtn.setHeight (chatHeadSize);

        closeBtnParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;

        chatHead.setOnTouchListener (new View.OnTouchListener () {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch (View v, MotionEvent event) {
                switch (event.getAction ()) {
                    case MotionEvent.ACTION_DOWN:
                        chatHead.setBackground (getResources ().getDrawable (R.drawable.circle_bubble_btn_pressed));
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX ();
                        initialTouchY = event.getRawY ();
                        startClickTime = Calendar.getInstance ().getTimeInMillis ();
                        mPressHandler.postDelayed (new Runnable () {
                            @Override
                            public void run () {
                                windowManager.addView (closeImgBtn, closeBtnParams);
                            }
                        }, MAX_CLICK_DURATION);
                        return true;
                    case MotionEvent.ACTION_UP:
                        long clickDuration = Calendar.getInstance ().getTimeInMillis () - startClickTime;
                        chatHead.setBackground (getResources ().getDrawable (R.drawable.circle_bubble_btn));
                        if (isInClose) {
                            stopSelf ();
                        }
                        else if (clickDuration <= MAX_CLICK_DURATION) {

                            if (data != null) {
                                showScreenShotDialog ();
                            }
                            mPressHandler.removeCallbacksAndMessages (null);
                            return true;
                        }
                        windowManager.removeView (closeImgBtn);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (mWidth / 2 - mWidth / 5 < event.getRawX () &&
                                mWidth / 2 + mWidth / 5 > event.getRawX () &&
                                mHeight - mHeight / 5 < event.getRawY ()) {

                            int position[] = new int[2];
                            closeImgBtn.getLocationOnScreen (position);
                            params.x = position[0];
                            params.y = position[1];
                            isInClose = true;

                        }
                        else {
                            params.x = initialX + (int) (event.getRawX () - initialTouchX);
                            params.y = initialY + (int) (event.getRawY () - initialTouchY);
                            isInClose = false;
                        }
                        windowManager.updateViewLayout (chatHead, params);
                        return true;
                }
                return false;
            }
        });

        windowManager.addView (chatHead, params);

    }

    private void showScreenShotDialog () {

        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setMessage ("This will send current Screen Data to Redcarpet, continue ?");
        builder.setPositiveButton ("Screen Shot", new DialogInterface.OnClickListener () {
            @Override
            public void onClick (DialogInterface dialog, int which) {

                dialog.dismiss ();
                new Handler ().postDelayed (new Runnable () {
                    @Override
                    public void run () {


                    }
                }, 500);
            }
        });

        dialog = builder.create ();
        dialog.getWindow ().setType (WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show ();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras () != null) {
            data = (Intent) intent.getExtras ().get ("intentData");
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy () {
        super.onDestroy ();
        if (chatHead != null) {
            windowManager.removeView (chatHead);
        }

    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    public static float pxFromDp (final Context context, final float dp) {
        return dp * context.getResources ().getDisplayMetrics ().density;
    }

}
