package soylente.com.trakrecord.fragments;


import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import soylente.com.trakrecord.Adaptor.ImageAdapter;
import soylente.com.trakrecord.DAO.Camp;
import soylente.com.trakrecord.R;
import soylente.com.trakrecord.estimote.BeaconID;
import soylente.com.trakrecord.estimote.EstimoteCloudBeaconDetails;
import soylente.com.trakrecord.estimote.EstimoteCloudBeaconDetailsFactory;
import soylente.com.trakrecord.estimote.ProximityContentManager;


public class BadgeFragment extends Fragment implements View.OnClickListener, ProximityContentManager.Listener {
    private ProximityContentManager proximityContentManager;

    private ImageAdapter iAdaptor = null;
    private BluetoothAdapter mBluetoothAdapter;
    private FragmentManager fragmentManager;
    private ArrayList<BeaconID> beaconsList;
    private ArrayList<Camp> campList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        campList = getArguments().getParcelableArrayList("CAMPS");
        beaconsList = getArguments().getParcelableArrayList("BEACONS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_badge, container, false);

        Button scanButton = (Button) view.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);

        if (!beaconsList.isEmpty()) {
            proximityContentManager = new ProximityContentManager(this.getActivity(), beaconsList,
                    new EstimoteCloudBeaconDetailsFactory());
            proximityContentManager.setListener(this);
        }
        if (iAdaptor == null)
            iAdaptor = new ImageAdapter(view.getContext());

        GridView gridView = (GridView) view.findViewById(R.id.badgeGrid);
        gridView.setAdapter(iAdaptor); // uses the view to get the context instead of getActivity().
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                if (campList.size() > position) {
                    Bundle bundle = new Bundle();
                    CampFragment campFrag = new CampFragment();
                    bundle.putParcelable("CAMP", campList.get(position));
                    campFrag.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, campFrag)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void startScanning() {

        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            proximityContentManager.startContentUpdates();
            Toast.makeText(getActivity(), "starting beacons", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        startScanning();
    }

    @Override
    public void onContentChanged(Object content) {

        if (content != null) {
            EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
            for (Camp c : campList) {
                if (c.getBeaconID() == beaconDetails.getId()) {
                    unlockCamp(c.getCampNumber());
                    //Stop scanning for beacons
                    proximityContentManager.stopContentUpdates();
                }
            }
        }
    }

    private void unlockCamp(int campNumber) {
        iAdaptor.updateImage(campNumber, 1);
        iAdaptor.notifyDataSetChanged();
        campList.get(campNumber).setFound(true);
    }
}