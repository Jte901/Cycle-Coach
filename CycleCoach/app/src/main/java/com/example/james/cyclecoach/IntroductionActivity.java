package com.example.james.cyclecoach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IntroductionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView _dialogTextView;
    Button _skipIntroButton;
    Button _gotItButton;
    Button _gearButton;
    Button _waterBottleButton;
    Button _whistleButton;
    Button _hexKeyButton;

    ArrayList<String> _dialogs;
    int _currentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        _dialogTextView = (TextView) findViewById(R.id.dialogTextView);

        _skipIntroButton = (Button) findViewById(R.id.skipIntroButton);
        _gotItButton = (Button) findViewById(R.id.gotItButton);

        _gearButton = (Button) findViewById(R.id.gearButton);
        _waterBottleButton = (Button) findViewById(R.id.waterBottleButton);
        _whistleButton = (Button) findViewById(R.id.whistleButton);
        _hexKeyButton = (Button) findViewById(R.id.hexKeyButton);

        _dialogs = new ArrayList<>();
        _dialogs.add("Hi! I'm Lance, your new cycling coach!");
        _dialogs.add("I'll keep track of your cycling stats, but more importantly I'll help you set " +
                "and obtain your cycling goals.");
        _dialogs.add("It's up to you whether you'll improve or not!");
        _dialogs.add("But I can help you figure out the best way for you to do that.");
        _dialogs.add("Besides meeting face-to-face, there are 4 areas you should know about.");
        _dialogs.add("The gear will take you to your cycling data. (Remember, I can only keep track " +
                "of things you tell me about.)");
        _dialogs.add("The sports bottle is where you can record your nutrition.");
        _dialogs.add("The whistle is where you can set & check your coaching settings.");
        _dialogs.add("The hex key is where you can set sensors like NFC, cadence, HR, etc. (You'll " +
                "need to make sure I can do that for you, but I'll help when the time comes.)");

        _dialogTextView.setText(_dialogs.get(0));
        _currentDialog = 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skipIntroButton:
                break;
            case R.id.gotItButton:
                if (_currentDialog == _dialogs.size()) return;

                _gearButton.setBackgroundResource(R.drawable.gear);
                _waterBottleButton.setBackgroundResource(R.drawable.waterbottle);
                _whistleButton.setBackgroundResource(R.drawable.whistle);
                _hexKeyButton.setBackgroundResource(R.drawable.hexkey);
                _dialogTextView.setTextSize(38);

                if (_currentDialog == 1) {
                    _dialogTextView.setTextSize(30);
                } else if (_currentDialog == 5) {
                    _dialogTextView.setTextSize(30);
                    _gearButton.setBackgroundResource(R.drawable.gear_highlight);
                } else if (_currentDialog == 6) {
                    _waterBottleButton.setBackgroundResource(R.drawable.waterbottle_highlight);
                } else if (_currentDialog == 7) {
                    _whistleButton.setBackgroundResource(R.drawable.whistle_highlight);
                } else if (_currentDialog == 8) {
                    _hexKeyButton.setBackgroundResource(R.drawable.hexkey_highlight);
                    _dialogTextView.setTextSize(24);
                }
                _dialogTextView.setText(_dialogs.get(_currentDialog));
                ++_currentDialog;
                break;
        }
    }
}
