package com.example.wyscig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ustawienia extends AppCompatActivity {
    Button wyjscie, zapis;
    EditText szerokosc, wysokosc;
    TextView obwodText;
    double obwod;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);
        wyjscie = findViewById(R.id.wyjscie);
        wyjscie.setOnClickListener(v -> openMain());
        zapis = findViewById(R.id.zapis);
        zapis.setOnClickListener(v -> Pole());
    }
    public void Pole(){
        wysokosc = (EditText) findViewById(R.id.wysokosc);
        szerokosc = (EditText) findViewById(R.id.szerokosc);
        String wysStr = wysokosc.getText().toString();
        int wysInt = Integer.parseInt(wysStr);
        String szerStr = szerokosc.getText().toString();
        int szerInt = Integer.parseInt(szerStr);
        obwod = 2*(wysInt + szerInt + 20);
        final TextView textViewToChange = (TextView) findViewById(R.id.obwodText);
        textViewToChange.setText("Obwód to: "+ obwod);

    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
