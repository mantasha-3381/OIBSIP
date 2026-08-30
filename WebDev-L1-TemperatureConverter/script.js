const temperatureInput = document.getElementById("temperature");
const unitSelect = document.getElementById("unit");
const convertBtn = document.getElementById("convertBtn");

const errorMessage = document.getElementById("errorMessage");

const celsiusResult = document.getElementById("celsiusResult");
const fahrenheitResult = document.getElementById("fahrenheitResult");
const kelvinResult = document.getElementById("kelvinResult");

convertBtn.addEventListener("click", function () {

    const temperature = parseFloat(temperatureInput.value);
    const unit = unitSelect.value;

    errorMessage.textContent = "";

    // Check for empty or invalid input
    if (temperatureInput.value.trim() === "" || isNaN(temperature)) {
        errorMessage.textContent = "Please enter a valid numeric temperature.";
        return;
    }

    // Convert input to Celsius first
    let celsius;

    if (unit === "celsius") {
        celsius = temperature;
    } 
    else if (unit === "fahrenheit") {
        celsius = (temperature - 32) * 5 / 9;
    } 
    else if (unit === "kelvin") {
        celsius = temperature - 273.15;
    }

    // Absolute zero validation
    if (celsius < -273.15) {
        errorMessage.textContent =
            "Temperature cannot be below absolute zero (-273.15°C).";
        return;
    }

    // Convert Celsius to all units
    const fahrenheit = (celsius * 9 / 5) + 32;
    const kelvin = celsius + 273.15;

    // Display results
    celsiusResult.textContent = celsius.toFixed(2) + " °C";
    fahrenheitResult.textContent = fahrenheit.toFixed(2) + " °F";
    kelvinResult.textContent = kelvin.toFixed(2) + " K";
});