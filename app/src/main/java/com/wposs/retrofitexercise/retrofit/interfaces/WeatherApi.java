package com.wposs.retrofitexercise.retrofit.interfaces;

import com.wposs.retrofitexercise.retrofit.model.DataWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Call<DataWeather>    getWeatherCt(@Query("q") String city,
                                   @Query("appid") String apiKey);
    @GET("weather")
    Call<DataWeather> getWeatherCC(@Query("q") String city,@Query(",") String country,
                                   @Query("appid") String apiKey);
}
