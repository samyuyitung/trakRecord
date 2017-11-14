package soylente.com.trakrecord.sensor;

/**
 * Created by Hayes on 3/26/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import soylente.com.trakrecord.R;

import static android.content.Context.SENSOR_SERVICE;

public class StepCounter implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor sensor;
    private int totalSteps;
    private int previousSample;

    private double totalDistance;
    private Location previousLocation = null;

    private GoogleApiClient mGoogleApiClient;

    public StepCounter(Context c) {
        mContext = c;
        initDistance();
        initSteps();
    }

    private void initSteps() {
        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        totalSteps = 0;
        previousSample = -1;
    }

    private void initDistance() {
        totalDistance = 0;

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

                    @Override
                    public void onConnected(Bundle arg0) {
                        // Get last known recent location.
                        try {
                            previousLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                }).build();

    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int d) {
        totalSteps = d;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (previousSample != -1)
            totalSteps += (int) event.values[0] - previousSample;

        previousSample = (int) event.values[0];
        totalDistance += getDeltaDistance();
        updateLabel(totalSteps, totalDistance);

    }

    private double getDeltaDistance() {
        try {
            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            double deltaDistance = 0;
            if (currentLocation != null) {
                deltaDistance = previousLocation.distanceTo(currentLocation);
                previousLocation = currentLocation;
            }
            return deltaDistance;
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void updateLabel(int steps, double distance) {

        TextView txtView1 = (TextView) ((Activity) mContext).findViewById(R.id.steps_label);
        TextView txtView2 = (TextView) ((Activity) mContext).findViewById(R.id.distance_label);
        txtView1.setText("Walked: " + steps + " Steps");
        txtView2.setText("Distance: " + String.format("%.2f", distance) + "m");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void register() {
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    //Called when closed
    public void unregister() {
        mSensorManager.unregisterListener(this);
    }
}

