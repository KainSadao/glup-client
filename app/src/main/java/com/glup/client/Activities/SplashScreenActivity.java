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

    private static Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }
}
