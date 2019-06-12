package com.teamnumberb.pureair.splash_screens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.teamnumberb.pureair.MainActivity;
import com.teamnumberb.pureair.R;

public class SplashFavorites3Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_favorites_3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashFavorites3Activity.this, SplashFavorites4Activity.class));
                finish();
            }
        }, 15000);
    }
}
