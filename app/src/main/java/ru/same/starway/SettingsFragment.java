package ru.same.starway;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        View close = view.findViewById(R.id.settings_close_button);
        TextView units = view.findViewById(R.id.units);
        TextView gps = view.findViewById(R.id.gps);
        close.setOnClickListener(v -> getActivity().finish());
        units.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new UnitsFragment())
                .commit());
        gps.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, new GpsFragment())
                .commit());
        return view;
    }
}