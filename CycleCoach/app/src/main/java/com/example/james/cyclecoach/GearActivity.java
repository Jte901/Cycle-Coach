package com.example.james.cyclecoach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GearActivity extends AppCompatActivity implements View.OnClickListener{

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
