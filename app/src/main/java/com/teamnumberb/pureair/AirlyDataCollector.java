package com.teamnumberb.pureair;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import okhttp3.*;

class Location {
    double latitude;
    double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public GeoPoint getLocationAsGeoPoint(){
        return new GeoPoint(getLatitude(), getLongitude());
    }

    public void setGeoPointAsLocation(GeoPoint geoPoint){
        this.latitude = geoPoint.getLatitude();
        this.longitude = geoPoint.getLongitude();
    }
}

public class AirlyDataCollector extends PollutionDataCollector {
    private static String lastReadTime = "airlyLastReadTime";
    private static String lastReadData = "airlyLastReadData";
    private static long rereadTime = 60 * 60 * 1000; //1h
    private String apiKey;
    private Map<Integer, Location> locations = new TreeMap<>();

    AirlyDataCollector(Context c) {
        super(c);
        InputStream inputStream = context.getResources().openRawResource(R.raw.airly_api_key);
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        apiKey = s.hasNext() ? s.next() : "";

        if (shouldRereadData()) {
            retrieveData();
            asyncSaveNewData();
        } else {
            restoreSavedData();
        }
    }

    private boolean shouldRereadData() {
        try {
            FileInputStream fis = context.openFileInput(lastReadTime);
            ObjectInputStream is = new ObjectInputStream(fis);
            Long lastReadTime = (Long) is.readObject();
            long currentTime = System.currentTimeMillis();
            is.close();
            fis.close();

            return (currentTime - lastReadTime) > rereadTime;

        } catch (FileNotFoundException e) {
            return true;
        } catch (Exception e) {
            System.err.println("Unexpected exception while accessing data!");
            e.printStackTrace();
        }
        return true;
    }

    private void retrieveData() {
        String url = getAllLocationsUrl();
        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String data = response.body().string();
                try {
                    JSONArray responseData = new JSONArray(data);
                    parseLocationsJson(responseData);
                    getValuesFromIds();
                } catch (JSONException e) {
                    System.err.println("Invalid JSON data!");
                    throw new IOException(e);
                }
            }
        });
    }

    private void asyncSaveNewData() {
        new Runnable() {
            @Override
            public void run() {
                while (!isDataReady) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                try {
                    FileOutputStream fos =
                            context.openFileOutput(lastReadData, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(currentData);
                    os.close();
                    fos.close();

                    fos = context.openFileOutput(lastReadTime, Context.MODE_PRIVATE);
                    os = new ObjectOutputStream(fos);
                    Long currentTime = System.currentTimeMillis();
                    os.writeObject(currentTime);
                    os.close();
                    fos.close();
                } catch (Exception e) {
                    System.err.println("Unexpected exception while saving data!");
                    e.printStackTrace();
                }
            }
        }.run();
    }

    private void restoreSavedData() {
        try {
            FileInputStream fis = context.openFileInput(lastReadData);
            ObjectInputStream is = new ObjectInputStream(fis);
            currentData = (Vector<PollutionData>) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found?!");
        } catch (Exception e) {
            System.err.println("Unexpected exception while accessing data!");
            e.printStackTrace();
        }

        if (currentData != null && !currentData.isEmpty()) {
            isDataReady = true;
        } else {
            retrieveData();
            asyncSaveNewData();
        }
    }

    private String getAllLocationsUrl() {
        return "https://airapi.airly.eu/v2/installations/nearest?lat=51.11&lng=17.022222" +
                "&maxDistanceKM=15&maxResults=-1&apikey=" + apiKey;
    }

    private void parseLocationsJson(JSONArray responseData) {
        for (int i = 0; i < responseData.length(); i++) {
            JSONObject elem = null;
            try {
                elem = responseData.getJSONObject(i);
                int id = elem.getInt("id");
                JSONObject location = elem.getJSONObject("location");
                double latitude = location.getDouble("latitude");
                double longitude = location.getDouble("longitude");
                locations.put(id, new Location(latitude, longitude));
                System.out.println(id + ": lat=" + latitude + ";long=" + longitude);
            } catch (JSONException e) {
                System.err.println("Error in parsing locations JSON");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void getValuesFromIds() {
        Integer[] keys = locations.keySet().toArray(new Integer[locations.keySet().size()]);
        for (int i = 0; i < keys.length; ++i) {
            getPm25FromLocation(keys[i], i == keys.length - 1);
        }
    }

    private void getPm25FromLocation(final int locationId, final boolean isLast) {
        String url = getSingleMeasurementUrl(locationId);
        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String data = response.body().string();
                try {
                    JSONObject responseData = new JSONObject(data);
                    parseSingleMeasurementJson(locationId, responseData);
                    if (isLast) {
                        isDataReady = true;
                    }
                } catch (JSONException e) {
                    System.err.println("Invalid JSON data!");
                    throw new IOException(e);
                }
            }
        });
    }

    private String getSingleMeasurementUrl(int locationId) {
        return "https://airapi.airly.eu/v2/measurements/installation?installationId=" + locationId +
                "&apikey=" + apiKey;
    }

    private void parseSingleMeasurementJson(int locationId, JSONObject responseData) {
        Location location = locations.get(locationId);
        try {
            JSONObject current = responseData.getJSONObject("current");
            JSONArray values = current.getJSONArray("values");
            for (int i = 0; i < values.length(); ++i) {
                JSONObject element = values.getJSONObject(i);
                String name = element.getString("name");
                if (name.equals("PM25")) {
                    double value = element.getDouble("value");
                    PollutionData data = new PollutionData(location.latitude, location.longitude,
                                                           value);
                    currentData.add(data);
                    System.out.println("Pollution data location: " + data.location.toDoubleString() + ", value: " + data.pm25);
                }
            }
        } catch (JSONException e) {
            System.err.println("Error while parsing JSON for locationId " + locationId);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
