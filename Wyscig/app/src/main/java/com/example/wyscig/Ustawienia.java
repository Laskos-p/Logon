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
    EditText szerokosc, wysokosc, promien;
    int obwod;
    RadioButton jasnosc, odleglosc;
    Spinner spinner;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        findViewById(R.id.wyjscie).setOnClickListener(v -> openMain());

        findViewById(R.id.zapis_prostokat).setOnClickListener(v -> poleProstokat());
        findViewById(R.id.zapis_okrag).setOnClickListener(v -> poleOkrag());

        wysokosc = (EditText) findViewById(R.id.wysokosc);
        szerokosc = (EditText) findViewById(R.id.szerokosc);
        promien = (EditText) findViewById(R.id.promien);

        //Załadowanie wcześniej ustawionych przez użytkownika preferencji sensora
        SharedPreferences ustawienia = getSharedPreferences("ustawienia", MODE_PRIVATE);
        jasnosc = (RadioButton) findViewById(R.id.jasnosc);
        odleglosc = (RadioButton) findViewById(R.id.odleglosc);
        jasnosc.setChecked(ustawienia.getBoolean("ustawienia_jasnosc",true));
        odleglosc.setChecked(ustawienia.getBoolean("ustawienia_odleglosc", false));

        spinner = (Spinner) findViewById(R.id.spinner);

        //Menu rozwijane
        String[] items = new String[]{"okrągły", "prostokątny"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setSelection(ustawienia.getInt("spinnervalue", 0));
        if (ustawienia.getInt("spinnervalue", 0) == 0) {
            findViewById(R.id.opcje_prostokat).setVisibility(View.GONE);
            findViewById(R.id.opcje_okrag).setVisibility(View.VISIBLE);
            try {
                promien.setText(String.valueOf(ustawienia.getInt("prom", 0)));
            } catch (Throwable ignored) {}
        } else {
            findViewById(R.id.opcje_okrag).setVisibility(View.GONE);
            findViewById(R.id.opcje_prostokat).setVisibility(View.VISIBLE);
            try {
                szerokosc.setText(String.valueOf(ustawienia.getInt("szer", 0)));
                wysokosc.setText(String.valueOf(ustawienia.getInt("wys", 0)));
            } catch (Throwable ignored) {}
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position) {
                    case 0:
                        findViewById(R.id.opcje_prostokat).setVisibility(View.GONE);
                        findViewById(R.id.opcje_okrag).setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        findViewById(R.id.opcje_okrag).setVisibility(View.GONE);
                        findViewById(R.id.opcje_prostokat).setVisibility(View.VISIBLE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void poleProstokat(){
        try {
            int wysInt = Integer.parseInt(wysokosc.getText().toString());
            int szerInt = Integer.parseInt(szerokosc.getText().toString());

            obwod = (int) 2*(wysInt + szerInt + 40);

            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Obwód to: "+ obwod);

            SharedPreferences sharedPreferences = getSharedPreferences("ustawienia", MODE_PRIVATE);
            SharedPreferences.Editor ustawienia = sharedPreferences.edit();
            ustawienia.putInt("szer", szerInt);
            ustawienia.putInt("wys", wysInt);
            ustawienia.putInt("obwod", obwod);
            ustawienia.putInt("spinnervalue", 1);
            ustawienia.apply();

        } catch (Throwable e) {
            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Podaj prawidłowe wartości");
        }
    }

    public void poleOkrag() {
        try {
            int promInt = Integer.parseInt(promien.getText().toString());

            obwod = (int) (6.28 * (promInt + 20));
            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Obwód to: " + obwod);

            SharedPreferences sharedPreferences = getSharedPreferences("ustawienia", MODE_PRIVATE);
            SharedPreferences.Editor ustawienia = sharedPreferences.edit();
            ustawienia.putInt("prom", promInt);
            ustawienia.putInt("obwod", obwod);
            ustawienia.putInt("spinnervalue", 0);
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
