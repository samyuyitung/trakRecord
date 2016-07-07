package soylente.com.trakrecord.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import java.util.Arrays;

import soylente.com.trakrecord.Adaptor.ImageAdapter;
import soylente.com.trakrecord.R;
import soylente.com.trakrecord.estimote.BeaconID;
import soylente.com.trakrecord.estimote.BeaconStats;
import soylente.com.trakrecord.estimote.EstimoteCloudBeaconDetails;
import soylente.com.trakrecord.estimote.EstimoteCloudBeaconDetailsFactory;
import soylente.com.trakrecord.estimote.ProximityContentManager;

public class BadgeFragment extends Fragment implements View.OnClickListener, ProximityContentManager.Listener {
    private Button scanButton;
    private ProximityContentManager proximityContentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_badge,container,false);

        scanButton = (Button) view.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);

        proximityContentManager = new ProximityContentManager(this.getActivity(),
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 23105, 37595),
                        new BeaconID("B9407130-F5F8-466E-AFF9-25556B57FE6D", 23105, 37595),
                        new BeaconID("B9407730-F5F8-466E-AFF9-25556B57FE6D", 23105, 37595),
                        new BeaconID("B9407230-F5F8-466E-AFF9-25556B57FE6D", 23105, 37595)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(this);
        GridView gridView = (GridView) view.findViewById(R.id.badgeGrid);
        gridView.setAdapter(new ImageAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        scanForBeacon();
    }

    void scanForBeacon(){
        System.out.println("Scanning!");
        proximityContentManager.startContentUpdates();
    }
    @Override
    public void onContentChanged(Object content) {

        if (content != null) {
            EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
            BeaconStats bs = new BeaconStats();
            //Read beacon info
            bs = bs.grabById(beaconDetails.getId());
        }
        proximityContentManager.stopContentUpdates();
    }
}