package com.teamnumberb.pureair;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.teamnumberb.pureair.favourite.FavouritesAdapter;
import com.teamnumberb.pureair.splash_screens.SplashInitialActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;

public class MainActivity extends AppCompatActivity implements HomeFragment.IFavouriteSelectListener, FavouritesAdapter.IFavoriteAdapterClick {


    DirectionsFragment directionsFragment;
    HomeFragment homeFragment;
    SettingsFragment settingsFragment;
    private static final String DIRECTIONS_TAG = "directions";
    private static final String HOME_TAG = "home";
    private static final String SETTINGS_TAG = "settings";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_settings:
                    /*Fragment settingsFragment = fragmentManager.findFragmentByTag("settings");
                    settingsFragment = settingsFragment != null ? settingsFragment : SettingsFragment.newInstance();*/
                    if (settingsFragment == null)
                        settingsFragment = new SettingsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, settingsFragment, SETTINGS_TAG)
                            .commitNow();
                    return true;
                case R.id.navigation_home:
                    /*Fragment homeFragment = fragmentManager.findFragmentByTag("home");
                    homeFragment = homeFragment != null ? homeFragment : HomeFragment.newInstance();*/
                    if (homeFragment == null)
                        homeFragment = new HomeFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, homeFragment, HOME_TAG)
                            .commitNow();
                    return true;
                case R.id.navigation_directions:
                    /*Fragment directionsFragment = fragmentManager.findFragmentByTag("directions");
                    directionsFragment = directionsFragment != null ? directionsFragment : DirectionsFragment.newInstance();*/
                    if (directionsFragment == null)
                        directionsFragment = new DirectionsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, directionsFragment, DIRECTIONS_TAG)
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


        if (!hasPermissions(this, PERMISSIONS)) {
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
    public void selectLocation(GeoPoint geoPoint) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (directionsFragment == null)
            directionsFragment = new DirectionsFragment();

        directionsFragment.navigateFromFavourites(geoPoint);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_directions);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, directionsFragment, DIRECTIONS_TAG)
                .commitNow();
    }


    @Override
    public void favGeoPoint(GeoPoint geoPoint) {
        selectLocation(geoPoint);
    }

    public void displaySplashCreens(){
    }
}


