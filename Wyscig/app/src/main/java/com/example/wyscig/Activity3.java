package com.example.wyscig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity3 extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private MediaPlayer BeepMP;
    private TextView okrazenie, data, Trasa, start, Predkosc, Tempo;
    int okrazenia = 0, obwod, czas = 0, tempoHours = 0, tempoMinutes = 0;
    float last_value, tempoSeconds = 0, tempo = 0;
    double trasa = 0, obwodDouble, predkosc = 0, tempoTrasa = 0;
    String okrazeniaS, dataS, obwodS, obwodSError, TrasaS, PredkoscS, TempoS;
    Chronometer cmTimer;
    Button btnStart, btnStop, btnExit;
    Boolean on = false;
    long hours, minutes, seconds;
    DecimalFormat precision2 = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        SharedPreferences ustawienia = getSharedPreferences("ustawienia", MODE_PRIVATE);
        obwod = ustawienia.getInt("obwod", 0);
        obwodDouble = obwod;
        obwodDouble /= 100;
        obwodS = getString(R.string.Obwód, precision2.format(obwodDouble));
        final TextView textViewToChange = findViewById(R.id.wymiary_info);
        textViewToChange.setText(obwodS);
        BeepMP = MediaPlayer.create(this, R.raw.beep);
        okrazenie = findViewById(R.id.okrazenia);
        data = findViewById(R.id.data_czas);
        Trasa = findViewById(R.id.trasa);
        start = findViewById(R.id.start);
        Predkosc = findViewById(R.id.predkosc);
        Tempo = findViewById(R.id.tempo);
        cmTimer = findViewById(R.id.timer);
        btnStart = findViewById(R.id.start_zegar);
        btnStop = findViewById(R.id.stop);
        if (obwod == 0) {
            obwodSError = getString(R.string.Obwód_error);
            textViewToChange.setText(obwodSError);
            btnStart.setEnabled(false);
            btnStop.setEnabled(false);
        }
        btnExit = findViewById(R.id.exit);
        okrazeniaS = getString(R.string.okrazenia, okrazenia);
        okrazenie.setText(okrazeniaS);
        dataS = getString(R.string.data, "");
        data.setText(dataS);
        TrasaS = getString(R.string.Trasa, precision2.format(trasa));
        Trasa.setText(TrasaS);
        PredkoscS = getString(R.string.predkosc, precision2.format(predkosc));
        Predkosc.setText(PredkoscS);
        TempoS = getString(R.string.tempo, tempoHours, tempoMinutes, (int) tempoSeconds);
        Tempo.setText(TempoS);
        btnStart.setOnClickListener(v -> {
            on = true;
            okrazenia = 0;
            trasa = 0.0;
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            cmTimer.setBase(SystemClock.elapsedRealtime());
            cmTimer.start();
            start.setVisibility(View.VISIBLE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            dataS = getString(R.string.data, dateFormat.format(date));
            data.setText(dataS);
        });
        btnStop.setOnClickListener(v -> {

            cmTimer.stop();
            SharedPreferences sharedPreferences = getSharedPreferences("wyniki", MODE_PRIVATE);
            SharedPreferences.Editor wyniki = sharedPreferences.edit();
            wyniki.putString("data", dataS);
            wyniki.putInt("okrazenia", okrazenia);
            wyniki.putFloat("dystans", (float) trasa);
            wyniki.putFloat("predkosc", (float) predkosc);
            wyniki.putString("tempo", TempoS);
            wyniki.putString("czas", getString(R.string.czas, hours, minutes, seconds));
            wyniki.apply();
            openWyniki();

        });
        btnExit.setOnClickListener(v -> openMainActivity());
        cmTimer.setOnChronometerTickListener(arg0 -> {
            hours = ((SystemClock.elapsedRealtime() - cmTimer.getBase()) / 3600000);
            minutes = ((SystemClock.elapsedRealtime() - cmTimer.getBase()) / 60000);
            seconds = ((SystemClock.elapsedRealtime() - cmTimer.getBase()) / 1000) % 60;
            arg0.setText(getString(R.string.czas, hours, minutes, seconds));
        });
        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                if (last_value >= value * 1.3 && on) {
                    BeepMP.start();
                    trasa += obwodDouble;
                    okrazenia++;
                    okrazeniaS = getString(R.string.okrazenia, okrazenia);
                    okrazenie.setText(okrazeniaS);
                    TrasaS = getString(R.string.Trasa, precision2.format(trasa));
                    Trasa.setText(TrasaS);
                    predkosc = trasa / (minutes * 60 + seconds);
                    PredkoscS = getString(R.string.predkosc, precision2.format(predkosc));
                    Predkosc.setText(PredkoscS);
                    czas = (int) (hours * 3600 + minutes * 60 + seconds);
                    tempoTrasa = trasa / 1000;
                    tempo = (float) (czas / tempoTrasa);
                    tempoMinutes = (int) tempo / 60;
                    tempoSeconds = tempo - tempoMinutes * 60;
                    while (tempoMinutes >= 60) {
                        tempoMinutes -= 60;
                        tempoHours++;
                    }
                    TempoS = getString(R.string.tempo, tempoHours, tempoMinutes, Math.round(tempoSeconds));
                    Tempo.setText(TempoS);
                }
                last_value = value;
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

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openWyniki() {
        Intent intent = new Intent(this, Wyniki.class);
        startActivity(intent);
    }
}