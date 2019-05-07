package com.teamnumberb.pureair;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import org.osmdroid.config.Configuration;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_settings:
                    Fragment settingsFragment = fragmentManager.findFragmentByTag("settings");
                    settingsFragment = settingsFragment != null ? settingsFragment : SettingsFragment.newInstance();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, settingsFragment, "settings")
                            .commitNow();
                    return true;
                case R.id.navigation_home:
                    Fragment homeFragment = fragmentManager.findFragmentByTag("home");
                    homeFragment = homeFragment != null ? homeFragment : HomeFragment.newInstance();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, homeFragment, "home")
                            .commitNow();
                    return true;
                case R.id.navigation_directions:
                    Fragment directionsFragment = fragmentManager.findFragmentByTag("directions");
                    directionsFragment = directionsFragment != null ? directionsFragment : DirectionsFragment.newInstance();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, directionsFragment, "directions")
                            .commitNow();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance())
                    .commitNow();
        }

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);

            }
        } else {

        }


    }

}
