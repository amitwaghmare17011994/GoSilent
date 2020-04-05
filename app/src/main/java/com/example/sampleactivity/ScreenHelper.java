package com.example.sampleactivity;

import android.app.Activity;

public class ScreenHelper {
    int WIDTH;
    int HEIGHT;
    ScreenHelper(Activity activity)
    {

          WIDTH  = activity.getResources().getSystem().getDisplayMetrics().widthPixels;
          HEIGHT = activity.getResources().getSystem().getDisplayMetrics().heightPixels;

    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }
}
