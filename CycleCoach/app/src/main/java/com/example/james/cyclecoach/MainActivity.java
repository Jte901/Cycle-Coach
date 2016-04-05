package com.example.james.cyclecoach;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout _layout;
    TextView _dialogTextView;

    Button _eyeButton;
    Button _cogwheelButton;
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
        if (getIntent().getExtras() != null)
            data.name = getIntent().getExtras().getString("NAME");

        _eyeButton = (Button) findViewById(R.id.eyeButton);
        _cogwheelButton = (Button) findViewById(R.id.gearButton);
        _waterBottleButton = (Button) findViewById(R.id.waterBottleButton);
        _whistleButton = (Button) findViewById(R.id.whistleButton);
        _hexKeyButton = (Button) findViewById(R.id.hexKeyButton);
        _eyePressCount = 0;

        _eyeButton.setOnClickListener(this);
        _cogwheelButton.setOnClickListener(this);
        _waterBottleButton.setOnClickListener(this);
        _whistleButton.setOnClickListener(this);
        _hexKeyButton.setOnClickListener(this);

        if (data.firstTime) {
            Intent intent = new Intent(this, IntroductionActivity.class);
            startActivity(intent);
            data.firstTime = false;
        }

        if (data.name.compareTo("") == 0) {
            Intent nameIntent = new Intent(this, NameActivity.class);
            startActivity(nameIntent);
        }
        else {
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
                _dialogTextView.setText("You clicked the whistle!");
                Intent intent = new Intent(this, WhistleActivty.class);
                startActivity(intent);
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
                    }
                    _eyePressCount = 0;
                }
            default:
                break;
        }
    }
}
