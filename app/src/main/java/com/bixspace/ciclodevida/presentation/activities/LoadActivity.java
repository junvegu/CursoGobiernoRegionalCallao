package com.bixspace.ciclodevida.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.data.local.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by junior on 28/11/16.
 */

public class LoadActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DELAY =2000;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_activity);
            sessionManager = new SessionManager(this);
            initialProcess();

    }

    private void initialProcess() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //Preguntaremos si el usuario esta logueado mediante el Shared Preference
                if(sessionManager.isLogin()){
                    //Si el usuario esta logueado
                    Intent intentMain = new Intent().setClass(LoadActivity.this,MainActivity.class);
                    startActivity(intentMain);
                    finish();
                }else{
                    //Si el usuario no esta logueado
                    Intent intentMain = new Intent().setClass(LoadActivity.this,LoginActivity.class);
                    startActivity(intentMain);
                    finish();

                }
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }
}
