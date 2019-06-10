package com.teamnumberb.pureair.favourite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.teamnumberb.pureair.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.io.IOException;
import java.util.List;

public class AddFavouriteActivity extends AppCompatActivity implements LocationListener {
    private FavouritesManager favouritesManager;
    private LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourite);
        favouritesManager = new FavouritesManager(this);

        final MapView mapView = findViewById(R.id.map);
        final GeoPoint startPoint = new GeoPoint(51.10, 17.06);
        final IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(startPoint);
        mapView.setMultiTouchControls(true);
        mapView.setFlingEnabled(true);
        RotationGestureOverlay rotationGestureOverlay = new RotationGestureOverlay(mapView);
        rotationGestureOverlay.setEnabled(true);
        mapView.getOverlays().add(rotationGestureOverlay);
        mapView.invalidate();
        mapView.getOverlayManager().add(new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Marker marker = new Marker(mapView);
                marker.setPosition(p);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                mapView.getOverlays().add(marker);
                mapController.animateTo(p);
                showInput(p);
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        }));

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("MissingPermission")
            @Override
            public boolean onQueryTextSubmit(String s) {
                GeocoderNominatim geocoderNominatim = new GeocoderNominatim("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
                try {
                    Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    final List<Address> addresses = geocoderNominatim.getFromLocationName(
                            s,
                            3,
                            location.getLatitude() - 0.3,
                            location.getLongitude() - 0.3,
                            location.getLatitude() + 0.3,
                            location.getLongitude() + 0.3
                    );
                    final TextView textView1 = findViewById(R.id.searchResultTextView1);
                    final TextView textView2 = findViewById(R.id.searchResultTextView2);
                    final TextView textView3 = findViewById(R.id.searchResultTextView3);
                    final Marker marker = new Marker(mapView);
                    hideResults(textView1, textView2, textView3);
                    if (addresses.size() > 0) {
                        textView1.setVisibility(View.VISIBLE);
                        textView1.setText(getAddressString(addresses.get(0)));
                        textView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GeoPoint p = new GeoPoint(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                                marker.setPosition(p);
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                mapView.getOverlays().add(marker);
                                mapController.animateTo(p);
                                showInput(p);
                                hideResults(textView1, textView2, textView3);
                            }
                        });
                    }
                    if (addresses.size() > 1) {
                        textView2.setVisibility(View.VISIBLE);
                        textView2.setText(getAddressString(addresses.get(1)));
                        textView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GeoPoint p = new GeoPoint(addresses.get(1).getLatitude(), addresses.get(1).getLongitude());
                                marker.setPosition(p);
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                mapView.getOverlays().add(marker);
                                mapController.animateTo(p);
                                showInput(p);
                                hideResults(textView1, textView2, textView3);
                            }
                        });
                    }
                    if (addresses.size() > 2) {
                        textView3.setVisibility(View.VISIBLE);
                        textView3.setText(getAddressString(addresses.get(2)));
                        textView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GeoPoint p = new GeoPoint(addresses.get(2).getLatitude(), addresses.get(2).getLongitude());
                                marker.setPosition(p);
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                mapView.getOverlays().add(marker);
                                mapController.animateTo(p);
                                showInput(p);
                                hideResults(textView1, textView2, textView3);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private String getAddressString(Address address) {
        StringBuilder addressString = new StringBuilder();
        int i = 0;
        while (i < address.getMaxAddressLineIndex()) {
            addressString.append(address.getAddressLine(i));
            addressString.append(", ");
            i++;
        }
        return addressString.toString();
    }

    private void hideResults(TextView textView1, TextView textView2, TextView textView3) {
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
    }

    private void showInput(final GeoPoint p) {
        final EditText nameEditText = new EditText(this);

        AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle(R.string.enter_name)
                .setView(nameEditText)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = nameEditText.getText().toString();
                        favouritesManager.addFavourite(new Place(name, p.getLatitude(), p.getLongitude()));
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                }).create();
        Window window = dlg.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dlg.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            lm.removeUpdates(this);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            //this fails on AVD 19s, even with the appcompat check, says no provided named gps is available
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0l, 0f, this);
        } catch (Exception ex) {
        }

        try {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0l, 0f, this);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
