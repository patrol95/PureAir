package com.teamnumberb.pureair;

import android.content.Context;
import android.content.res.Resources;

import java.util.*;

import okhttp3.OkHttpClient;

public abstract class PollutionDataCollector {
    protected static OkHttpClient httpClient = new OkHttpClient();

    protected List<PollutionData> currentData = new Vector<>();
    protected Resources res;
    protected boolean isDataReady = false;

    PollutionDataCollector(Context context) {
        res = context.getResources();
    }

    public List<PollutionData> getPollutionData() {
        if (isDataReady) {
            return currentData;
        }
        return null;
    }

    protected abstract void retrieveData();
}
