package ru.same.starway;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.same.starway.api.PostWeather;
import ru.same.starway.api.SunData;


public class GpsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gps, container, false);
        View close = view.findViewById(R.id.settings_left_button);
        close.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, new SettingsFragment())
                    .commit();
        });
        TextView lat = view.findViewById(R.id.lat);
        TextView lon = view.findViewById(R.id.lon);
        SharedPreferences preferences =
                getActivity().getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE);
        lat.setText(preferences.getString(Constants.gpsLat.name(), ""));
        lon.setText(preferences.getString(Constants.gpsLon.name(), ""));
        Button getGpsButton = view.findViewById(R.id.gpsButton);
        getGpsButton.setOnClickListener(v -> {
            preferences.edit()
                    .putString(Constants.gpsLat.name(), lat.getText().toString())
                    .putString(Constants.gpsLon.name(), lon.getText().toString())
                    .apply();
            App.getApi().getData(lat.getText().toString(), lon.getText().toString(),
                    App.getUNITS(), App.getLANG(), App.getKEY()
            ).enqueue(new Callback<PostWeather>() {
                @Override
                public void onResponse(@NonNull Call<PostWeather> call,
                                       @NonNull Response<PostWeather> response) {
                    if (response.body() != null) {
                        preferences.edit()
                                .putString(Constants.location.name(), response.body().getName())
                                .apply();
                        Log.e("response.body()", response.body().getName());
                    }
                    sendNotify();
                }

                @Override
                public void onFailure(Call<PostWeather> call, Throwable t) {
                    Log.e("response.body()", "null:Failure");
                    sendNotify();
                }
            });


        });
        return view;
    }

    private void sendNotify() {
        AlarmManager alarmManager =
                (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(requireContext(), SunReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                5000,
                24*1000*60*60,
                pendingIntent);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new SettingsFragment())
                .commit();
    }
}