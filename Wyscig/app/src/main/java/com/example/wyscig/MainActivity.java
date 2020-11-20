package com.example.wyscig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    SwitchCompat switchCompat;
    ImageButton button;
    Button pomoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchCompat = findViewById(R.id.switch1);
        button = findViewById(R.id.imageButton);
        button.setOnClickListener(v -> {
            if(switchCompat.isChecked()){
                openActivity3();
            }else{
                openActivity2();
            }
        });
        pomoc = findViewById(R.id.pomoc);
        pomoc.setOnClickListener(v -> openPomoc());
    }
    public void openPomoc(){
        Intent intent = new Intent(this, Pomoc.class);
        startActivity(intent);
    }
    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
    public void openActivity3(){
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }
}