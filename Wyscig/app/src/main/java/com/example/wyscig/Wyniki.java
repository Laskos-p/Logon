package com.example.wyscig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class Wyniki extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyniki);

/*
        SharedPreferences wyniki = getSharedPreferences("wyniki", MODE_PRIVATE);
        wyniki.putString("data", dataS);
        wyniki.putInt("okrazenia", okrazenia);
        wyniki.putFloat("dystans", (float) trasa);
        wyniki.putFloat("predkosc", (float) predkosc);
        wyniki.putString("tempo", TempoS);
        wyniki.putString("czas", getString(R.string.czas, hours, minutes, seconds));
        */
    }
}