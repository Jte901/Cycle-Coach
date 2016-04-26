package com.example.james.cyclecoach;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GearActivity extends AppCompatActivity implements View.OnClickListener {

    Button personalGoalsButton;
    Button cyclingGoalsButton;
    Button progressButton;
    Button waterBottleButton;
    Button hexKeyButton;
    Button whistleButton;
    String lance_state;
    UserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear);
<<<<<<< HEAD
        personalGoalsButton = (Button)findViewById(R.id.personalGoalsButton);
        personalGoalsButton.setOnClickListener(this);
        cyclingGoalsButton = (Button)findViewById(R.id.cyclingGoalsButton);
        cyclingGoalsButton.setOnClickListener(this);
        progressButton = (Button)findViewById(R.id.progressButton);
        progressButton.setOnClickListener(this);
        waterBottleButton = (Button)findViewById(R.id.waterBottleButton);
=======
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        data = new UserData();
        data.name = getIntent().getExtras().getString("NAME");
        data.frequency = getIntent().getExtras().getInt("FREQUENCY");
        data.days = getIntent().getExtras().getInt("DAYS");
        data.distance = getIntent().getExtras().getFloat("DISTANCE");
        data.firstTime = getIntent().getExtras().getBoolean("FIRST_TIME");


        decMiles = (Button) findViewById(R.id.decMiles);
        incMiles = (Button) findViewById(R.id.incMiles);
        decFreq = (Button) findViewById(R.id.decFreq);
        incFreq = (Button) findViewById(R.id.incFreq);
        decDays = (Button) findViewById(R.id.decDays);
        incDays = (Button) findViewById(R.id.incDays);
        waterBottleButton = (Button) findViewById(R.id.waterBottleButton);
        hexKeyButton = (Button) findViewById(R.id.hexKeyButton);
        whistleButton = (Button) findViewById(R.id.whistleButton);
        milesText = (TextView) findViewById(R.id.milesText);
        freqText = (TextView) findViewById(R.id.freqText);
        daysText = (TextView) findViewById(R.id.daysText);
        head = (TextView) findViewById(R.id.head);

        decMiles.setOnClickListener(this);
        incMiles.setOnClickListener(this);
        decFreq.setOnClickListener(this);
        incFreq.setOnClickListener(this);
        decDays.setOnClickListener(this);
        incDays.setOnClickListener(this);
>>>>>>> 5a1b209a22c27a89858fea930b3e9614ef352b05
        waterBottleButton.setOnClickListener(this);
        hexKeyButton = (Button)findViewById(R.id.hexKeyButton);
        hexKeyButton.setOnClickListener(this);
        whistleButton = (Button)findViewById(R.id.whistleButton);

        data = (UserData)getIntent().getSerializableExtra("DATA");
        lance_state = getIntent().getExtras().getString("LANCE_STATE");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == personalGoalsButton.getId()) {
            Intent pgIntent = new Intent(this, PersonalGoalsActivity.class);
            pgIntent.putExtra("DATA", data);
            pgIntent.putExtra("LANCE_STATE", lance_state);
            startActivity(pgIntent);
        }
        if (v.getId() == cyclingGoalsButton.getId()) {
            Intent cgIntent = new Intent(this, CyclingGoalsActivity.class);
            cgIntent.putExtra("DATA", data);
            cgIntent.putExtra("LANCE_STATE", lance_state);
            startActivity(cgIntent);
        }
        if (v.getId() == progressButton.getId()) {
            Intent progressIntent = new Intent(this, ProgressActivity.class);
            progressIntent.putExtra("DATA", data);
            progressIntent.putExtra("LANCE_STATE", lance_state);
            startActivity(progressIntent);
        }
        if (v.getId() == whistleButton.getId()) {
            Intent whistleIntent = new Intent(this, ProgressActivity.class);
            whistleIntent.putExtra("DATA", data);
            whistleIntent.putExtra("LANCE_STATE", lance_state);
            startActivity(whistleIntent);
        }

    }
}
