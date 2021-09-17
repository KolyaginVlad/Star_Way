package ru.same.starway;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {
    private final int objectId;
    private final String spec;
    private TextView textview;
    private String last;
    public InfoFragment(int objectId, String spec) {
        this.objectId = objectId;
        this.spec = spec;
    }

    @Override
    public void onResume() {
        super.onResume();

        String distance = "";
        String unit = getActivity().getSharedPreferences(Constants.name.name(),
                Context.MODE_PRIVATE)
                .getString(Constants.units.name(), Units.years
                        .name());
        if (last.equals(unit)) return;
        if (unit.equals(Units.years.name())) distance = "расстояние от Земли\n6500 св.л.";
        else if (unit.equals(Units.parsecs.name()))
            distance = "расстояние от Земли\n1 993.046 парсеков";
        else distance = "расстояние от Земли\n4.11 * 10^8 a.e.";
        Spannable spans = new SpannableString(distance);
        spans.setSpan(new ForegroundColorSpan(Color.parseColor("#979797")), 0, 19,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(spans);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info_layout, container, false);
        if (objectId == 1) {
            int id = 0;
            String specName = "";
            if (spec.equals(Spectrum.visible.name())) {
                id = R.drawable.visible;
                specName = "видимый свет";
            } else if (spec.equals(Spectrum.infrared.name())) {
                id = R.drawable.infrared;
                specName = "инфракрасные волны";
            } else if (spec.equals(Spectrum.ultraviolet.name())) {
                id = R.drawable.ultraviolet;
                specName = "ультрафиолет";
            } else if (spec.equals(Spectrum.radio.name())) {
                id = R.drawable.radio;
                specName = "радиоволны";
            } else {
                id = R.drawable.xray;
                specName = "рентгеновское излучение";
            }
            ((ImageView) view.findViewById(R.id.imageOfObject)).setImageResource(id);
            ((TextView) view.findViewById(R.id.specOfObject)).setText(specName);
            Spannable span1 = new SpannableString("NGC 1952");
            span1.setSpan(new ForegroundColorSpan(Color.parseColor("#979797")), 0, 3,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Spannable span2 = new SpannableString("м1");
            span2.setSpan(new ForegroundColorSpan(Color.parseColor("#979797")), 0, 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) view.findViewById(R.id.type2)).setText(span1);
            ((TextView) view.findViewById(R.id.type1)).setText(span2);
            String distance = "";
            String unit = getActivity().getSharedPreferences(Constants.name.name(),
                    Context.MODE_PRIVATE)
                    .getString(Constants.units.name(), Units.years
                            .name());
            if (unit.equals(Units.years.name())) distance = "расстояние от Земли\n6500 св.л.";
            else if (unit.equals(Units.parsecs.name()))
                distance = "расстояние от Земли\n1 993.046 парсеков";
            else distance = "расстояние от Земли\n4.11 * 10^8 a.e.";
            textview = (TextView) view.findViewById(R.id.distance);
            Spannable spans = new SpannableString(distance);
            spans.setSpan(new ForegroundColorSpan(Color.parseColor("#979797")), 0, 19,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textview.setText(spans);
            last = unit;
        }
        return view;
    }
}
