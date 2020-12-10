package com.example.wyscig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

public class Wyniki extends AppCompatActivity {
    private String dataS, tempoS, czas, wyniki_zapis, wyniki_zapis1, predkoscS;
    private int okrazenia;
    private float trasa, predkosc;
    Button powrot, resett;
    DecimalFormat precision2 = new DecimalFormat("0.00");


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

        wyniki_zapis = "\n" + dataS + "\nOkrążeń: " + okrazenia + "\nTrasa: " + trasa + "m\nPrędkość: " + precision2.format(predkosc) + "km/h\n" + tempoS + "\nCzas: " + czas + "\n________________________\n";

        resett = findViewById(R.id.reset);
        resett.setOnClickListener(v -> {
            wyniki_zapis1 = "";
            writeToFile(wyniki_zapis1, this);
            openMainActivity();
            Toast tost = Toast.makeText(this, "Historia zresetowana, nastąpił powrót do menu", Toast.LENGTH_LONG);
            tost.show();
        });

        wyniki_zapis1 = readFromFile((this)) + wyniki_zapis;
        writeToFile(wyniki_zapis1, this);


        powrot = findViewById(R.id.powrot_z_tabeli);
        powrot.setOnClickListener(v -> openMainActivity());


        final TextView textViewToChange = (TextView) findViewById(R.id.test_text);
        //textViewToChange.setText(dataS + " " +okrazenia+ " " +trasa+ "m " +predkosc+ "km/h " +tempoS+ "m/km " +czas+"s");
        textViewToChange.setText(readFromFile(this));
        //textViewToChange.setText(wyniki_zapis);

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    //ZAPIS
    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "Zapis się nie powiódł: " + e.toString());
        }
    }

    //ODCZYT
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}