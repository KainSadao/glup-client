package com.glup.client.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.glup.client.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kain on 02/10/2016.
 */

public class SplashScreenActivity extends AppCompatActivity{

    private static final int DELAY_TIME_ACTIVITY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent login = new Intent(SplashScreenActivity.this, TutorialActivity.class);
                startActivity(login);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,DELAY_TIME_ACTIVITY);
    }
}
