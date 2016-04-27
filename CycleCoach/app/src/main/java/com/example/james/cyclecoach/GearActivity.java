package com.example.james.cyclecoach;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class GearActivity extends AppCompatActivity implements View.OnClickListener{

    Button personalGoalsButton;
    Button cyclingGoalsButton;
    Button progressButton;
    Button waterBottleButton;
    Button hexKeyButton;
    Button whistleButton;
    UserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        personalGoalsButton = (Button)findViewById(R.id.personalGoalsButton);
        personalGoalsButton.setOnClickListener(this);
        cyclingGoalsButton = (Button)findViewById(R.id.cyclingGoalsButton);
        cyclingGoalsButton.setOnClickListener(this);
        progressButton = (Button)findViewById(R.id.progressButton);
        progressButton.setOnClickListener(this);
        waterBottleButton = (Button)findViewById(R.id.waterBottleButton);
        waterBottleButton.setOnClickListener(this);
        hexKeyButton = (Button)findViewById(R.id.hexKeyButton);
        hexKeyButton.setOnClickListener(this);
        whistleButton = (Button)findViewById(R.id.whistleButton);

        data = new UserData();
        data.name = getIntent().getExtras().getString("NAME");
        data.frequency = getIntent().getExtras().getInt("FREQUENCY");
        data.days = getIntent().getExtras().getInt("DAYS");
        data.distance = getIntent().getExtras().getFloat("DISTANCE");
        data.firstTime = getIntent().getExtras().getBoolean("FIRST_TIME");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == personalGoalsButton.getId()) {
            Intent pgIntent = new Intent(this, PersonalGoalsActivity.class);
            pgIntent.putExtra("NAME", data.name);
            pgIntent.putExtra("DISTANCE", data.distance);
            pgIntent.putExtra("DAYS", data.days);
            pgIntent.putExtra("FREQUENCY", data.frequency);
            pgIntent.putExtra("FIRST_TIME", data.firstTime);
            startActivity(pgIntent);
        }
        if (v.getId() == cyclingGoalsButton.getId()) {
            Intent cgIntent = new Intent(this, CyclingGoalsActivity.class);
            cgIntent.putExtra("NAME", data.name);
            cgIntent.putExtra("DISTANCE", data.distance);
            cgIntent.putExtra("DAYS", data.days);
            cgIntent.putExtra("FREQUENCY", data.frequency);
            cgIntent.putExtra("FIRST_TIME", data.firstTime);
            startActivity(cgIntent);
        }
        if (v.getId() == progressButton.getId()) {
            Intent progressIntent = new Intent(this, ProgressActivity.class);
            progressIntent.putExtra("NAME", data.name);
            progressIntent.putExtra("DISTANCE", data.distance);
            progressIntent.putExtra("DAYS", data.days);
            progressIntent.putExtra("FREQUENCY", data.frequency);
            progressIntent.putExtra("FIRST_TIME", data.firstTime);
            startActivity(progressIntent);
        }
        if (v.getId() == whistleButton.getId()) {
            Intent whistleIntent = new Intent(this, MainActivity.class);
            whistleIntent.putExtra("NAME", data.name);
            whistleIntent.putExtra("DISTANCE", data.distance);
            whistleIntent.putExtra("DAYS", data.days);
            whistleIntent.putExtra("FREQUENCY", data.frequency);
            whistleIntent.putExtra("FIRST_TIME", data.firstTime);
            startActivity(whistleIntent);
        }

    }
}
