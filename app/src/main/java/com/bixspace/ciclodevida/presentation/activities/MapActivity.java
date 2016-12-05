package com.bixspace.ciclodevida.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.presentation.fragments.AddPersonFragment;
import com.bixspace.ciclodevida.presentation.fragments.MapFragment;

public class MapActivity extends AppCompatActivity {


    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        toolbar =(Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Mapa");

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        MapFragment fragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);


        if (fragment == null) {
            fragment = MapFragment.newInstance();

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
