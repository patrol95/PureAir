package com.teamnumberb.pureair;

import android.content.Context;
import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.Distance;

import java.io.InputStream;
import java.util.Scanner;

public class LookO2DataCollector extends PollutionDataCollector {
    private static double wroclawCenterLatitude = 51.11;
    private static double wroclawCenterLongitude = 17.022222;
    private static float maxDistanceFromCenter = 15000.f;

    LookO2DataCollector(Context context) {
        super(context);
        retrieveData();
        isDataReady = true;
    }

    private void retrieveData() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.looko2);
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String jsonData = s.hasNext() ? s.next() : "";
        JSONArray data = null;
        try {
            data = new JSONArray(jsonData);
            getRelevantDataFromJson(data);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void getRelevantDataFromJson(JSONArray data) throws JSONException {
        for (int i = 0; i < data.length(); ++i) {
            JSONObject dataPiece = data.getJSONObject(i);
            String latitudeString = dataPiece.getString("Lat");
            String longitudeString = dataPiece.getString("Lon");
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);
            if (pointInRequiredArea(latitude, longitude)) {
                String pm25String = dataPiece.getString("PM25");
                double pm25 = Double.parseDouble(pm25String);
                currentData.add(new PollutionData(latitude, longitude, pm25));
            }
        }
    }

    private boolean pointInRequiredArea(double latitude, double longitude) {
        float[] res = new float[1];
        Location.distanceBetween(wroclawCenterLatitude, wroclawCenterLongitude, latitude,
                                 longitude, res);
        System.out.println("Distance=" + res[0]);
        return res[0] < maxDistanceFromCenter;

    }
}
