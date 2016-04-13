package com.example.james.cyclecoach;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout _layout;
    TextView _dialogTextView;
    ImageView lance;
    Button _eyeButton;
    Button _gearButton;
    Button _waterBottleButton;
    Button _whistleButton;
    Button _hexKeyButton;
    File nameFile;
    int _eyePressCount;
    UserData data;
    Document doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lance = (ImageView) findViewById(R.id.lance_image);
        Intent intent = getIntent();
        String lance_state = intent.getStringExtra("LANCE_STATE");

        _layout = (RelativeLayout) findViewById(R.id.main_activity_layout);
        _dialogTextView = (TextView) findViewById(R.id.dialogTextView);
        data = new UserData();
        data.name = intent.getStringExtra("USER_NAME");

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

        if (lance_state.equals("blue")) {
            lance.setImageDrawable(getDrawable(R.drawable.lance));
            _dialogTextView.setText("What can I help you with, " + this.data.name + "?");
        } else if (lance_state.equals("orange")) {
            lance.setImageDrawable(getDrawable(R.drawable.lance_orange));
            _dialogTextView.setText("It's been a while, " + this.data.name + "... you should ride soon.");

        } else if (lance_state.equals("red")) {
            lance.setImageDrawable(getDrawable(R.drawable.lance_red));
            _dialogTextView.setText("It's been way too long, " + this.data.name + ". You need to ride!");

        }
        Intent splash = new Intent(this, SplashScreenActivity.class);

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
    protected void onResume() {
        super.onResume();
        nameFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "CycleCoach_name.xml");
        if (!nameFile.exists()) {
            Intent intent = new Intent(this, IntroductionActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

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
                Intent hexKeyIntent = new Intent(this, HexKeyActivity.class);
                startActivity(hexKeyIntent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
