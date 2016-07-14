package soylente.com.trakrecord.sensor;

/**
 * Created by Hayes on 3/26/2016.
 */

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import soylente.com.trakrecord.R;


public class DistanceCalculator {

    private Context mContext;
    private double totalDistance;
    private Location previousLocation = null;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 2000;  /* 10 secs */
    private long FASTEST_INTERVAL = 100; /* 2 sec */

    private LocationListener mLocationListener;

    public DistanceCalculator(Context c) {
        mContext = c;
        totalDistance = 0;
        mLocationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location currentLocation) {
                if (currentLocation != null)
                    totalDistance += previousLocation.distanceTo(currentLocation);

                updateLabel(totalDistance);
                previousLocation = currentLocation;
            }
        };

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

                    @Override
                    public void onConnected(Bundle arg0) {
                        // Get last known recent location.
                        try {
                            previousLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            startLocationUpdates();
                        } catch (SecurityException e) {
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                }).build();

    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double d) {
        totalDistance = d;
    }


    private void updateLabel(Double distance) {
        TextView txtView = (TextView) ((Activity) mContext).findViewById(R.id.distance_label);
        txtView.setText("Distance: " + String.format("%.2f", distance) + "m");
    }

    protected void startLocationUpdates() throws SecurityException {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, mLocationListener);
    }

    public void start() {
        mGoogleApiClient.connect();
    }

    public void stop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);

        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();

    }
}
