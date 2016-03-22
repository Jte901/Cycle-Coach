package com.example.james.cyclecoach;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView _dialogTextView;

    Button _eyeButton;
    Button _cogwheelButton;
    Button _waterBottleButton;
    Button _whistleButton;
    Button _hexKeyButton;

    int _eyePressCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _dialogTextView = (TextView) findViewById(R.id.dialogTextView);

        _eyeButton = (Button) findViewById(R.id.eyeButton);
        _cogwheelButton = (Button) findViewById(R.id.cogwheelButton);
        _waterBottleButton = (Button) findViewById(R.id.waterBottleButton);
        _whistleButton = (Button) findViewById(R.id.whistleButton);
        _hexKeyButton = (Button) findViewById(R.id.hexKeyButton);
        _eyePressCount = 0;

        Intent intent = new Intent(this, IntroductionActivity.class);
        startActivity(intent);
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.cogwheelButton:
                _dialogTextView.setText("You clicked the cogwheel!");
                break;
            case R.id.waterBottleButton:
                _dialogTextView.setText("You clicked the water bottle!");
                break;
            case R.id.whistleButton:
                _dialogTextView.setText("You clicked the whistle!");
                break;
            case R.id.hexKeyButton:
                _dialogTextView.setText("You clicked the hex key!");
                break;
            case R.id.eyeButton:
                ++_eyePressCount;
                if (_eyePressCount == 10) {
                    _dialogTextView.setText("Stop poking my eye!");
                    _eyePressCount = 0;
                }
            default:
                break;
        }
    }
}
