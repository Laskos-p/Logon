package com.example.wyscig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.wyscig.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton button;
    Button pomoc, menu_ustawienia, logowanie_z_menu, exitt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences ustawienia = getSharedPreferences("ustawienia", MODE_PRIVATE);

        button = findViewById(R.id.imageButton);
        button.setOnClickListener(v -> {
            if (ustawienia.getBoolean("ustawienia_jasnosc", true)) {
                openActivity3();
            } else if (ustawienia.getBoolean("ustawienia_odleglosc", true)) {
                openActivity2();
            }
        });

        exitt = findViewById(R.id.exitt);
        exitt.setOnClickListener(v -> openWyjscie());

        logowanie_z_menu = findViewById(R.id.logowanie_z_menu);
        logowanie_z_menu.setOnClickListener(v -> openLogowanie());

        pomoc = findViewById(R.id.pomoc);
        pomoc.setOnClickListener(v -> openPomoc());

        menu_ustawienia = findViewById(R.id.ustawienia);
        menu_ustawienia.setOnClickListener(v -> openUstawienia());
    }

    public void openPomoc() {
        Intent intent = new Intent(this, Pomoc.class);
        startActivity(intent);
    }

    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void openActivity3() {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    public void openUstawienia() {
        Intent intent = new Intent(this, Ustawienia.class);
        startActivity(intent);
    }

    public void openLogowanie() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openWyjscie() {
        this.finishAffinity();
    }
}