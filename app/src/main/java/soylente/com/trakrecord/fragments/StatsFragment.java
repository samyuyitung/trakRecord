package soylente.com.trakrecord.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import soylente.com.trakrecord.R;
import soylente.com.trakrecord.sensor.DistanceCalculator;
import soylente.com.trakrecord.sensor.StepCounter;


public class StatsFragment extends Fragment {

    private StepCounter stepCounter;
    private DistanceCalculator distanceCalculator;
    private TextView steps;
    private TextView distance;
    private ToggleButton toggleReading;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepCounter = new StepCounter(getActivity());
//        distanceCalculator = new DistanceCalculator(getActivity());
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        steps = (TextView) view.findViewById(R.id.steps_label);
        distance = (TextView) view.findViewById(R.id.distance_label);

        writeLabels();
        toggleReading = (ToggleButton) view.findViewById(R.id.toggle_reading);
        toggleReading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                toggleReadings(isChecked);
            }
        });
        Button resetButton = (Button) view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepCounter.setTotalSteps(0);
                distanceCalculator.setTotalDistance(0);
                writeLabels();
            }
        });
        return view;
    }

    private void writeLabels(){
        steps.setText("Steps: " + stepCounter.getTotalSteps());
    }

    private void toggleReadings(boolean isChecked) {
        if (isChecked) {
            distanceCalculator.start();
            stepCounter.register();

        } else {
            distanceCalculator.stop();
            stepCounter.unregister();
        }
    }
}
