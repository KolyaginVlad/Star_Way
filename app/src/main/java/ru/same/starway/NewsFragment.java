package ru.same.starway;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    List<News> news = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        news.add(new News("Частные космические компании грозят обложить налогом в США...",
                R.drawable.news1));
        news.add(new News("Астрономы выяснили, может ли Солнце поглотить Землю...", R.drawable.news2));
        news.add(new News("Россия отправила в космос один из самых больших модулей МКС",
                R.drawable.news3));
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        NewsAdapter adapter = new NewsAdapter(view.getContext(),news);
        recyclerView.setAdapter(adapter);
        return view;

    }
}
