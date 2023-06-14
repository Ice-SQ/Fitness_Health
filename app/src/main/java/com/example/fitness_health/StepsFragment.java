package com.example.fitness_health;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class StepsFragment extends Fragment implements SensorEventListener {

    private TextView mTextView, mDetector, mCount;
    private SensorManager mSensorManager;
    private Sensor mStepSensor;
    private boolean isSensorAvailable;
    private int stepCount;
    private static final int MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 123;
    private long startTime = 0;
    private int startStepCount = 0;
    private int stepsTaken = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        mTextView = view.findViewById(R.id.textViewStepsCounter);
        mCount = view.findViewById(R.id.displayCount);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (mStepSensor == null) {
            mDetector = view.findViewById(R.id.textViewStepsDetector);
            mDetector.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);
            mCount.setVisibility(View.GONE);

        } else {
            // Check if the app has permission to access the Step Counter sensor
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACTIVITY_RECOGNITION)
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted, register the sensor listener
                mSensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
            }
        }

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        if (isSensorAvailable) {
            mSensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    public void onPause() {
        super.onPause();

        if (isSensorAvailable) {
            mSensorManager.unregisterListener(this);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int stepCount = (int) event.values[0];
            if (startTime == 0) {
                // Set start time and step count
                startTime = SystemClock.elapsedRealtime();
                startStepCount = stepCount;
            } else {
                // Calculate steps taken
                stepsTaken = stepCount - startStepCount;
                // Update the step count TextView
                mCount.setText(String.valueOf(stepsTaken));
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, register the sensor listener
                mSensorManager.registerListener(this, mStepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                // Permission has been denied
            }
        }
    }

}
