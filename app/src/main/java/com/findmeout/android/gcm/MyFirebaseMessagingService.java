package com.findmeout.android.gcm;

/**
 * Created by umesh0492 on 12/03/16.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.findmeout.android.MainApplication;
import com.findmeout.android.R;
import com.findmeout.android.model.GcmModel;
import com.findmeout.android.ui.MainActivity;
import com.findmeout.android.utils.Mapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived (RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d (TAG, "From: " + remoteMessage.getFrom ());

        // Check if message contains a data payload.

        GcmModel model = null;
        if (remoteMessage.getData ().size () > 0) {

            model = Mapper.object (remoteMessage.getData ().get ("data"), GcmModel.class);

            if (null != model) {

                // actAccordingToTarget (model);

            }
            Log.d (TAG, "Message data payload: " + remoteMessage.getData ());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification () != null) {
            Log.d (TAG, "Message Notification Body: " + remoteMessage.getNotification ().getBody ());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

/*
    private void actAccordingToTarget (GcmModel model) {

        switch (model.getType ()) {

            case 0:

                if (null == Preferences.getNextDownloadWordMeaningCategoryId ()) {
                    DictionaryWordModel.Category category = new DictionaryWordModel.Category ();
                    category.setCategoryID (model.getCategoryId ());
                    category.setCategoryName (model.getCategoryName ());
                    category.setUpdatedOn (model.getUpdatedOn ());

                    DataClient.insertDictionaryWordMeaningCategory (category);
                }
                else {
                    startService (new Intent (MainApplication.context, DownloadCompleteDictionaryService.class));
                }

                break;

            case 1:
                DataClient.updateDictionaryWordMeaningCategory (model);

                break;

            case 2:
                DataClient.deleteDictionaryWordMeaningCategory (model);

                break;

            case 3:

                if (null == Preferences.getNextDownloadWordMeaningId ()) {
                    DictionaryWordModel.Meaning meaning = new DictionaryWordModel.Meaning ();
                    meaning.setCategoryId (model.getCategoryId ());
                    meaning.setMeaning (model.getMeaning ());
                    meaning.setMeaningId (model.getMeaningId ());
                    meaning.setMeaningUsage (model.getMeaningUsage ());
                    meaning.setWordId (model.getWordId ());
                    meaning.setUpdatedOn (model.getUpdatedOn ());

                    DataClient.insertDictionaryWordMeaning (meaning);
                }
                else {
                    startService (new Intent (MainApplication.context, DownloadCompleteDictionaryService.class));
                }
                break;

            case 4:
                DataClient.updateDictionaryWordMeaning (model);

                break;

            case 5:
                DataClient.deleteDictionaryWordMeaning (model);

                break;

            case 6:
                if (null == Preferences.getNextDownloadWordId ()) {
                    DictionaryWordModel.Word word = new DictionaryWordModel.Word ();
                    word.setWord (model.getWord ());
                    word.setWordId (model.getWordId ());
                    word.setPhonetic (model.getPhonetic ());
                    word.setPhoneticSound (model.getPhoneticSound ());
                    word.setUpdatedOn (model.getUpdatedOn ());

                    DataClient.insertDictionaryWord (word);
                }
                else {
                    startService (new Intent (MainApplication.context, DownloadCompleteDictionaryService.class));
                }
                break;

            case 7:
                DataClient.updateDictionaryWord (model);
                break;

            case 8:
                DataClient.deleteDictionaryWord (model);

                break;

            case 9:
                showSimpleNotification (model);
                break;
        }
    }
*/
    // [END receive_message]


    private void showSimpleNotification (GcmModel message) {

        //:// TODO: 19/08/16 need to handle opens webview
        Intent intent = new Intent (this, MainActivity.class);
        intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity (this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        if (null != message.getUrl ()) {
            intent.putExtra ("url", message.getUrl ());
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri (RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder (this)
                .setSmallIcon (R.mipmap.ic_launcher)
                .setLargeIcon (BitmapFactory.decodeResource
                        (MainApplication.context.getResources (), R.mipmap.ic_launcher))
                .setContentTitle (message.getTitle ())
                .setContentText (message.getMessage ())
                //.setTicker (message.getTicker())
                .setAutoCancel (true)
                .setSound (defaultSoundUri)
                .setContentIntent (pendingIntent)
                .setDefaults (Notification.DEFAULT_ALL);

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);

        String imageUrl = message.getImageUrl ();
        if (!imageUrl.isEmpty ()) {
            try {
                Bitmap webImage = BitmapFactory
                        .decodeStream ((InputStream) new URL (imageUrl).getContent ());
                NotificationCompat.BigPictureStyle big = new NotificationCompat.BigPictureStyle (notificationBuilder);
                big.bigPicture (webImage);
                mNotifyMgr.notify (0, big.build ());

            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
        else {
            NotificationCompat.BigTextStyle big = new NotificationCompat.BigTextStyle (notificationBuilder);
            big.bigText (message.getMessage ());
            mNotifyMgr.notify (0, big.build ());
        }

    }
}

