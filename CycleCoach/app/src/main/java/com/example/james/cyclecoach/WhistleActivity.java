package com.example.james.cyclecoach;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class WhistleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whistle);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));
    }

    @Override
    public void onClick(View v) {

    }
}
