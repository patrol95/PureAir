package com.teamnumberb.pureair;

import android.Manifest;
import android.content.Context;
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
import org.osmdroid.util.GeoPoint;

public class MainActivity extends AppCompatActivity implements HomeFragment.FavouriteSelectListener{


    DirectionsFragment directionsFragment;
    HomeFragment homeFragment;
    private static final String DIR_TAG = "dir";
    private static final String HOME_TAG = "home";

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);




        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    public void selectLocation(GeoPoint geoPoint){
        directionsFragment.navigateFromFavourites(geoPoint);
    }
}


