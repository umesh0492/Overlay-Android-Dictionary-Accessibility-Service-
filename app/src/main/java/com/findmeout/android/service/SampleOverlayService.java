package com.findmeout.android.service;

/**
 * Created by umesh0492 on 13/06/16.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import com.findmeout.android.OverlayService;
import com.findmeout.android.R;
import com.findmeout.android.SampleOverlayView;
import com.findmeout.android.SettingsActivity;

public class SampleOverlayService extends OverlayService {

    public static SampleOverlayService instance;

    private SampleOverlayView overlayView;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        overlayView = new SampleOverlayView (this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (overlayView != null) {
            overlayView.destory();
        }

    }

    static public void stop() {
        if (instance != null) {
            instance.stopSelf();
        }
    }

    @Override
    protected Notification foregroundNotification(int notificationId) {
        Notification notification;

        notification = new Notification(R.mipmap.ic_launcher, getString(R.string.title_notification), System.currentTimeMillis());

        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT | Notification.FLAG_ONLY_ALERT_ONCE;

        //notification.setLatestEventInfo(this, getString(R.string.title_notification), getString(R.string.message_notification), notificationIntent());

        return notification;
    }


    private PendingIntent notificationIntent() {
        Intent intent = new Intent(this, SettingsActivity.class);

        PendingIntent pending = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pending;
    }

}
