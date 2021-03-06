package com.findmeout.android.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

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

        String pck = event.getPackageName ().toString ().trim ();

        if (pck.equals (packages[0])) {

            if (event.getEventType () == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
                onMedium (event);
            }
        }
        else {
            closeChatHead ();
            //:// TODO: 20/09/16 close chat head service
        }

        /*if(pck.equals (packages[1])){
            onQuora(event);
        }
*/

        //Log.d (TAG,"ContentDescription1 "+event.getSource ().getParent ()+"");


        //event.getAction ()
    }

    void onMedium (AccessibilityEvent event) {
        try {

            Log.d (TAG, event.getSource ().getViewIdResourceName () + "");
            Log.d (TAG, "action " + event.getSource ().getTextSelectionStart () + "");

            String sentence = event.getText ().get (0).toString ();
            String word = sentence.subSequence (event.getSource ().getTextSelectionStart (),
                    event.getSource ().getTextSelectionEnd ()).toString ();
            if (word.trim ().length () > 0) {

                //:// TODO: 14/06/16  write regex for spaces at both the ends and only alphabets

                Intent in = new Intent (NewAccessibilityService.this, ChatHeadService.class);

                if (word.trim ().matches ("([A-za-z]*)")) {

                    String firstLetter = word.substring (0, 1);
                    word = word.toLowerCase ();
                    word = word.replaceFirst (firstLetter, firstLetter.toUpperCase ());

                    in.putExtra ("word", word);
                    in.putExtra ("sentence", sentence);
                    startService (in);

                }
                else {
stopService (in);
                }


            }

            // Log.d (TAG, "word : " + word);

        } catch (Exception e) {

            e.printStackTrace ();
        }
    }

    private void closeChatHead () {

        Intent in = new Intent (NewAccessibilityService.this, ChatHeadService.class);
       // startService (in);
        stopService (in);
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

        //accessibilityServiceInfo.flags |= AccessibilityServiceInfo.DEFAULT;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        // accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
        // accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        //accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
       // accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;

       // accessibilityServiceInfo.flags |= AccessibilityServiceInfo.FEEDBACK_ALL_MASK;


        //accessibilityServiceInfo.packageNames = packages;
        setServiceInfo (accessibilityServiceInfo);

        super.onServiceConnected ();

    }

    private void onQuora (AccessibilityEvent event) {

        try {
            for (AccessibilityNodeInfo info : event.getSource ().findAccessibilityNodeInfosByViewId ("in_app_webview")) {

                Log.e (TAG, info.getContentDescription () + "");
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }

        if (null != event.getSource ()) {
            for (int i = 0; i < event.getSource ().getChildCount (); i++) {
                AccessibilityNodeInfo child = event.getSource ().getChild (i);
                if (child != null && child.getChildCount () > 1) {
                    child = child.getChild (1);
                    if (child != null && child.getChildCount () > 0) {
                        child = child.getChild (0);
                        if (child != null && "android.widget.WebView".equals (child.getClassName ())) {
                            Log.e (TAG, child.getContentDescription () + "");
                            return;
                        }
                    }
                }
                child = event.getSource ().getChild (i);
                Log.e (TAG, child.getContentDescription () + "");
            }
        }
    }

    public int onStartCommand (Intent intent, int i, int i2) {
        super.onStartCommand (intent, i, i2);
        return START_NOT_STICKY;
    }
}
