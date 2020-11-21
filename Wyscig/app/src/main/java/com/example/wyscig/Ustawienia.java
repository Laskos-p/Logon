package com.example.wyscig;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Ustawienia extends AppCompatActivity {
    Button wyjscie;

    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);
        wyjscie = findViewById(R.id.wyjscie);
        wyjscie.setOnClickListener(v -> openMain());
    }

    public void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
