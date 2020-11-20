package com.example.wyscig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Activity2 extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mProximity;
    private MediaPlayer BeepMP;
    private TextView okrazenie;
    private TextView data;
    int i=0;
    private static final int SENSOR_SENSITIVITY = 4;
    Chronometer cmTimer;
    Button btnStart, btnStop, btnExit;
    Boolean resume = false;
    long elapsedTime;
    String TAG = "TAG";

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        BeepMP = MediaPlayer.create(this,R.raw.beep);
        okrazenie = findViewById(R.id.okrazenia);
        data = findViewById(R.id.data_czas);
        cmTimer = (Chronometer) findViewById(R.id.timer);
        btnStart = (Button) findViewById(R.id.start_zegar);
        btnStop = (Button) findViewById(R.id.stop);
        btnExit = (Button) findViewById(R.id.exit);
        btnStart.setOnClickListener(v -> {
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            if (!resume) {
                cmTimer.setBase(SystemClock.elapsedRealtime());
            }
            cmTimer.start();
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dataS = getString(R.string.data,dateFormat.format(date));
            data.setText(dataS);
        });
        btnStop.setOnClickListener(v -> {
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
            cmTimer.stop();
        });
        btnExit.setOnClickListener(v -> openMainActivity());
        cmTimer.setOnChronometerTickListener(arg0 -> {
            long minutes;
            long seconds;
            if (!resume) {
                minutes = ((SystemClock.elapsedRealtime() - cmTimer.getBase()) / 1000) / 60;
                seconds = ((SystemClock.elapsedRealtime() - cmTimer.getBase()) / 1000) % 60;
                elapsedTime = SystemClock.elapsedRealtime();
            } else {
                minutes = ((elapsedTime - cmTimer.getBase()) / 1000) / 60;
                seconds = ((elapsedTime - cmTimer.getBase()) / 1000) % 60;
                elapsedTime = elapsedTime + 1000;
            }
            Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);
        });

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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}