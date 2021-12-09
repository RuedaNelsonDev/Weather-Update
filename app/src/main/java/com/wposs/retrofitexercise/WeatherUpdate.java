package com.wposs.retrofitexercise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wposs.retrofitexercise.retrofit.interfaces.WeatherApi;
import com.wposs.retrofitexercise.retrofit.model.DataWeather;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherUpdate extends AppCompatActivity {

    EditText etCity;
    EditText etCountry;
    TextView tvResult;
    Button btnSearchWeather;

    boolean blnCountry = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather_update);
        initUi();
    }

    public void initUi() {
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult);
        btnSearchWeather = findViewById(R.id.btnSearchWeather);
        btnSearchWeather.setOnClickListener(v -> getWeatherDetails());
    }

    private void getWeatherDetails() {


        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        tvResult.setText("");
        if (!city.isEmpty()) {

            if (!country.isEmpty()) {

                blnCountry = true;
                Toast.makeText(getApplicationContext(), "Busqueda por ciudad y pais", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(getApplicationContext(), "ciudad ", Toast.LENGTH_SHORT).show();

            getInfo();
        } else {
            Toast.makeText(getApplicationContext(), "El campo de la ciudad no puede estar vacía", Toast.LENGTH_SHORT).show();
        }

    }

    private void getInfo() {
        String appid = "7d0d7d067ffc446761fb7eeb8d21b20a";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<DataWeather> call;
        if (blnCountry)
            call = weatherApi.getWeatherCC(etCity.getText().toString().trim(), etCountry.getText().toString().trim(), appid);
        else
            call = weatherApi.getWeatherCt(etCity.getText().toString().trim(), appid);


        call.enqueue(new Callback<DataWeather>() {
            @Override
            public void onResponse(@NonNull Call<DataWeather> call, @NonNull Response<DataWeather> response) {
                if (!response.isSuccessful()) {
                    StringBuilder codeError = new StringBuilder();
                    tvResult.setText(codeError.append("Código : ").append(response.code()).toString());
                    return;
                }
                DataWeather weather = response.body();
                assert weather != null;
                double temperatura = weather.getMain().getTemp() - 273.15;

                String weatherDescription = weather.getWeather().get(0).getDescription();

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(1);

                StringBuilder result = new StringBuilder();
                tvResult.setText( result.append(df.format(temperatura)).append("°C") .append("\n") .append(weatherDescription));

            }

            @Override
            public void onFailure(@NonNull Call<DataWeather> call, @NonNull Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });

    }
}