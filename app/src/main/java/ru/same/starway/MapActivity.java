package ru.same.starway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;


public class MapActivity extends AppCompatActivity implements OnDrawListener, OnTouchPin {
    private Spinner spinner;
    private BottomSheetBehavior bottomSheetBehaviour;
    private MySubsamplingScaleImageView imageView;
    private View search;
    private View settings;
    private View logo;
    private TextView load;
    private float y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_layout);
        bottomSheetBehaviour =
                BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        bottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviour.setGestureInsetBottomIgnored(true);
        MyAdapterForMapActivity pagerAdapter = new MyAdapterForMapActivity(this);

        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0, false);
        spinner = findViewById(R.id.spinner);
        spinner.setVisibility(View.INVISIBLE);


        clearWindow();
        OneFunction.Companion.avoidDropdownFocus(spinner);
        logo = findViewById(R.id.logo);
        search = findViewById(R.id.search);
        load = findViewById(R.id.load);
        settings = findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, Settings.class));
            }
        });
        settings.setVisibility(View.INVISIBLE);
        search.setVisibility(View.INVISIBLE);
        imageView = new MySubsamplingScaleImageView(this);
        ConstraintLayout constraintLayout = findViewById(R.id.img);
        constraintLayout.addView(imageView);
        imageView.setLayoutParams(
                new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setImage(ImageSource.resource(R.drawable.map));
        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        imageView.setMaxScale(1.2f);
        y = imageView.getY();
        ArrayList<MapPin> mapPins = new ArrayList();
        mapPins.add(new MapPin(4050f, 5250f, 1));
        imageView.setPins(mapPins);
        imageView.setOnDrawListener(this);
        imageView.setOnTouchPinListener(this);
        MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(),
                getResources().getStringArray(R.array.spec));
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.spec, R.layout.spinner_item);
////        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        bottomSheetBehaviour.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    imageView.setFocused(false);
                    spinner.setVisibility(View.INVISIBLE);
                    settings.setVisibility(View.INVISIBLE);
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    imageView.setFocused(true);
                } else {
                    spinner.setVisibility(View.VISIBLE);
                    settings.setVisibility(View.VISIBLE);
                    spinner.setSelection(viewPager.getCurrentItem(), false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                imageView.setFocused(true);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearWindow();
                String choose = getResources().getStringArray(R.array.spec)[position];
                switch (choose) {
                    case "видим..":
                        choose = Spectrum.visible.name();
                        viewPager.setCurrentItem(0, false);
                        break;
                    case "радио..":
                        choose = Spectrum.radio.name();
                        viewPager.setCurrentItem(1, false);
                        break;
                    case "инфра..":
                        choose = Spectrum.infrared.name();
                        viewPager.setCurrentItem(2, false);
                        break;
                    case "ультра..":
                        choose = Spectrum.ultraviolet.name();
                        viewPager.setCurrentItem(3, false);
                        break;
                    case "рент..":
                        choose = Spectrum.xray.name();
                        viewPager.setCurrentItem(4, false);
                        break;
                }
                getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE)
                        .edit()
                        .putString(Constants.spectrum.name(), choose)
                        .apply();
                if (imageView.isReady())
                    imageView.animateScale(imageView.getScale()).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clearWindow();
            }
        });

    }

    private void clearWindow() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onDraw() {
        spinner.setVisibility(View.VISIBLE);
        logo.setVisibility(View.INVISIBLE);
        search.setVisibility(View.VISIBLE);
        settings.setVisibility(View.VISIBLE);
        load.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTouchPin(int id) {
        if (id == 1) {
            ((TextView) findViewById(R.id.title)).setText("крабовидная туманность");
        }
        bottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        imageView.setY(y - 50);

        imageView.setFocused(false);
    }

    @Override
    public void onTouchOutsidePin() {
        bottomSheetBehaviour.setState(BottomSheetBehavior.STATE_HIDDEN);
        imageView.setY(y);

    }

    @Override
    protected void onResume() {
        super.onResume();
        clearWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // Go full screen again when a spinner is closed
        if (hasFocus) {
            clearWindow();
        }
    }


}


