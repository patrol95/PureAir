package com.teamnumberb.pureair;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance() { return new SettingsFragment(); }

    private SettingsManager settingsManager;
    private View currentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        settingsManager = new SettingsManager(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        settingsManager = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_settings, null);
        setCurrentValuesForBars();
        setCurrentValuesForTextViews();
        setupProgressBarsListeners();
        return currentView;
    }

    private void setCurrentValuesForBars() {
        SeekBar pollutionBar = currentView.findViewById(R.id.pollution_seekBar);
        pollutionBar.setProgress(settingsManager.getAcceptablePollution());

        SeekBar distanceBar = currentView.findViewById(R.id.distance_seekBar);
        distanceBar.setProgress(settingsManager.getAcceptableDistance());

        SeekBar priorityBar = currentView.findViewById(R.id.prioritize_SeekBar);
        priorityBar.setProgress(settingsManager.getPriority());
    }

    private void setCurrentValuesForTextViews() {
        setTextForAcceptablePollution();
        setTextForAcceptableDistance();
    }

    private void setTextForAcceptableDistance() {
        TextView distanceTolerance = currentView.findViewById(R.id.distance_tolerance_value);
        distanceTolerance.setText(String.valueOf(settingsManager.getAcceptableDistance()));
    }

    private void setTextForAcceptablePollution() {
        TextView pollutionTolerance = currentView.findViewById(R.id.pollution_tolerance_value);
        pollutionTolerance.setText(String.valueOf(settingsManager.getAcceptablePollution()));
    }

    private void setupProgressBarsListeners() {
        setAcceptablePollutionChangeListener();
        setAcceptableDistanceChangeListener();
        setPriorityChangeListener();
    }

    private void setAcceptablePollutionChangeListener() {
        SeekBar bar = currentView.findViewById(R.id.pollution_seekBar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean inputFromUser) {
                if(inputFromUser) {
                    settingsManager.setAcceptablePollution(progress);
                    setTextForAcceptablePollution();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setAcceptableDistanceChangeListener() {
        SeekBar bar = currentView.findViewById(R.id.distance_seekBar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean inputFromUser) {
                if(inputFromUser) {
                    settingsManager.setAcceptableDistance(progress);
                    setTextForAcceptableDistance();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void setPriorityChangeListener() {
        SeekBar bar = currentView.findViewById(R.id.prioritize_SeekBar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean inputFromUser) {
                if(inputFromUser) {
                    settingsManager.setPriority(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}