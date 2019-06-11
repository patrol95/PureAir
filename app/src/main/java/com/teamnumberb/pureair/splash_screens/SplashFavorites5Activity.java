package com.teamnumberb.pureair.splash_screens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.teamnumberb.pureair.MainActivity;
import com.teamnumberb.pureair.R;

public class SplashFavorites5Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_favorites_5);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashFavorites5Activity.this, SplashFinalActivity.class));
                finish();
            }
        }, 10000);
    }
}
