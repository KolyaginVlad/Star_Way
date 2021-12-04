package ru.same.starway;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.same.starway.api.SunData;

public class SunReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.name.name(),
                Context.MODE_PRIVATE);

        App.getSunApi().getData(preferences.getString(Constants.gpsLat.name(), "55.75"),
                preferences.getString(Constants.gpsLon.name(), "35.6")).enqueue(
                new Callback<SunData>() {
                    @Override
                    public void onResponse(Call<SunData> call, Response<SunData> response) {
                        if (response.body() != null) {
                            String des = "Время восхода солнца " + response.body()
                                    .getResults()
                                    .getSunrise() + "\nВремя захода " +
                                    "солнца " + response
                                    .body().getResults().getSunset();
                            NotificationCompat.Builder builder =
                                    new NotificationCompat.Builder(context,
                                            Constants.StarWay.name())
                                            .setSmallIcon(R.drawable.news2)
                                            .setContentTitle(
                                                    context.getString(R.string.time_sunrise_and_sunset))
                                            .setContentText(des)
                                            .setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText(des))
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                            NotificationManagerCompat notificationManager =
                                    NotificationManagerCompat.from(context);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                                notificationManager.createNotificationChannel(
                                        new NotificationChannel(Constants.StarWay.name(),
                                                Constants.StarWay.name(),
                                                NotificationManager.IMPORTANCE_DEFAULT));
                            notificationManager.notify(1001, builder.build());

                        }
                    }

                    @Override
                    public void onFailure(Call<SunData> call, Throwable t) {
                    }
                }
        );
    }
}
