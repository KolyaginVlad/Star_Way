package ru.same.starway;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        LinearLayout linearLayout = view.findViewById(R.id.settings_linear);
        EditText search = view.findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    if (editable.toString().equals("")) {
                        linearLayout.getChildAt(i).setVisibility(View.VISIBLE);
                    } else if (!((TextView) linearLayout.getChildAt(i)).getText().toString()
                            .contains(editable.toString())) {
                        linearLayout.getChildAt(i).setVisibility(View.GONE);
                    }
                }
            }
        });
        return view;
    }
}