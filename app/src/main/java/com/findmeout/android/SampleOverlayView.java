package com.findmeout.android;

/**
 * Created by umesh0492 on 13/06/16.
 */
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

public class SampleOverlayView extends OverlayView {

    private TextView info;

    public SampleOverlayView(OverlayService service) {
        super(service, R.layout.overlay, 1);
    }

    public int getGravity() {
        return Gravity.TOP + Gravity.RIGHT;
    }

    @Override
    protected void onInflateView() {
        info = (TextView) this.findViewById(R.id.textview_info);
    }

    @Override
    protected void refreshViews() {
        info.setText("WAITING\nWAITING");
    }

    @Override
    protected void onTouchEvent_Up(MotionEvent event) {
        info.setText("UP\nPOINTERS: " + event.getPointerCount());
    }

    @Override
    protected void onTouchEvent_Move(MotionEvent event) {
        info.setText("MOVE\nPOINTERS: " + event.getPointerCount());
    }

    @Override
    protected void onTouchEvent_Press(MotionEvent event) {
        info.setText("DOWN\nPOINTERS: " + event.getPointerCount());
    }

    @Override
    public boolean onTouchEvent_LongPress() {
        info.setText("LONG\nPRESS");

        return true;
    }


}
