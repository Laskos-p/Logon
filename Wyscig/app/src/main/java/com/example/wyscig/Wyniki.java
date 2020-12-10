package com.example.wyscig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Wyniki extends AppCompatActivity {
    private String dataS, tempoS, czas;
    private int okrazenia;
    private float trasa, predkosc;
    Button powrot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyniki);
        SharedPreferences wyniki = getSharedPreferences("wyniki", MODE_PRIVATE);
        dataS = wyniki.getString("data", null);
        okrazenia = wyniki.getInt("okrazenia", 0);
        trasa = wyniki.getFloat("dystans", 0);
        predkosc = wyniki.getFloat("predkosc", 0);
        tempoS = wyniki.getString("tempo", null);
        czas = wyniki.getString("czas", null);

        powrot = findViewById(R.id.powrot_z_tabeli);
        powrot.setOnClickListener(v -> openMainActivity());

        final TextView textViewToChange = (TextView) findViewById(R.id.test_text);
        textViewToChange.setText(dataS + " " +okrazenia+ " " +trasa+ "m " +predkosc+ "km/h " +tempoS+ "m/km " +czas+"s");

    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}