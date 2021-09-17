package ru.same.starway;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapterForMapActivity extends FragmentStateAdapter {
    private final FragmentActivity activity;

    public MyAdapterForMapActivity(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        activity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:

                return new InfoFragment(1, Spectrum.visible.name());
            case 1:

                return new InfoFragment(1, Spectrum.radio.name());
            case 2:

                return new InfoFragment(1, Spectrum.infrared.name());
            case 3:

                return new InfoFragment(1, Spectrum.ultraviolet.name());
            default:

                return new InfoFragment(1, Spectrum.xray.name());
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
