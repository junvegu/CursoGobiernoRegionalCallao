package com.bixspace.ciclodevida.presentation.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.presentation.fragments.LoginFragment;
import com.bixspace.ciclodevida.presentation.fragments.MainFragment;
import com.bixspace.ciclodevida.presentation.services.GeolocationService;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        toolbar =(Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Lista de Personas");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        MainFragment fragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);


        if (fragment == null) {
            fragment = MainFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
