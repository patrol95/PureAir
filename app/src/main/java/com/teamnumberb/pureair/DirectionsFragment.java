package com.teamnumberb.pureair;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.GraphHopperRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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
            return 0x7700b050;
        if (pm25 < 36)
            return 0x7792d050;
        if (pm25 < 60)
            return 0x77ffff00;
        if (pm25 < 85)
            return 0x77ff7000;
        if (pm25 < 120)
            return 0x77ff0000;
        return 0x77c00000;
    }
}

public class DirectionsFragment extends Fragment implements LocationListener {

    public static DirectionsFragment newInstance() {
        return new DirectionsFragment();
    }

    private MapView mMapView;
    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private LocationManager lm;
    private Location currentLocation;
    private SettingsManager settingsManager;
    private PollutionDataCollector airlyData;
    private GeoPoint endPoint = null;
    private GeoPoint favouritePoint = null;
    private GeoPoint defaultWroclawPoint = new GeoPoint(51.10, 17.06);
    private Polyline roadOverlay = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        airlyData = new AirlyDataCollector(context);
        settingsManager = new SettingsManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_directions, null);
        mMapView = v.findViewById(R.id.map);

        asyncAddPollutionDataToMap();

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final IMapController mapController = mMapView.getController();
        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        final Marker marker = new Marker(mMapView);


        this.mCompassOverlay =
                new CompassOverlay(context, new InternalCompassOrientationProvider(context),
                                   mMapView);
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),
                                                         mMapView);

        mScaleBarOverlay = new ScaleBarOverlay(mMapView);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);


        mapController.setZoom(15.0);
        mapController.setCenter(defaultWroclawPoint);
        mMapView.setTilesScaledToDpi(true);
        mMapView.setMultiTouchControls(true);
        mMapView.setFlingEnabled(true);
        mMapView.getOverlays().add(this.mLocationOverlay);
        mMapView.getOverlays().add(this.mCompassOverlay);
        mMapView.getOverlays().add(this.mScaleBarOverlay);

        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableFollowLocation();
        mLocationOverlay.setOptionsMenuEnabled(true);
        mCompassOverlay.enableCompass();


        mRotationGestureOverlay = new RotationGestureOverlay(mMapView);
        mRotationGestureOverlay.setEnabled(true);
        mMapView.getOverlays().add(mRotationGestureOverlay);

        if (favouritePoint != null) {
            marker.setPosition(favouritePoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mMapView.getOverlays().add(marker);
            mapController.animateTo(favouritePoint);
            endPoint = favouritePoint;
        }


        view.findViewById(R.id.fab_navigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLocation != null) {
                    if (endPoint == null)
                        Toast.makeText(getContext(),
                                       "Please select your destination",
                                       Toast.LENGTH_LONG).show();
                    else {


                        if (roadOverlay != null)
                            mMapView.getOverlays().remove(roadOverlay);

                        Road bestRoute = selectBestRoute();

                        roadOverlay = RoadManager.buildRoadOverlay(bestRoute);
                        roadOverlay.setWidth(20);
                        GeoPoint midPoint =
                                new GeoPoint((currentLocation.getLatitude() + endPoint.getLatitude()) / 2,
                                             (currentLocation.getLongitude() + endPoint.getLongitude()) / 2);
                        mapController.animateTo(midPoint);
                        double distance = roadOverlay.getDistance();

                        if (distance < 3000)
                            mapController.setZoom(15.0);
                        else if (distance < 9000)
                            mapController.setZoom(13.0);
                        else
                            mapController.setZoom(11.0);


                        mMapView.getOverlays().add(roadOverlay);
                        mMapView.invalidate();
                    }
                } else
                    Toast.makeText(getContext(), "Current location unavailable", Toast.LENGTH_LONG)
                            .show();
            }


        });

        view.findViewById(R.id.fab_go_to_my_location)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentLocation != null) {
                            GeoPoint myLocation = new GeoPoint((currentLocation.getLatitude()),
                                                               (currentLocation.getLongitude()));
                            mapController.animateTo(myLocation);
                            mapController.setZoom(15.0);
                        } else
                            Toast.makeText(getContext(),
                                           "Current location unavailable",
                                           Toast.LENGTH_LONG).show();
                        return;
                    }
                });


        mMapView.getOverlayManager().add(new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {

                marker.setPosition(p);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                mMapView.getOverlays().add(marker);
                mapController.animateTo(p);
                endPoint = p;
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        }));


    }


    public void navigateFromFavourites(GeoPoint geoPoint) {
        favouritePoint = geoPoint;
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            lm.removeUpdates(this);
        } catch (Exception ex) {
        }

        mCompassOverlay.disableCompass();
        mLocationOverlay.disableFollowLocation();
        mLocationOverlay.disableMyLocation();
        mScaleBarOverlay.enableScaleBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            //this fails on AVD 19s, even with the appcompat check, says no provided named gps is
            // available
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0l, 0f, this);
        } catch (Exception ex) {
        }

        try {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0l, 0f, this);
        } catch (Exception ex) {
        }

        mLocationOverlay.enableFollowLocation();
        mLocationOverlay.enableMyLocation();
        mScaleBarOverlay.disableScaleBar();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lm = null;
        currentLocation = null;

        mLocationOverlay = null;
        mCompassOverlay = null;
        mScaleBarOverlay = null;
        mRotationGestureOverlay = null;
    }

    private void asyncAddPollutionDataToMap() {
        PollutionDataListener t = new PollutionDataListener(mMapView, airlyData);
        t.run();
    }

    private Road selectBestRoute() {
        final String apiKey = getGraphhopperApiKey();
        GraphHopperRoadManager roadManager = new GraphHopperRoadManager(apiKey, false);
        roadManager.addRequestOption("vehicle=bike");

        ArrayList<GeoPoint> waypoints = new ArrayList<>();
        GeoPoint startPoint = new GeoPoint(currentLocation);

        if (shouldPrioritizeLowPollution()) {
            waypoints.add(startPoint);
            waypoints.addAll(getMidWaypoints());
            waypoints.add(endPoint);

            return roadManager.getRoad(waypoints);
        }

        return getShortestRouteWithLowestPollution(roadManager);
    }

    private boolean shouldPrioritizeLowPollution() {
        int maxPriority = getResources().getInteger(R.integer.maxPriority);
        int treshold = maxPriority / 2;
        int userChoice = settingsManager.getPriority();
        return userChoice < treshold;
    }

    private String getGraphhopperApiKey() {
        InputStream inputStream = getResources().openRawResource(R.raw.graphhopper_api_key);
        Scanner s = new Scanner(inputStream);
        return s.hasNext() ? s.next() : "";
    }

    private ArrayList<GeoPoint> getMidWaypoints() {
        ArrayList<PollutionData> pointsInSquare = getAllPollutionPointsWithinSquare();
        Collections.sort(pointsInSquare, new Comparator<PollutionData>() {
                             @Override
                             public int compare(PollutionData a, PollutionData b) {
                                 return Double.compare(a.pm25, b.pm25);
                             }
                         }
        );

        ArrayList<GeoPoint> result = new ArrayList<>();
        for (int i = 0; i < 3 && i < pointsInSquare.size(); ++i) {
            if (pmValueIsAcceptable(pointsInSquare.get(i).pm25)) {
                result.add(pointsInSquare.get(i).location);
            }
        }

        Collections.sort(result, new Comparator<GeoPoint>() {
            @Override
            public int compare(GeoPoint p1, GeoPoint p2) {
                float[] r1 = new float[1];
                float[] r2 = new float[1];
                Location.distanceBetween(currentLocation.getLatitude(),
                                         currentLocation.getLongitude(), p1.getLatitude(),
                                         p1.getLongitude(), r1);
                Location.distanceBetween(currentLocation.getLatitude(),
                                         currentLocation.getLongitude(), p2.getLatitude(),
                                         p2.getLongitude(), r2);
                System.out.println("Distance1: " + r1[0] + ", Distance2: " + r2[0]);
                return Float.compare(r1[0], r2[0]);
            }
        });

        return result;
    }

    private ArrayList<PollutionData> getAllPollutionPointsWithinSquare() {
        double startLat = currentLocation.getLatitude();
        double startLong = currentLocation.getLongitude();
        double endLat = endPoint.getLatitude();
        double endLong = endPoint.getLongitude();

        double minLat = startLat < endLat ? startLat : endLat;
        double maxLat = startLat > endLat ? startLat : endLat;
        double minLong = startLong < endLong ? startLong : endLong;
        double maxLong = startLong > endLong ? startLong : endLong;

        List<PollutionData> data = airlyData.getPollutionData();
        ArrayList<PollutionData> results = new ArrayList<>();
        for (PollutionData point : data) {
            double lat = point.location.getLatitude();
            double lon = point.location.getLongitude();
            if (minLat < lat && lat < maxLat &&
                    minLong < lon && lon < maxLong) {
                results.add(point);
            }
        }
        return results;
    }

    private boolean pmValueIsAcceptable(double pm25) {
        double acceptablePollution = settingsManager.getAcceptablePollution();
        return pm25 < acceptablePollution;
    }

    private Road getShortestRouteWithLowestPollution(RoadManager roadManager) {
        ArrayList<GeoPoint> waypoints = new ArrayList<>();
        GeoPoint startPoint = new GeoPoint(currentLocation);
        waypoints.add(startPoint);
        waypoints.add(endPoint);
        Road shortestRoute = roadManager.getRoad(waypoints);

        int maxAcceptableOverheadDistanceInMeters = settingsManager.getAcceptableDistance();
        double maxAcceptableOverheadDistanceInKM = maxAcceptableOverheadDistanceInMeters / 1000.;
        double maxAcceptableDistance = shortestRoute.mLength + maxAcceptableOverheadDistanceInKM;

        waypoints.clear();

        waypoints.add(startPoint);
        waypoints.addAll(getMidWaypoints());
        waypoints.add(endPoint);
        Road triedRoute = roadManager.getRoad(waypoints);

        if (triedRoute.mLength > maxAcceptableDistance) {
            Toast toast = Toast.makeText(getContext(), "Lowest pollution route is too long,\n" +
                    "selecting shortest route", Toast.LENGTH_LONG);
            View view = toast.getView();
            view.setBackgroundColor(0xFF000000);
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(0xFFFFFFFF);
            toast.show();

            return shortestRoute;
        }
        return triedRoute;
    }

}
