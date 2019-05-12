package com.teamnumberb.pureair.favourite;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.teamnumberb.pureair.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class AddFavouriteActivity extends AppCompatActivity {
    AddFavouriteManager addFavouriteManager = new AddFavouriteManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourite);

        final MapView mapView = findViewById(R.id.map);
        final GeoPoint startPoint = new GeoPoint(51.10, 17.06);
        final IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(startPoint);
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
    }

    private void showInput(final GeoPoint p) {
        final EditText nameEditText = new EditText(this);

        AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle(R.string.enter_name)
                .setView(nameEditText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = nameEditText.getText().toString();
                        addFavouriteManager.addFavourite(new Place(name, p.getLatitude(), p.getLongitude()));
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
}
