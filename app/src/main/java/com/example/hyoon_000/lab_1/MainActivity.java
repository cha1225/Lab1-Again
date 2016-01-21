package com.example.hyoon_000.lab_1;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mLight, mMagnetic, mRotation, mAccelerometer; //Finding all the labels on the activity
    TextView lightText, magneticText, rotationText, accText;
    LineGraphView graph;
    DecimalFormat df = new DecimalFormat("#.##");
    float accXMax=0, accYMax=0, accZMax=0, rotationXMax=0,rotationYMax=0,rotationZMax=0, magXMax=0, magYMax=0, magZMax=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightText = (TextView)findViewById(R.id.lighttextfield);
        magneticText = (TextView)findViewById(R.id.magneticfield);
        rotationText = (TextView)findViewById(R.id.rotationfield);
        accText = (TextView)findViewById(R.id.accText);
        RelativeLayout layout = ((RelativeLayout)findViewById(R.id.layout));
        graph = new LineGraphView(getApplicationContext(),
                100,
                Arrays.asList("x", "y", "z"));
        layout.addView(graph);
        graph.setVisibility(View.VISIBLE);
        //Using the graph
        //Getting the sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //Sensor manager
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //Apply it to get data from the light sensor
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    //The function that listens for changes in the sensors
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) { //Accelerometer data
            float accX = event.values[0]; //Obtaining the X,Y,Z data for the accelerometer
            if (Math.abs(accX) > Math.abs(accXMax)) {
                accXMax = accX;
            }
            float accY = event.values[1];
            if (Math.abs(accY) > Math.abs(accYMax)) {
                accYMax = accY;
            }
            float accZ = event.values[2];
            if (Math.abs(accZ) > Math.abs(accZMax)) {
                accZMax = accZ;
            }
            graph.addPoint(event.values);
            accText.setText("AccX:" + df.format(accX) + " Record:" + df.format(accXMax) + "  Y:" + df.format(accY) + " Record:" + df.format(accYMax) + "  Z:" + df.format(accZ) + " Record:" + df.format(accZMax));
        }else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) { //Rotational data
            float rotationX = event.values[0];;
            if (Math.abs(rotationX) > rotationXMax) {
                rotationXMax = rotationX;
            }
            float rotationY = event.values[1];
            if (Math.abs(rotationY) > rotationYMax) {
                rotationYMax = rotationY;
            }
            float rotationZ = event.values[2];
            if (Math.abs(rotationZ) > rotationZMax) {
                rotationZMax = rotationZ;
            }
            rotationText.setText("Rot X:" + df.format(rotationX) + " Record:" + df.format(rotationXMax) + "  Y:" + df.format(rotationY) + " Record:" + df.format(rotationYMax) +"  Z:" + df.format(rotationZ) + " Record:" + df.format(rotationZMax));
        }else if (sensor.getType() == Sensor.TYPE_LIGHT) { //Light intensity data
            float light = event.values[0];
            df.format(light);
            String Lux = Float.toString(light); //Convert the float into a string to output it to a textfield
            lightText.setText("Light Intensity Lux " + Lux);
        }else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float magneticX = event.values[0];
            if (Math.abs(magneticX) > magXMax) {
                magXMax = magneticX;
            }
            float magneticY = event.values[1];
            if (Math.abs(magneticY) > magYMax) {
                magYMax = magneticY;
            }
            float magneticZ = event.values[2];
            if (Math.abs(magneticX) > magZMax) {
                magZMax = magneticX;
            }
            magneticText.setText("Mag Field X:" + df.format(magneticX) + " Record:" + df.format(magXMax) + "  Y:" + df.format(magneticY) + " Record:" + df.format(magYMax) +"  Z:" + df.format(magneticZ) + " Record:" + df.format(magZMax));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //What the app does when it is running
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRotation, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //When the app is paused make sure to stop listening for sensor activity
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

  /*  public class PlaceholderFragment extends Fragment {
        private LineGraphView graph;

        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,false);
            LinearLayout l = (LinearLayout)rootView.findViewById(R.id.frag_layout);
            l.setOrientation(LinearLayout.VERTICAL);
            graph = new LineGraphView(getApplicationContext(),
                    100,
                    Arrays.asList("x", "y", "z"));
            l.addView(graph);
            graph.setVisibility(View.VISIBLE);
            return rootView;
        }
    }*/
}
