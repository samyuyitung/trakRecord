package soylente.com.trakrecord.fragments;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import soylente.com.trakrecord.DAO.Camp;
import soylente.com.trakrecord.R;

import static android.content.Context.LOCATION_SERVICE;

public class CampFragment extends Fragment implements OnMapReadyCallback {

    private Location mLocation;
    private MapView campMap;
    private Camp camp;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            mLocation = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderDisabled(String arg0) {
        }

        @Override
        public void onProviderEnabled(String arg0) {
        }
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        camp = new Camp("1st", 43.650842, -79.373020);
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (SecurityException e) {
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camp, container, false);
        TextView title = (TextView) view.findViewById(R.id.camp_title);
        title.setText(camp.getCampName());
        TextView distanceFrom = (TextView) view.findViewById(R.id.camp_distance_label);
        setDistanceFrom(distanceFrom);
        campMap = (MapView) view.findViewById(R.id.camp_view);
        campMap.onCreate(savedInstanceState);
        campMap.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(camp.getCoords()));

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(20));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(camp.getCoords()));
        campMap.onResume();
    }

    void setDistanceFrom(TextView distanceFrom) {
        float distance = -12;
        Location campLoc = new Location("");
        campLoc.setLatitude(camp.getLatitude());
        campLoc.setLongitude(camp.getLongitude());
        if(mLocation != null && camp.getCoords() != null)
            distance = mLocation.distanceTo(campLoc);
        distanceFrom.setText("Distance from: " + distance + " km");
    }


}