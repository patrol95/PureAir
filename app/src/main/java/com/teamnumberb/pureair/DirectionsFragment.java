package com.teamnumberb.pureair;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;

import java.util.List;

class PollutionDataListener implements Runnable {
    private MapView mapView;
    private PollutionDataCollector collector;
    private List<PollutionData> pollutionData = null;

    public PollutionDataListener(MapView mapView,
                                 PollutionDataCollector collector) {
        this.mapView = mapView;
        this.collector = collector;
    }

    @Override
    public void run() {
        do {
            pollutionData = collector.getPollutionData();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (pollutionData == null);

        addPollutionDataToMap();
    }

    private void addPollutionDataToMap() {
        for (PollutionData data : pollutionData) {
            Polygon circle = new Polygon(mapView);
            circle.setPoints(Polygon.pointsAsCircle(data.location, 100.0));
            circle.setFillColor(getColorCodeOfPmValue(data.pm25));
            circle.setStrokeWidth(0);
            mapView.getOverlays().add(circle);
        }
        mapView.invalidate();
    }

    private int getColorCodeOfPmValue(double pm25) {
        if (pm25 < 12)
            return 0xFF00b050;
        if (pm25 < 36)
            return 0xFF92d050;
        if (pm25 < 60)
            return 0xFFffff00;
        if (pm25 < 85)
            return 0xFFff7000;
        if (pm25 < 120)
            return 0xFFff0000;
        return 0xFFc00000;
    }
}

public class DirectionsFragment extends Fragment {
    private PollutionDataCollector airlyData;
    private MapView map;


    public static DirectionsFragment newInstance() {
        return new DirectionsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        airlyData = new AirlyDataCollector(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                                              Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                                                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                                                  new String[]{
                                                          Manifest.permission.ACCESS_FINE_LOCATION},
                                                  5);

            }
        } else {

        }


        View view;
        view = inflater.inflate(R.layout.fragment_directions, null);
        ;


        map = (MapView) view.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(51.10, 17.06);
        IMapController mapController = map.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(startPoint);
        map.invalidate();

        asyncAddPollutionDataToMap();

        return view;
    }

    private void asyncAddPollutionDataToMap() {
        PollutionDataListener t = new PollutionDataListener(map, airlyData);
        t.run();
    }
}