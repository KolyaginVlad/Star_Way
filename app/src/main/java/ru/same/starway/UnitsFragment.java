package ru.same.starway;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class UnitsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_units, container, false);
        String check =
                getActivity().getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE)
                        .getString(Constants.units.name(), Units.years.name());
        TextView years = view.findViewById(R.id.light_years);
        TextView parsecs = view.findViewById(R.id.parsecs);
        TextView astronomicalUnits = view.findViewById(R.id.astronomical_units);
        View close = view.findViewById(R.id.settings_left_button);
        if (check.equals(Units.years.name())) {
            years.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
        } else if (check.equals(Units.parsecs.name())) {
            parsecs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
        } else {
            astronomicalUnits.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
        }
        years.setOnClickListener(v -> {
            astronomicalUnits.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            parsecs.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            years.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
            getActivity().getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE)
                    .edit()
                    .putString(Constants.units.name(), Units.years.name())
                    .apply();
        });
        parsecs.setOnClickListener(v -> {
            astronomicalUnits.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            parsecs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
            years.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            getActivity().getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE)
                    .edit()
                    .putString(Constants.units.name(), Units.parsecs.name())
                    .apply();
        });
        astronomicalUnits.setOnClickListener(v -> {
            astronomicalUnits.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.check, 0);
            parsecs.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            years.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            getActivity().getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE)
                    .edit()
                    .putString(Constants.units.name(), Units.astronomical_units.name())
                    .apply();
        });
        close.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, new SettingsFragment())
                    .commit();
        });
        return view;
    }
}