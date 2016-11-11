package com.glup.client.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                preferences = SplashScreenActivity.this.getSharedPreferences(getString(R.string.ini_preferences), Context.MODE_PRIVATE);
                boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
                if(isFirstRun){ //La primera vez que corre la app, crea el sharedPreferences y muestra el tutorial
                    createSharedPreferences();
                    Intent tutorial = new Intent(SplashScreenActivity.this, TutorialActivity.class);
                    startActivity(tutorial);
                }else{ //Si no es la primera vez que corre la app, lee la preferencia de mostrar o no el tutorial
                    if(preferences.getBoolean("enableTutorial", true)){
                        Intent tutorial = new Intent(SplashScreenActivity.this, TutorialActivity.class);
                        startActivity(tutorial);
                    }else {
                        Intent login = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(login);
                    }
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,DELAY_TIME_ACTIVITY);
    }

    private void createSharedPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.putString("name","");
        editor.putBoolean("isLogged", false);
        editor.putBoolean("enableTutorial", true);
        editor.apply();
    }
}
