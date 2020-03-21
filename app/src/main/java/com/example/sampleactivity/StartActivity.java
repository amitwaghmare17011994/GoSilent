package com.example.sampleactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class StartActivity extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        this.setPermissions();
        this.launchMainActivity();
    }

    public void setPermissions() {
        MobileAudioManager audioManager=new MobileAudioManager(this);
    }
    public  void launchMainActivity()
    {
        Intent intent=new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}
