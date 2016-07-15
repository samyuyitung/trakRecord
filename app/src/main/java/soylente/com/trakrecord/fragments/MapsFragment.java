package soylente.com.trakrecord.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import soylente.com.trakrecord.DAO.Camp;
import soylente.com.trakrecord.R;

public class MapsFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    ArrayList<Camp> camps;
    private float[] pinColours = {BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_CYAN, BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_ROSE, BitmapDescriptorFactory.HUE_VIOLET, BitmapDescriptorFactory.HUE_YELLOW};
    private int[] routeColours = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camps = getArguments().getParcelableArrayList("CAMPS");
    }

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
        if(mMapView != null)
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void populateMap(GoogleMap googleMap) {
        if (!camps.isEmpty()) {
            for (Camp c : camps) {
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
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker arg0) {
                    Bundle bundle = new Bundle();
                    CampFragment campFrag = new CampFragment();
                    for (Camp c : camps)
                        if (c.getCampName().equals(arg0.getTitle())) {
                            bundle.putParcelable("CAMP", c);
                            campFrag.setArguments(bundle);
                            break;
                        }

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, campFrag)
                            .addToBackStack(null)
                            .commit();
                }
            });
            try {
                googleMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {

            }
        }
    }

}