package com.example.conversao_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText editNumberFahrenheit;
    TextView textViewResultCelsius;
    Button buttonConvertFahrenheitToCelsius;
    Button buttonClear;
    Button buttonChangeToFahrenheitConversion;

    float celsius = 0f;
    float fahrenheit = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editNumberFahrenheit = findViewById(R.id.editNumberFahrenheit);
        textViewResultCelsius = findViewById(R.id.textViewResultCelsius);
        buttonConvertFahrenheitToCelsius = findViewById(R.id.buttonConvertFahrenheitToCelsius);
        buttonClear = findViewById(R.id.buttonClear);
        buttonChangeToFahrenheitConversion = findViewById(R.id.buttonChangeToFahrenheitConversion);

        buttonConvertFahrenheitToCelsius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fahrenheit = Float.parseFloat(editNumberFahrenheit.getText().toString());
                celsius = convertFahrenheitToCelsius(fahrenheit);

                SharedPreferences prefs = getSharedPreferences("dados", Context.MODE_PRIVATE);
                SharedPreferences.Editor editaPrefs = prefs.edit();

                editaPrefs.putFloat("celsius", celsius);
                editaPrefs.putFloat("fahrenheit", fahrenheit);
                editaPrefs.apply();

                textViewResultCelsius.setText(Float.toString(celsius));

                Toast.makeText(MainActivity2.this, "Convertido", Toast.LENGTH_SHORT).show();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("dados", Context.MODE_PRIVATE);
                SharedPreferences.Editor editaPrefs = prefs.edit();

                editaPrefs.clear().apply();

                editNumberFahrenheit.setText("");
                textViewResultCelsius.setText("");

                fahrenheit = 0f;
                celsius = 0f;
            }
        });

        buttonChangeToFahrenheitConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("celsius", celsius);
                intent.putExtra("fahrenheit", fahrenheit);

                startActivity(intent);
            }
        });

        SharedPreferences prefs = getSharedPreferences("dados", Context.MODE_PRIVATE);

        if (prefs.getFloat("fahrenheit", 0f) == 0f && prefs.getFloat("celsius", 0f) == 0f) {
            celsius = 0f;
            fahrenheit = 0f;

            editNumberFahrenheit.setText("");
            textViewResultCelsius.setText("");
        } else {
            fahrenheit = prefs.getFloat("fahrenheit", 0f);
            celsius = convertFahrenheitToCelsius(fahrenheit);

            editNumberFahrenheit.setText(Float.toString(fahrenheit));
            textViewResultCelsius.setText(Float.toString(celsius));
        }
    }

    public float convertFahrenheitToCelsius(float fahrenheitValue) {
        return (fahrenheitValue - 32f) / 1.8f;
    }
}