package com.example.matt.watchface;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mClockView;
    CommHandler commHandler;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mClockView = (TextView) findViewById(R.id.clock);
        commHandler = new CommHandler(this);
        layout = (RelativeLayout)findViewById(R.id.rLayout);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mClockView.setVisibility(View.GONE);
        }
    }

    public void updateMood(int i) {
        if (i == 0) {
            layout.setBackgroundResource(R.drawable.watchface_blue);
        }
        if (i == 1) {
            layout.setBackgroundResource(R.drawable.watchface_green);
        }
        if (i == 2) {
            layout.setBackgroundResource(R.drawable.watchface_orange);
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            String str = "Let's get a ride in today!";
            Toast toast = Toast.makeText(context, str, duration);
            toast.show();
        }
        if (i == 3) {
            layout.setBackgroundResource(R.drawable.watchface_red);
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            String str = "GET UP AND RIDE!";
            Toast toast = Toast.makeText(context, str, duration);
            toast.show();
        }
    }
}
