package fr.umontpellier.exercice3capteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    TextView textView;
    LinearLayout layout;

    private static final double MIDDLE_THRESHOLD = 9.0;
    private static final double LOW_THRESHOLD = 0.1;
    private static final double HIGH_THRESHOLD = 6.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.id_text_view);
        layout = (LinearLayout)findViewById(R.id.layout_parent);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            double value = x + y + z;
            if (value <= LOW_THRESHOLD){
                layout.setBackgroundColor(getResources().getColor(R.color.green));
            } else if (value > LOW_THRESHOLD && value <= MIDDLE_THRESHOLD){
                layout.setBackgroundColor(getResources().getColor(R.color.black));
            } else {
                layout.setBackgroundColor(getResources().getColor(R.color.red));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}