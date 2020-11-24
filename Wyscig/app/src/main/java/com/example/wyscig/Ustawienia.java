package com.example.wyscig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ustawienia extends AppCompatActivity {
    EditText szerokosc, wysokosc;
    int obwod;
    RadioButton jasnosc, odleglosc;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        findViewById(R.id.wyjscie).setOnClickListener(v -> openMain());

        findViewById(R.id.zapis).setOnClickListener(v -> Pole());

        SharedPreferences ustawienia = getSharedPreferences("ustawienia", MODE_PRIVATE);
        jasnosc = (RadioButton) findViewById(R.id.jasnosc);
        odleglosc = (RadioButton) findViewById(R.id.odleglosc);
        jasnosc.setChecked(ustawienia.getBoolean("ustawienia_jasnosc",true));
        odleglosc.setChecked(ustawienia.getBoolean("ustawienia_odleglosc", false));
    }
    public void Pole(){
        try {
            wysokosc = (EditText) findViewById(R.id.wysokosc);
            szerokosc = (EditText) findViewById(R.id.szerokosc);

            int wysInt = Integer.parseInt(wysokosc.getText().toString());
            int szerInt = Integer.parseInt(szerokosc.getText().toString());

            obwod = 2*(wysInt + szerInt + 40);

            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Obwód to: "+ obwod);

            SharedPreferences sharedPreferences = getSharedPreferences("ustawienia", MODE_PRIVATE);
            SharedPreferences.Editor ustawienia = sharedPreferences.edit();
            ustawienia.putInt("obwod", wysInt);
            ustawienia.putInt("obwod", szerInt);
            ustawienia.putInt("obwod", obwod);
            ustawienia.commit();

        } catch (Throwable e) {
            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Podaj prawidłowe wartości");
        }

    }
    public void onRadioButtonClicked(View v) {
        jasnosc = (RadioButton) findViewById(R.id.jasnosc);
        odleglosc = (RadioButton) findViewById(R.id.odleglosc);

        SharedPreferences sharedPreferences = getSharedPreferences("ustawienia", MODE_PRIVATE);
        SharedPreferences.Editor ustawienia = sharedPreferences.edit();

        ustawienia.putBoolean("ustawienia_jasnosc", jasnosc.isChecked());
        ustawienia.putBoolean("ustawienia_odleglosc", odleglosc.isChecked());
        ustawienia.commit();
    }
    public void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
