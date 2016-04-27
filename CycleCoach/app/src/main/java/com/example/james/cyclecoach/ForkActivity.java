package com.example.james.cyclecoach;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class ForkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fork);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));
    }
}
