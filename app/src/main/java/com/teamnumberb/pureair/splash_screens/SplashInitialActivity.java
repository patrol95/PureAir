package com.teamnumberb.pureair.splash_screens;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.teamnumberb.pureair.MainActivity;
import com.teamnumberb.pureair.R;

import org.w3c.dom.Text;

public class SplashInitialActivity extends Activity {

    SharedPreferences prefs;
    Boolean firstLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_initial);

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        firstLaunch = prefs.getBoolean("firstLaunch", true);

        if (firstLaunch) {
            TextView textView = findViewById(R.id.initial_skip);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstLaunch = false;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstLaunch", firstLaunch);
                    editor.apply();
                    startActivity(new Intent(SplashInitialActivity.this, MainActivity.class));
                    finish();

                }
            });
            final TextView textView1 = findViewById(R.id.initial_next);
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstLaunch = false;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstLaunch", firstLaunch);
                    editor.apply();
                    startActivity(new Intent(SplashInitialActivity.this, SplashHomeInitActivity.class));
                    finish();
                }
            });
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    firstLaunch = false;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstLaunch", firstLaunch);
                    editor.apply();
                    startActivity(new Intent(SplashInitialActivity.this, SplashHomeInitActivity.class));
                    finish();
                }
            }, 9000);*/
        } else {

            startActivity(new Intent(SplashInitialActivity.this, MainActivity.class));
            finish();
        }
    }
}
