package com.example.wyscig;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ustawienia extends AppCompatActivity {
    Button wyjscie, zapis;
    EditText szerokosc, wysokosc;
    int obwod;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        wyjscie = findViewById(R.id.wyjscie);
        wyjscie.setOnClickListener(v -> openMain());

        zapis = findViewById(R.id.zapis);
        zapis.setOnClickListener(v -> Pole());
    }
    public void Pole(){
        try {
            wysokosc = (EditText) findViewById(R.id.wysokosc);
            szerokosc = (EditText) findViewById(R.id.szerokosc);

            String wysStr = wysokosc.getText().toString();
            int wysInt = Integer.parseInt(wysStr);

            String szerStr = szerokosc.getText().toString();
            int szerInt = Integer.parseInt(szerStr);

            obwod = 2*(wysInt + szerInt + 20);

            final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
            textViewToChange.setText("Obwód to: "+ obwod);

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
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

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
