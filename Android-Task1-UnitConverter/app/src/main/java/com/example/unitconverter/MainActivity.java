package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner categorySpinner, fromSpinner, toSpinner;
    EditText valueInput;
    Button convertButton;
    TextView resultText;

    String[] categories = {"Length", "Weight", "Temperature"};

    String[] lengthUnits = {
            "Centimetres", "Metres", "Kilometres", "Inches", "Feet"
    };

    String[] weightUnits = {
            "Grams", "Kilograms", "Pounds", "Ounces"
    };

    String[] temperatureUnits = {
            "Celsius", "Fahrenheit", "Kelvin"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        valueInput = findViewById(R.id.valueInput);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(categoryAdapter);

        updateUnitSpinners("Length");

        categorySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        updateUnitSpinners(categories[position]);

                        valueInput.setText("");
                        resultText.setText("Result will appear here");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        convertButton.setOnClickListener(v -> convertValue());
    }

    private void updateUnitSpinners(String category) {

        String[] units;

        if (category.equals("Length")) {
            units = lengthUnits;

        } else if (category.equals("Weight")) {
            units = weightUnits;

        } else {
            units = temperatureUnits;
        }

        ArrayAdapter<String> unitAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );

        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        fromSpinner.setAdapter(unitAdapter);
        toSpinner.setAdapter(unitAdapter);
    }

    private void convertValue() {

        String input = valueInput.getText().toString().trim();

        if (input.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please enter a value.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double value;

        try {

            value = Double.parseDouble(input);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter a valid number.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String category =
                categorySpinner.getSelectedItem().toString();

        String fromUnit =
                fromSpinner.getSelectedItem().toString();

        String toUnit =
                toSpinner.getSelectedItem().toString();

        double result;

        if (category.equals("Length")) {

            result = convertLength(value, fromUnit, toUnit);

        } else if (category.equals("Weight")) {

            result = convertWeight(value, fromUnit, toUnit);

        } else {

            double celsiusValue = convertToCelsius(value, fromUnit);

            if (celsiusValue < -273.15) {

                Toast.makeText(
                        this,
                        "Temperature cannot be below absolute zero.",
                        Toast.LENGTH_SHORT
                ).show();

                resultText.setText("Invalid temperature");

                return;
            }

            result = convertFromCelsius(celsiusValue, toUnit);
        }

        resultText.setText(
                String.format(
                        Locale.getDefault(),
                        "%.2f %s",
                        result,
                        toUnit
                )
        );
    }

    private double convertLength(
            double value,
            String from,
            String to) {

        double metres;

        switch (from) {

            case "Centimetres":
                metres = value / 100;
                break;

            case "Metres":
                metres = value;
                break;

            case "Kilometres":
                metres = value * 1000;
                break;

            case "Inches":
                metres = value * 0.0254;
                break;

            case "Feet":
                metres = value * 0.3048;
                break;

            default:
                metres = value;
        }

        switch (to) {

            case "Centimetres":
                return metres * 100;

            case "Metres":
                return metres;

            case "Kilometres":
                return metres / 1000;

            case "Inches":
                return metres / 0.0254;

            case "Feet":
                return metres / 0.3048;

            default:
                return metres;
        }
    }

    private double convertWeight(
            double value,
            String from,
            String to) {

        double grams;

        switch (from) {

            case "Grams":
                grams = value;
                break;

            case "Kilograms":
                grams = value * 1000;
                break;

            case "Pounds":
                grams = value * 453.592;
                break;

            case "Ounces":
                grams = value * 28.3495;
                break;

            default:
                grams = value;
        }

        switch (to) {

            case "Grams":
                return grams;

            case "Kilograms":
                return grams / 1000;

            case "Pounds":
                return grams / 453.592;

            case "Ounces":
                return grams / 28.3495;

            default:
                return grams;
        }
    }

    private double convertToCelsius(
            double value,
            String from) {

        switch (from) {

            case "Celsius":
                return value;

            case "Fahrenheit":
                return (value - 32) * 5 / 9;

            case "Kelvin":
                return value - 273.15;

            default:
                return value;
        }
    }

    private double convertFromCelsius(
            double celsius,
            String to) {

        switch (to) {

            case "Celsius":
                return celsius;

            case "Fahrenheit":
                return (celsius * 9 / 5) + 32;

            case "Kelvin":
                return celsius + 273.15;

            default:
                return celsius;
        }
    }
}