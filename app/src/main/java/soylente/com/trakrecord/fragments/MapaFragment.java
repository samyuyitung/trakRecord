package soylente.com.trakrecord.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import soylente.com.trakrecord.DAO.Camp;
import soylente.com.trakrecord.R;

/**
 * Created by sam on 2016-06-29.
 */

public class MapaFragment extends MapFragment implements OnMapReadyCallback {

    MapView mapView;
    List<Camp> camps = new ArrayList<>();
    GoogleMap mMap;
    private float[] pinColours = {BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_CYAN, BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_ROSE, BitmapDescriptorFactory.HUE_VIOLET, BitmapDescriptorFactory.HUE_YELLOW};
    private int[] routeColours = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED};


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getActivity());

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        initializeMap();
    }

    private void initializeMap() {
        if (mMap == null) {
            mapView = (MapView) getActivity().findViewById(R.id.map);
            mapView.getMapAsync(this);
            //setup markers etc...
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) parent.findViewById(R.id.map);
        return parent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        initializeMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        populateMap(mMap);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void populateMap(GoogleMap googleMap) {
        getCamps();
        if (!camps.isEmpty()) {
            for (Camp c : camps) {
                System.out.println(c.getCampName() + "  " + c.getCoords());
                googleMap.addMarker(new MarkerOptions()
                        .position(c.getCoords())
                        .title(c.getCampName())
                        .icon(BitmapDescriptorFactory.defaultMarker(pinColours[camps.indexOf(c)])));
                if (camps.indexOf(c) != 4)
                    googleMap.addPolyline(new PolylineOptions()
                            .add(c.getCoords(), camps.get((camps.indexOf(c) + 1) % camps.size()).getCoords())

                            .width(10)
                            .color(routeColours[camps.indexOf(c)])
                            .geodesic(true));

            }
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(14));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(camps.get(0).getCoords()));

        }
    }


    private void getCamps() {
    /*        Firebase ref = new Firebase("https://trakrecord.firebaseio.com/Camps");
        // Attach an listener to read the data at our posts reference

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " camps");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    camps.add(postSnapshot.getValue(Camp.class));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });*/

        camps.add(new Camp("Home", 43.637141, -79.406711));
        camps.add(new Camp("1st", 43.650842, -79.373020));
        camps.add(new Camp("2nd", 43.663394, -79.391790));
        camps.add(new Camp("3nd", 43.662827, -79.409837));
        camps.add(new Camp("4nd", 43.647667, -79.414318));
    }

}