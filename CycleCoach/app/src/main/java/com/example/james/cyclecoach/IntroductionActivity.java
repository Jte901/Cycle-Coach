package com.example.james.cyclecoach;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

public class IntroductionActivity extends AppCompatActivity implements View.OnClickListener,
        IntroductionFragment.OnFragmentInteractionListener, NameFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));

        Fragment fragment = null;
        Class fragmentClass = IntroductionFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {

        }
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }


}
