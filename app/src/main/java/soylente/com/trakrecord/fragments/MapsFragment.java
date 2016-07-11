package soylente.com.trakrecord.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import soylente.com.trakrecord.DAO.Camp;
import soylente.com.trakrecord.R;

public class MapsFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    List<Camp> camps = new ArrayList<>();
    private float[] pinColours = {BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_CYAN, BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_ROSE, BitmapDescriptorFactory.HUE_VIOLET, BitmapDescriptorFactory.HUE_YELLOW};
    private int[] routeColours = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        populateMap(googleMap);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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