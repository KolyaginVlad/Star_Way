package ru.same.starway;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_layout, container, false);
        View settings = view.findViewById(R.id.settings_button);
        RelativeLayout skyView = view.findViewById(R.id.sky_view);
        TextView skyViewText = view.findViewById(R.id.view_sky_text);
        OnClickSkyView onClickSkyView = new OnClickSkyView();
        skyView.setOnClickListener(onClickSkyView);
        skyViewText.setOnClickListener(onClickSkyView);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Settings.class));
            }
        });
        return view;

    }

    class OnClickSkyView implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), MapActivity.class));
        }
    }
}
