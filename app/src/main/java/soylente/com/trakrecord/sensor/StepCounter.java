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

    public StepCounter (Context c){
        mContext = c;
        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    public long getTotalSteps (){return totalSteps; }

    @Override
    public void onSensorChanged(SensorEvent event) {
        totalSteps = (int)event.values[0];
        TextView txtView = (TextView) ((Activity)mContext).findViewById(R.id.steps_label);
        txtView.setText("Steps: " + totalSteps);

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

