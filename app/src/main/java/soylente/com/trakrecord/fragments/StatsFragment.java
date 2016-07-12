package soylente.com.trakrecord.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import soylente.com.trakrecord.R;
import soylente.com.trakrecord.sensor.StepCounter;


public class StatsFragment extends Fragment {

    StepCounter stepCounter;
    private TextView steps;
    private TextView distance;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepCounter = new StepCounter(getActivity());
        stepCounter.register();
        getActivity().setTitle("Statistics");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        steps = (TextView) view.findViewById(R.id.steps_label);
        steps.setText("Steps: " + stepCounter.getTotalSteps());
        distance = (TextView) view.findViewById(R.id.distance_label);
        distance.setText("Distance");
        return view;

    }


    //Step counter


}
