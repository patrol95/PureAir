package com.teamnumberb.pureair;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

}
