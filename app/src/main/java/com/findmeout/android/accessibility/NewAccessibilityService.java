package com.findmeout.android.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.findmeout.android.service.ChatHeadService;

/**
 * Created by umesh0492 on 30/12/15.
 */
public class NewAccessibilityService extends android.accessibilityservice.AccessibilityService {

    private static final String TAG = "MyAccessibilityService";

    String[] packages = {"com.medium.reader"};

    @Override
    public void onAccessibilityEvent (AccessibilityEvent event) {

        Log.d (TAG, "Event Type : " + event.getEventType ());
        Log.d (TAG, "package " + event.getPackageName ().toString ());

        Log.d (TAG, "ContentDescription " + event.getText ());

        //Log.d (TAG,"action "+event.getSource ().getTextSelectionStart ()+"");

        try {
            String sentence = event.getText ().get (0).toString ();
            String word = sentence.subSequence (event.getSource ().getTextSelectionStart (),
                    event.getSource ().getTextSelectionEnd ()).toString ();
            if (word.trim ().length () > 0) {

                //:// TODO: 14/06/16  write regex for spaces at both the ends and only alphabets

                Intent in = new Intent (NewAccessibilityService.this, ChatHeadService.class);

                if (word.trim ().matches ("([A-za-z]*)")) {

                    in.putExtra ("word", word);
                    in.putExtra ("sentence", sentence);

                }
                else {
                    in.putExtra ("word", "");
                    in.putExtra ("sentence", "");

                }

                startService (in);

            }

            Log.d (TAG, "word : " + word);

        } catch (Exception e) {

            e.printStackTrace ();
        }
        //Log.d (TAG,"ContentDescription1 "+event.getSource ().getParent ()+"");


        //event.getAction ()
    }

    @Override
    public void onInterrupt () {

    }

    @Override
    protected void onServiceConnected () {
        Log.d (TAG, "Accessibility Service Connected..");

        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo ();

        accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED;// works for medium

        //accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        //accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_VIEW_SELECTED;
        //accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_VIEW_FOCUSED;
        //accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_VIEW_HOVER_EXIT;
        //accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END;
        //accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED;
        //accessibilityServiceInfo.eventTypes |= AccessibilityEvent.TYPE_TOUCH_INTERACTION_END;

        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.DEFAULT;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
        //accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;

        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FEEDBACK_ALL_MASK;

        accessibilityServiceInfo.packageNames = packages;
        setServiceInfo (accessibilityServiceInfo);

        super.onServiceConnected ();

    }

    public int onStartCommand (Intent intent, int i, int i2) {
        super.onStartCommand (intent, i, i2);
        return 1;
    }
}
