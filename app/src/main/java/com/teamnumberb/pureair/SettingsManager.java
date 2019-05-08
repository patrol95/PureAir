package com.teamnumberb.pureair;

import android.content.Context;

import java.io.*;

public class SettingsManager {
    private static String fileName = "settings";

    private Context context;
    private SettingsData data = new SettingsData();

    public SettingsManager(Context c) {
        context = c;
        readData();
    }

    public int getAcceptablePollution() {
        return data.acceptablePm25InMicrogramsPerCubeMeter;
    }

    public void setAcceptablePollution(int value) {
        data.acceptablePm25InMicrogramsPerCubeMeter = value;
        saveData();
    }

    public int getAcceptableDistance() {
        return data.acceptableDistanceInMeters;
    }

    public void setAcceptableDistance(int value) {
        data.acceptableDistanceInMeters = value;
        saveData();
    }

    public int getPriority() {
        return data.priority;
    }

    public void setPriority(int value) {
        data.priority = value;
        saveData();
    }

    private void readData() {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            data = (SettingsData) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found, probably first time to use application.");
        } catch (Exception e) {
            System.err.println("We shouldn't get here!");
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            os.close();
            fos.close();
        } catch (Exception e) {
            System.err.println("We shouldn't get here!");
            e.printStackTrace();
        }
    }
}
