package com.example.james.cyclecoach;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button _cogwheelButton;
    Button _waterBottleButton;
    Button _whistleButton;
    Button _hexKeyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _cogwheelButton = (Button) findViewById(R.id.cogwheelButton);
        _waterBottleButton = (Button) findViewById(R.id.waterBottleButton);
        _whistleButton = (Button) findViewById(R.id.whistleButton);
        _hexKeyButton = (Button) findViewById(R.id.hexKeyButton);
    }

    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.cogwheelButton:
                Toast.makeText(this, "You clicked the cogwheel!", Toast.LENGTH_LONG).show();
                break;
            case R.id.waterBottleButton:
                Toast.makeText(this, "You clicked the water bottle!", Toast.LENGTH_LONG).show();
                break;
            case R.id.whistleButton:
                Toast.makeText(this, "You clicked the whistle!", Toast.LENGTH_LONG).show();
                break;
            case R.id.hexKeyButton:
                Toast.makeText(this, "You clicked the hex key!", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
