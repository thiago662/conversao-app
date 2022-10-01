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

public class MainActivity extends AppCompatActivity {
    EditText editNumberCelsius;
    TextView textViewResultFahrenheit;
    Button buttonConvertCelsiusToFahrenheit;
    Button buttonClear;
    Button buttonChangeToCelsiusConversion;

    float celsius = 0f;
    float fahrenheit = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNumberCelsius = findViewById(R.id.editNumberCelsius);
        textViewResultFahrenheit = findViewById(R.id.textViewResultFahrenheit);
        buttonConvertCelsiusToFahrenheit = findViewById(R.id.buttonConvertCelsiusToFahrenheit);
        buttonClear = findViewById(R.id.buttonClear);
        buttonChangeToCelsiusConversion = findViewById(R.id.buttonChangeToCelsiusConversion);

        buttonConvertCelsiusToFahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                celsius = Float.parseFloat(editNumberCelsius.getText().toString());
                fahrenheit = convertCelsiusToFahrenheit(celsius);

                SharedPreferences prefs = getSharedPreferences("dados", Context.MODE_PRIVATE);
                SharedPreferences.Editor editaPrefs = prefs.edit();

                editaPrefs.putFloat("celsius", celsius);
                editaPrefs.putFloat("fahrenheit", fahrenheit);
                editaPrefs.apply();

                textViewResultFahrenheit.setText(Float.toString(fahrenheit));

                Toast.makeText(MainActivity.this, "Convertido", Toast.LENGTH_SHORT).show();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("dados", Context.MODE_PRIVATE);
                SharedPreferences.Editor editaPrefs = prefs.edit();

                editaPrefs.clear().apply();

                editNumberCelsius.setText("");
                textViewResultFahrenheit.setText("");

                fahrenheit = 0f;
                celsius = 0f;
            }
        });

        buttonChangeToCelsiusConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

                intent.putExtra("celsius", celsius);
                intent.putExtra("fahrenheit", fahrenheit);

                startActivity(intent);
            }
        });

        SharedPreferences prefs = getSharedPreferences("dados", Context.MODE_PRIVATE);

        if (prefs.getFloat("fahrenheit", 0f) == 0f && prefs.getFloat("celsius", 0f) == 0f) {
            celsius = 0f;
            fahrenheit = 0f;

            editNumberCelsius.setText("");
            textViewResultFahrenheit.setText("");
        } else {
            celsius = prefs.getFloat("celsius", 0f);
            fahrenheit = convertCelsiusToFahrenheit(celsius);

            editNumberCelsius.setText(Float.toString(celsius));
            textViewResultFahrenheit.setText(Float.toString(fahrenheit));
        }
    }

    public float convertCelsiusToFahrenheit(float celsiusValue) {
        return (celsiusValue * 1.8f) + 32f;
    }
}