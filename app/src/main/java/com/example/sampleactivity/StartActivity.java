package com.example.sampleactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        this.setPermissions();
        this.launchMainActivity();
    }

    public void setPermissions() {
        MobileAudioManager audioManager = new MobileAudioManager(this);
    }

    public void launchMainActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}
