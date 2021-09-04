package ru.same.starway;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainActivity extends AppCompatActivity {
    private int[] backgroundId = new int[]{R.drawable.observatory, R.drawable.telescope,
            R.drawable.observatory2, R.drawable.andromeda, R.drawable.cloud};
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        MyAdapter adapter = new MyAdapter(this);

        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter); // устанавливаем адаптер
        viewPager.setCurrentItem(0); // выводим второй экран
    }

    public static class MyAdapter extends FragmentStateAdapter {


        public MyAdapter(
                @NonNull FragmentActivity fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new NewsFragment();
                default:
                    return new MainFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
//        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentIndex = (currentIndex < backgroundId.length - 1) ? currentIndex + 1 : 0;
//                ((ImageView) findViewById(R.id.background))
//                        .setImageResource(backgroundId[currentIndex]);
//            }
//        });

}