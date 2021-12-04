package ru.same.starway;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static final String UNITS = "metric";
    private static final String KEY = "87af9e430e18ed3d99612325416fd8bd";
    private static final String LANG = "ru";
    private static Api api;
    private static SunApi sunApi;

    private Retrofit retrofit;

    public static String getLANG() {
        return LANG;
    }

    public static String getUNITS() {
        return UNITS;
    }

    public static String getKEY() {
        return KEY;
    }

    public static Api getApi() {
        return api;
    }

    public static SunApi getSunApi() {
        return sunApi;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")//ставим снову url
                .addConverterFactory(GsonConverterFactory.create())//добавляем конвертор
                .build();
        api = retrofit.create(Api.class);

        retrofit =
                new Retrofit.Builder().baseUrl("https://api.sunrise-sunset.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        sunApi = retrofit.create(SunApi.class);


    }

}