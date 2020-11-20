package com.example.wyscig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Activity2 extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private MediaPlayer BeepMP;
    private TextView okrazenie;
    private TextView data;
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    Date date = new Date();
    int i=0;
    private static final int SENSOR_SENSITIVITY = 4;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        BeepMP = MediaPlayer.create(this,R.raw.beep);
        okrazenie = findViewById(R.id.okrazenia);
        data = findViewById(R.id.data_czas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] <= SENSOR_SENSITIVITY) {
                BeepMP.start();
                i++;
                okrazenie.setText("Wykonano "+i+" okrążeń");
            }
        }
        data.setText("Czas rozpoczęcia: "+dateFormat.format(date));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}