package com.teamnumberb.pureair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

public class AddFavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);

        MapView mapView = findViewById(R.id.map);
        GeoPoint startPoint = new GeoPoint(51.10, 17.06);
        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(startPoint);
        mapView.invalidate();
        mapView.getOverlayManager().add(new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(AddFavouriteActivity.this, "TODO: Save to favourites: " + p.toDoubleString(), Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        }));
    }
}
