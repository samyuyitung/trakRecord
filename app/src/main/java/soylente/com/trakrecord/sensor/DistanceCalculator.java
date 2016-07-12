package soylente.com.trakrecord.sensor;

/**
 * Created by Hayes on 3/26/2016.
 */

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import soylente.com.trakrecord.R;

import static android.content.Context.LOCATION_SERVICE;

public class DistanceCalculator implements LocationListener {

    private LocationManager mLocationManager;
    private Context mContext;
    private double totalDistance;
    private Location previousLocation = null;
    public DistanceCalculator(Context c) {
        mContext = c;
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        totalDistance = 0;
    }

    public double getTotalDistance() {return totalDistance; }

    private void updateLabel(Double distance){

        TextView txtView = (TextView) ((Activity) mContext).findViewById(R.id.distance_label);
        txtView.setText( "Distance: " + String.format( "%.2f", distance )+ "m" );
    }
    @Override
    public void onLocationChanged(Location currentLocation) {
        if (currentLocation != null)
                totalDistance += previousLocation.distanceTo(currentLocation);
        updateLabel(totalDistance);
        previousLocation = currentLocation;
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }


    public void start() {
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            previousLocation = getLocation();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            mLocationManager.removeUpdates(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    public Location getLocation() {
        Location location = null;
        try {
            // getting GPS status
            boolean isGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                if (isNetworkEnabled) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1000,
                            1, this);
                    Log.d("Network", "Network Enabled");
                    if (mLocationManager != null) {
                        location = mLocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1, this);
                        Log.d("GPS", "GPS Enabled");
                        if (mLocationManager != null)
                            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return location;
    }


}
