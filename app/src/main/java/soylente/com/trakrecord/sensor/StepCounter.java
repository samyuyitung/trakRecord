package soylente.com.trakrecord.sensor;

/**
 * Created by Hayes on 3/26/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import soylente.com.trakrecord.R;

import static android.content.Context.SENSOR_SERVICE;

public class StepCounter implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor sensor;
    private int totalSteps;
    private int previousSample;
    DistanceCalculator dc;
    public StepCounter (Context c){
        mContext = c;
        dc = new DistanceCalculator(mContext);
        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        totalSteps = 0;
        previousSample = -1;
    }

    public int getTotalSteps (){ return totalSteps; }

    public void setTotalSteps(int d){ totalSteps = d; }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(previousSample != -1)
            totalSteps += (int)event.values[0] - previousSample;

        previousSample = (int)event.values[0];
        updateLabel(totalSteps,  dc.getTotalDistance());

    }
    private void updateLabel(int steps, double distance){

        TextView txtView1 = (TextView) ((Activity)mContext).findViewById(R.id.steps_label);
        TextView txtView2 = (TextView) ((Activity)mContext).findViewById(R.id.distance_label);
        txtView1.setText("Walked: " + steps + " Steps");
        txtView2.setText("Distance: " + String.format("%.2f", distance) + "m");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }



    public void register(){
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
    //Called when closed
    public void unregister(){
        mSensorManager.unregisterListener(this);
    }
}

