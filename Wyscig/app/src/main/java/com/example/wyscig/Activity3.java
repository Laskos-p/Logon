package com.example.wyscig;

import android.annotation.SuppressLint;
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


public class Activity3 extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private TextView okrazenie2;
    private TextView data;
    private MediaPlayer BeepMP;
    private float last_value;
    int j = 0;
    Chronometer cmTimer;
    Button btnStart, btnStop, btnExit;
    Boolean resume = false;
    Boolean on = false;
    long elapsedTime;
    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        okrazenie2 = findViewById(R.id.okrazenia2);
        BeepMP = MediaPlayer.create(this,R.raw.beep);
        data = findViewById(R.id.data_czas2);
        cmTimer = findViewById(R.id.timer);
        btnStart = findViewById(R.id.start_zegar2);
        btnStop = findViewById(R.id.stop2);
        btnExit = findViewById(R.id.exit2);
        last_value=0;
        btnStart.setOnClickListener(v -> {
            on = true;
            j = 0;
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
            on = false;
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
        lightEventListener = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                if(last_value >= value*2 && on) {
                BeepMP.start();
                j++;
                okrazenie2.setText("Wykonano "+j+" okrążeń");
                }
                last_value=value;

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
