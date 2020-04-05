package com.example.sampleactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StartActivity extends AppCompatActivity   {
    LinearLayout linearLayout;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        linearLayout=(LinearLayout)findViewById(R.id.ll);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash);
        linearLayout.startAnimation((animation));
       final Intent intent = new Intent(this, MapsActivity.class);

        this.setPermissions();

        Thread timerThread=new Thread(){

            @Override
            public void run() {
                 try {
                sleep(2000);
                 }
                 catch (Exception e){}
                 finally {
                    startActivity(intent);
                    finish();
                 }
            }
        };
        timerThread.start();

    }

    public void setPermissions() {
        MobileAudioManager audioManager = new MobileAudioManager(this);
        audioManager.setPermissions();
    }

    public void launchMainActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }



}
