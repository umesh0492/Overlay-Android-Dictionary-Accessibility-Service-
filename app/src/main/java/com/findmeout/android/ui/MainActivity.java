package com.findmeout.android.ui;


import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.findmeout.android.R;
import com.findmeout.android.accessibility.AccessibilityUtils;
import com.findmeout.android.service.DownloadCompleteDictionaryService;
import com.findmeout.android.utils.Preferences;

import java.lang.reflect.Method;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cvOverlayPermissionButton, cvAccessibilityServiceButton,
            cvDownloadDictionaryButton, cvDownloadDictionaryProgressLayout;
    private TextView tvOverlayPermissionText, tvAccessibilityServiceText,
            tvDownloadDictionaryProgressText, tvEveryThingOk;

    private ProgressBar pbDownloadDictionaryProgress;
    private ImageView ivCancelDictionaryDownload;

    private boolean dictionaryDownloaded = false, everyThingOk = true;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver () {
        @Override
        public void onReceive (Context context, Intent intent) {
            // Get extra data included in the Intent
            int progress = intent.getIntExtra ("progress", 0);
            Log.e ("progress", "" + progress);
            if (progress < 100) {
                tvDownloadDictionaryProgressText.setText (String.format ("%d%%", progress));
                pbDownloadDictionaryProgress.setProgress (progress);
                showDownloadButton (false);
            }
            else {
                //dictionary downloaded.
                dictionaryDownloaded ();
            }
        }
    };

    private void dictionaryDownloaded () {
        cvDownloadDictionaryButton.setVisibility (View.GONE);
        cvDownloadDictionaryProgressLayout.setVisibility (View.GONE);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main_activity);

        pbDownloadDictionaryProgress = (ProgressBar)
                findViewById (R.id.download_dictionary_progress);
        ivCancelDictionaryDownload = (ImageView) findViewById (R.id.cancel_downloading_button);
        cvAccessibilityServiceButton = (CardView) findViewById (R.id.accessibility_service_button);
        cvOverlayPermissionButton = (CardView) findViewById (R.id.overlay_permission_button);
        cvDownloadDictionaryButton = (CardView) findViewById (R.id.download_dictionary_button);
        cvDownloadDictionaryProgressLayout = (CardView)
                findViewById (R.id.download_dictionary_progress_layout);
        tvAccessibilityServiceText = (TextView) findViewById (R.id.accessibility_service_text);
        tvOverlayPermissionText = (TextView) findViewById (R.id.overlay_permission_text);
        tvDownloadDictionaryProgressText = (TextView)
                findViewById (R.id.download_dictionary_progress_text);
        tvEveryThingOk = (TextView) findViewById (R.id.every_thing_ok);

        cvOverlayPermissionButton.setOnClickListener (this);
        cvAccessibilityServiceButton.setOnClickListener (this);
        ivCancelDictionaryDownload.setOnClickListener (this);
        cvDownloadDictionaryButton.setOnClickListener (this);

    }

    @Override
    protected void onStart () {
        super.onStart ();

        if (isDictionaryDownloaded ()) {
            dictionaryDownloaded ();
            dictionaryDownloaded = true;
        }
        else {
            LocalBroadcastManager.getInstance (this).registerReceiver (mMessageReceiver,
                    new IntentFilter ("download_dictionary_intent"));
        }
        initUIWithData ();
    }

    private boolean isDictionaryDownloaded () {
        if (("-1".equals (Preferences.getNextDownloadWordMeaningCategoryId ())
                && ("-1".equals (Preferences.getNextDownloadWordId ()))
                && ("-1".equals (Preferences.getNextDownloadWordMeaningId ())))) {
            dictionaryDownloaded ();
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onStop () {
        if (!dictionaryDownloaded) {
            LocalBroadcastManager.getInstance (this).unregisterReceiver (mMessageReceiver);
        }
        super.onStop ();
    }

    private void initUIWithData () {

        if (new AccessibilityUtils ().isAccessibilitySettingsOn (this)) {
            cvAccessibilityServiceButton.setVisibility (View.GONE);
        }
        else {
            tvAccessibilityServiceText.setText (getString (R.string.turn_on_accessibility_service));
            cvAccessibilityServiceButton.setVisibility (View.VISIBLE);
            cvAccessibilityServiceButton.setBackgroundColor (Color.RED);
            everyThingOk =false;
        }

        if (canDrawOverlayViews ()) {
            cvOverlayPermissionButton.setVisibility (View.GONE);
        }
        else {
            tvOverlayPermissionText.setText (getString (R.string.grant_overlay_permission));
            cvOverlayPermissionButton.setVisibility (View.VISIBLE);
            cvOverlayPermissionButton.setBackgroundColor (Color.RED);
            everyThingOk = false;
        }

        if (!isDictionaryDownloaded ()) {
            showDownloadButton (true);
            everyThingOk =false;
        }
        if (everyThingOk){
            tvEveryThingOk .setVisibility (View.VISIBLE);
        }
        else {
            tvEveryThingOk .setVisibility (View.GONE);

        }
    }

    public boolean canDrawOverlayViews () {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        try {
            return Settings.canDrawOverlays (this);
        } catch (NoSuchMethodError e) {
            e.printStackTrace ();
            return canDrawOverlaysUsingReflection (this);
        }

    }

    void showDownloadButton (boolean visible) {

        if (visible) {
            cvDownloadDictionaryButton.setVisibility (View.VISIBLE);
            cvDownloadDictionaryProgressLayout.setVisibility (View.GONE);
        }
        else {
            cvDownloadDictionaryButton.setVisibility (View.GONE);
            cvDownloadDictionaryProgressLayout.setVisibility (View.VISIBLE);
        }
    }

    public boolean canDrawOverlaysUsingReflection (Context context) {

        try {
            AppOpsManager manager = (AppOpsManager) context.getSystemService (Context.APP_OPS_SERVICE);
            Class clazz = AppOpsManager.class;
            Method dispatchMethod = clazz.getMethod ("checkOp",
                    new Class[]{int.class, int.class, String.class});
            //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
            int mode = (Integer) dispatchMethod.invoke (manager,
                    new Object[]{24, Binder.getCallingUid (),
                            context.getApplicationContext ().getPackageName ()});
          /* int modee = manager.checkOp(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                    Binder.getCallingUid(), context.getPackageName());

          */
            //Log.e ("mode", mode + "");

            return AppOpsManager.MODE_ALLOWED == mode;


        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }

    }

    @Override
    public void onClick (View view) {
        switch (view.getId ()) {
            case R.id.accessibility_service_button:
                requestAccessibilityServicePermission ();
                break;
            case R.id.overlay_permission_button:
                try {
                    requestOverlayDrawPermission (1);
                } catch (ActivityNotFoundException e) {

                }
                break;
            case R.id.download_dictionary_button:
                startService (new Intent (MainActivity.this,
                        DownloadCompleteDictionaryService.class));
                showDownloadButton (false);
                break;
            case R.id.cancel_downloading_button:
                Intent in = new Intent (MainActivity.this,
                        DownloadCompleteDictionaryService.class);
                in.putExtra ("close", true);
                startService (in);
                showDownloadButton (true);
                break;
        }
    }

    void requestAccessibilityServicePermission () {

        if (!new AccessibilityUtils ().isAccessibilitySettingsOn (this)) {
            Intent intent = new Intent (android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult (intent, 0);
        }
    }

    @SuppressLint ("InlinedApi")
    public void requestOverlayDrawPermission (int requestCode) {
        Intent intent = new Intent (Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse ("package:" + getPackageName ()));

        startActivityForResult (intent, requestCode);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */

        if (resultCode == RESULT_OK) {
            initUIWithData ();
        }
        else {
            switch (requestCode) {
                case 0:
                   /* Toast.makeText (this,
                            "service is still turn off. " +
                                    "To run this application turn on accessibility service",
                            Toast.LENGTH_LONG).show ();*/

                    break;
                case 1:

                    Toast.makeText (this,
                            "display pop-up window permission not" +
                                    " granted automatically, kindly grant manually",
                            Toast.LENGTH_LONG).show ();

                    AlertDialog.Builder builder = new AlertDialog.Builder (MainActivity.this);
                    builder.setTitle ("Grant permission manually");
                    builder.setMessage ("Find permission manager and grant" +
                            " display pop-up window permission");
                    builder.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick (DialogInterface dialogInterface, int i) {

                            Intent intent
                                    = new Intent (Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts ("package", getPackageName (), null));
                            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity (intent);
                        }
                    });
                    AlertDialog dialog = builder.create ();
                    dialog.show ();

                    break;
            }
        }
    }

}
