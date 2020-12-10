package com.example.wyscig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ustawienia extends AppCompatActivity {
    EditText szerokosc, wysokosc, promien, dlugosc;
    int droga;
    RadioButton jasnosc, odleglosc;
    Spinner spinner;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        findViewById(R.id.wyjscie).setOnClickListener(v -> openMain());

        findViewById(R.id.zapis_prostokat).setOnClickListener(v -> obwProstokat());
        findViewById(R.id.zapis_okrag).setOnClickListener(v -> obwOkrag());
        findViewById(R.id.zapis_korytarz).setOnClickListener(v -> obwKorytarz());

        wysokosc = (EditText) findViewById(R.id.wysokosc);
        szerokosc = (EditText) findViewById(R.id.szerokosc);
        promien = (EditText) findViewById(R.id.promien);
        dlugosc = (EditText) findViewById(R.id.dlugosc);

        //Załadowanie wcześniej ustawionych przez użytkownika preferencji sensora
        SharedPreferences ustawienia = getSharedPreferences("ustawienia", MODE_PRIVATE);
        jasnosc = (RadioButton) findViewById(R.id.jasnosc);
        odleglosc = (RadioButton) findViewById(R.id.odleglosc);
        jasnosc.setChecked(ustawienia.getBoolean("ustawienia_jasnosc", true));
        odleglosc.setChecked(ustawienia.getBoolean("ustawienia_odleglosc", false));

        spinner = (Spinner) findViewById(R.id.spinner);

        //Menu rozwijane
        String[] items = new String[]{"okrągły", "prostokątny", "korytarz"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setSelection(ustawienia.getInt("spinnervalue", 0));
        if (ustawienia.getInt("spinnervalue", 0) == 0) {
            findViewById(R.id.opcje_prostokat).setVisibility(View.GONE);
            findViewById(R.id.opcje_korytarz).setVisibility(View.GONE);
            findViewById(R.id.opcje_okrag).setVisibility(View.VISIBLE);
            try {
                promien.setText(String.valueOf(ustawienia.getInt("prom", 0)));
            } catch (Throwable ignored) {
            }
        } else if (ustawienia.getInt("spinnervalue", 0) == 1) {
            findViewById(R.id.opcje_okrag).setVisibility(View.GONE);
            findViewById(R.id.opcje_korytarz).setVisibility(View.GONE);
            findViewById(R.id.opcje_prostokat).setVisibility(View.VISIBLE);
            try {
                szerokosc.setText(String.valueOf(ustawienia.getInt("szer", 0)));
                wysokosc.setText(String.valueOf(ustawienia.getInt("wys", 0)));
            } catch (Throwable ignored) {
            }
        } else {
            findViewById(R.id.opcje_okrag).setVisibility(View.GONE);
            findViewById(R.id.opcje_korytarz).setVisibility(View.VISIBLE);
            findViewById(R.id.opcje_prostokat).setVisibility(View.GONE);
            try {
                dlugosc.setText(String.valueOf(ustawienia.getInt("dl", 0)));
            } catch (Throwable ignored) {
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        findViewById(R.id.opcje_prostokat).setVisibility(View.GONE);
                        findViewById(R.id.opcje_okrag).setVisibility(View.VISIBLE);
                        findViewById(R.id.opcje_korytarz).setVisibility(View.GONE);
                        break;
                    case 1:
                        findViewById(R.id.opcje_okrag).setVisibility(View.GONE);
                        findViewById(R.id.opcje_prostokat).setVisibility(View.VISIBLE);
                        findViewById(R.id.opcje_korytarz).setVisibility(View.GONE);
                        break;
                    case 2:
                        findViewById(R.id.opcje_okrag).setVisibility(View.GONE);
                        findViewById(R.id.opcje_prostokat).setVisibility(View.GONE);
                        findViewById(R.id.opcje_korytarz).setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void obwProstokat() {
        try {
            int wysInt = Integer.parseInt(wysokosc.getText().toString());
            int szerInt = Integer.parseInt(szerokosc.getText().toString());

            droga = (int) 2 * (wysInt + szerInt + 40);

            SharedPreferences sharedPreferences = getSharedPreferences("ustawienia", MODE_PRIVATE);
            SharedPreferences.Editor ustawienia = sharedPreferences.edit();
            ustawienia.putInt("szer", szerInt);
            ustawienia.putInt("wys", wysInt);
            ustawienia.putInt("obwod", droga);
            ustawienia.putInt("spinnervalue", 1);
            ustawienia.apply();

        } catch (Throwable e) {
            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Podaj prawidłowe wartości");
        }
    }

    public void obwOkrag() {
        try {
            int promInt = Integer.parseInt(promien.getText().toString());

            droga = (int) (6.28 * (promInt + 20));

            SharedPreferences sharedPreferences = getSharedPreferences("ustawienia", MODE_PRIVATE);
            SharedPreferences.Editor ustawienia = sharedPreferences.edit();
            ustawienia.putInt("prom", promInt);
            ustawienia.putInt("obwod", droga);
            ustawienia.putInt("spinnervalue", 0);
            ustawienia.apply();
        } catch (Throwable e) {
            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Podaj prawidłowe wartości");
        }
    }

    public void obwKorytarz() {
        try {
            int dlInt = Integer.parseInt(dlugosc.getText().toString());

            droga = (int) dlInt * 2;
            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Obwód to: " + droga);

            SharedPreferences sharedPreferences = getSharedPreferences("ustawienia", MODE_PRIVATE);
            SharedPreferences.Editor ustawienia = sharedPreferences.edit();
            ustawienia.putInt("dl", dlInt);
            ustawienia.putInt("obwod", droga);
            ustawienia.putInt("spinnervalue", 2);
            ustawienia.apply();
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
        ustawienia.apply();
    }

    public void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
