package com.example.james.cyclecoach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GearActivity extends AppCompatActivity implements View.OnClickListener{

    UserData data;
    Button decMiles;
    Button incMiles;
    Button decFreq;
    Button incFreq;
    Button decDays;
    Button incDays;
    Button waterBottleButton;
    Button hexKeyButton;
    Button whistleButton;
    TextView milesText;
    TextView freqText;
    TextView daysText;
    TextView head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear);
        data = new UserData();
        data.name = getIntent().getExtras().getString("NAME");
        data.frequency = getIntent().getExtras().getInt("FREQUENCY");
        data.days = getIntent().getExtras().getInt("DAYS");
        data.distance = getIntent().getExtras().getFloat("DISTANCE");
        data.firstTime = getIntent().getExtras().getBoolean("FIRST_TIME");


        decMiles = (Button)findViewById(R.id.decMiles);
        incMiles = (Button)findViewById(R.id.incMiles);
        decFreq = (Button)findViewById(R.id.decFreq);
        incFreq = (Button)findViewById(R.id.incFreq);
        decDays = (Button)findViewById(R.id.decDays);
        incDays = (Button)findViewById(R.id.incDays);
        waterBottleButton = (Button)findViewById(R.id.waterBottleButton);
        hexKeyButton = (Button)findViewById(R.id.hexKeyButton);
        whistleButton = (Button)findViewById(R.id.whistleButton);
        milesText = (TextView)findViewById(R.id.milesText);
        freqText = (TextView)findViewById(R.id.freqText);
        daysText = (TextView)findViewById(R.id.daysText);
        head = (TextView)findViewById(R.id.head);

        decMiles.setOnClickListener(this);
        incMiles.setOnClickListener(this);
        decFreq.setOnClickListener(this);
        incFreq.setOnClickListener(this);
        decDays.setOnClickListener(this);
        incDays.setOnClickListener(this);
        waterBottleButton.setOnClickListener(this);
        hexKeyButton.setOnClickListener(this);
        whistleButton.setOnClickListener(this);

        head.setText(data.name + "'s goals");
        milesText.setText("" + data.distance);
        freqText.setText("" + data.frequency);
        daysText.setText("" + data.days);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == decMiles.getId()) {
            if (data.distance >= 1) {
                data.distance -= 0.5;
                milesText.setText("" + data.distance);
            }
        }
        if (v.getId() == incMiles.getId()) {
            data.distance += 0.5;
            milesText.setText("" + data.distance);
        }
        if (v.getId() == decFreq.getId()) {
            if (data.frequency >= 2) {
                data.frequency--;
                freqText.setText("" + data.frequency);
            }
        }
        if (v.getId() == incFreq.getId()) {
            data.frequency++;
            freqText.setText("" + data.frequency);
        }
        if (v.getId() == decDays.getId()) {
            if (data.days >= 2) {
                data.days--;
                daysText.setText("" + data.days);
            }
        }
        if (v.getId() == incDays.getId()) {
            data.days++;
            daysText.setText("" + data.days);
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
