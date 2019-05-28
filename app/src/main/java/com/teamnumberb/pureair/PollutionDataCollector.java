package com.teamnumberb.pureair;

import android.content.Context;

import java.util.*;

import okhttp3.OkHttpClient;

public abstract class PollutionDataCollector {
    protected static OkHttpClient httpClient = new OkHttpClient();

    protected List<PollutionData> currentData = new Vector<>();
    protected Context context;
    protected boolean isDataReady = false;

    PollutionDataCollector(Context context) {
        this.context = context;
    }

    public List<PollutionData> getPollutionData() {
        if (isDataReady) {
            return currentData;
        }
        return null;
    }
}
