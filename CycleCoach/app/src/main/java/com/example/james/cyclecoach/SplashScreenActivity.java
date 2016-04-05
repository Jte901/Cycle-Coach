package com.example.james.cyclecoach;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Austin on 3/22/2016.
 */
public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 5000;
    /**
     * Called when the activity is first created.
     */
    Thread splashTread;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));

        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(animationSet);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    // Splash screen pause time
                    Thread.sleep(SPLASH_TIME_OUT);
                    Intent intent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreenActivity.this.finish();
                }
            }
        };
        splashTread.start();
    }
}
