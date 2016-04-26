package com.example.james.cyclecoach;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonalGoalsActivity extends AppCompatActivity implements View.OnClickListener{

    EditText goals;
    Button okButton2;
    UserData data;
    String lance_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_goals);
        goals = (EditText)findViewById(R.id.goals);
        okButton2 = (Button)findViewById(R.id.okButton2);
        goals.setOnClickListener(this);
        okButton2.setOnClickListener(this);
        data = (UserData)getIntent().getSerializableExtra("DATA");
        lance_state = getIntent().getExtras().getString("LANCE_STATE");
        goals.setText(data.personalGoals);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == okButton2.getId()) {
            data.personalGoals = goals.getText().toString();
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra("DATA", data);
            mainIntent.putExtra("LANCE_STATE", lance_state);
            startActivity(mainIntent);
        }


    }
}
