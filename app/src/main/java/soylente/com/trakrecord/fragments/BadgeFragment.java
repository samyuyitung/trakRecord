package soylente.com.trakrecord.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;

import java.util.Arrays;
import java.util.List;

import soylente.com.trakrecord.Adaptor.ImageAdapter;
import soylente.com.trakrecord.R;
import soylente.com.trakrecord.estimote.BeaconID;
import soylente.com.trakrecord.estimote.BeaconStats;
import soylente.com.trakrecord.estimote.EstimoteCloudBeaconDetails;
import soylente.com.trakrecord.estimote.EstimoteCloudBeaconDetailsFactory;
import soylente.com.trakrecord.estimote.ProximityContentManager;


//TODO: Camp images and shit, saying this is the camp unlock this badge

public class BadgeFragment extends Fragment implements View.OnClickListener, ProximityContentManager.Listener {
    private Button scanButton;
    private ProximityContentManager proximityContentManager;
    private GridView gridView;
    private ImageAdapter iAdaptor;
    private BluetoothAdapter mBluetoothAdapter;
    private FragmentManager fragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_badge, container, false);

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
        iAdaptor = new ImageAdapter(view.getContext());

        gridView = (GridView) view.findViewById(R.id.badgeGrid);
        gridView.setAdapter(iAdaptor); // uses the view to get the context instead of getActivity().
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                CampFragment campFrag = new CampFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, campFrag)
                        .commit();
                System.out.println("Starting frag");
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    void startScanning() {

        if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled())
            proximityContentManager.startContentUpdates();
        unlockCamp(2);
    }


    @Override
    public void onClick(View v) {
        startScanning();
    }

    @Override
    public void onContentChanged(Object content) {

        if (content != null) {
            EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
            BeaconStats bs = new BeaconStats();
            //Read beacon info
            bs = bs.grabById(beaconDetails.getId());
            unlockCamp(bs.getCampNumber());
            proximityContentManager.stopContentUpdates();
        }
    }

    private void unlockCamp(int campNumber) {
        iAdaptor.updateImage(campNumber, 1);
        iAdaptor.notifyDataSetChanged();
    }
}