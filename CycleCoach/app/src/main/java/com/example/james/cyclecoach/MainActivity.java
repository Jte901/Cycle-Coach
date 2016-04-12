package com.example.james.cyclecoach;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout _layout;
    TextView _dialogTextView;

    Button _eyeButton;
    Button _gearButton;
    Button _waterBottleButton;
    Button _whistleButton;
    Button _hexKeyButton;

    int _eyePressCount;
    UserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _layout = (RelativeLayout) findViewById(R.id.main_activity_layout);
        _dialogTextView = (TextView) findViewById(R.id.dialogTextView);

        data = new UserData();
        if (getIntent().getExtras() != null) {
            data.name = getIntent().getExtras().getString("NAME");
            data.frequency = getIntent().getExtras().getInt("FREQUENCY");
            data.days = getIntent().getExtras().getInt("DAYS");
            data.distance = getIntent().getExtras().getFloat("DISTANCE");
            data.firstTime = getIntent().getExtras().getBoolean("FIRST_TIME");
        }

        _eyeButton = (Button) findViewById(R.id.eyeButton);
        _gearButton = (Button) findViewById(R.id.gearButton);
        _waterBottleButton = (Button) findViewById(R.id.waterBottleButton);
        _whistleButton = (Button) findViewById(R.id.whistleButton);
        _hexKeyButton = (Button) findViewById(R.id.hexKeyButton);
        _eyePressCount = 0;

        _eyeButton.setOnClickListener(this);
        _gearButton.setOnClickListener(this);
        _waterBottleButton.setOnClickListener(this);
        _whistleButton.setOnClickListener(this);
        _hexKeyButton.setOnClickListener(this);

        if (data.firstTime) {
            Intent intent = new Intent(this, IntroductionActivity.class);
            intent.putExtra("NAME", data.name);
            intent.putExtra("DISTANCE", data.distance);
            intent.putExtra("DAYS", data.days);
            intent.putExtra("FREQUENCY", data.frequency);
            intent.putExtra("FIRST_TIME", data.firstTime);
            startActivity(intent);
        }

        if (data.name.compareTo("") == 0) {
            Intent nameIntent = new Intent(this, NameActivity.class);
            startActivity(nameIntent);
        } else {
            _dialogTextView.setText("What can I help you with, " + data.name + "?");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        data.name = getIntent().getExtras().getString("NAME");
        data.frequency = getIntent().getExtras().getInt("FREQUENCY");
        data.days = getIntent().getExtras().getInt("DAYS");
        data.distance = getIntent().getExtras().getFloat("DISTANCE");
        data.firstTime = getIntent().getExtras().getBoolean("FIRST_TIME");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("NAME", data.name);
        outState.putFloat("DISTANCE", data.distance);
        outState.putInt("DAYS", data.days);
        outState.putInt("FREQUENCY", data.frequency);
        outState.putBoolean("FIRST_TIME", data.firstTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gearButton:
                Intent gearIntent = new Intent(this, GearActivity.class);
                gearIntent.putExtra("NAME", data.name);
                gearIntent.putExtra("DISTANCE", data.distance);
                gearIntent.putExtra("DAYS", data.days);
                gearIntent.putExtra("FREQUENCY", data.frequency);
                gearIntent.putExtra("FIRST_TIME", data.firstTime);
                startActivity(gearIntent);
                break;
            case R.id.waterBottleButton:
                _dialogTextView.setText("You clicked the water bottle!");
                break;
            case R.id.whistleButton:
                Intent progressIntent = new Intent(this, ProgressActivity.class);
                startActivity(progressIntent);
                break;
            case R.id.hexKeyButton:
                _dialogTextView.setText("You clicked the hex key!");
                break;
            case R.id.eyeButton:
                ++_eyePressCount;
                if (_eyePressCount == 10) {
                    _dialogTextView.setText("Stop poking my eye!");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        _layout.setBackground(getDrawable(R.drawable.lance_eyepoked));
                        fixEye();
                    }
                    _eyePressCount = 0;
                }
            default:
                break;
        }
    }

    private void fixEye() {
        try {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                _layout.setBackground(getDrawable(R.drawable.lance));
                                _dialogTextView.setText("");
                            }
                        }
                    });
                }
            }, 2000);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }
}
