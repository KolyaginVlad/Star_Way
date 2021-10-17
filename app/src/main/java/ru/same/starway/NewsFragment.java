package ru.same.starway;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

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
        view.findViewById(R.id.bar).setVisibility(View.VISIBLE);
        SharedPreferences
                preferences =
                getActivity().getSharedPreferences(Constants.name.name(), Context.MODE_PRIVATE);
        MyNewsApiClient myNewsApiClient = new MyNewsApiClient();
        myNewsApiClient.getNewsList(preferences.getString(Constants.location.name(), "Москва"),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse articleResponse) {
                        if (articleResponse.getArticles().size() != 0) {
                            for (int i = 0; i < articleResponse.getArticles().size(); i++) {
                                news.add(new News(
                                        articleResponse.getArticles().get(i).getDescription(),
                                        articleResponse.getArticles().get(i).getUrlToImage()));
                            }
                        } else {
                            news.add(new News(
                                    "Частные космические компании грозят обложить налогом в США...",
                                    "https://oir.mobi/uploads/posts/2021-03/1616601588_1-p-fon-kosmos-zvezdi-1.jpg"));
                            news.add(new News("Астрономы выяснили, может ли Солнце поглотить Землю...",
                                    "https://oir.mobi/uploads/posts/2021-03/1616601588_1-p-fon-kosmos-zvezdi-1.jpg"));
                            news.add(new News(
                                    "Россия отправила в космос один из самых больших модулей МКС",
                                    "https://oir.mobi/uploads/posts/2021-03/1616601588_1-p-fon-kosmos-zvezdi-1.jpg"));
                        }
                        RecyclerView recyclerView = view.findViewById(R.id.recycler);
                        NewsAdapter adapter = new NewsAdapter(view.getContext(), news);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        view.findViewById(R.id.bar).setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        news.add(new News(
                                "Частные космические компании грозят обложить налогом в США...",
                                "https://oir.mobi/uploads/posts/2021-03/1616601588_1-p-fon-kosmos-zvezdi-1.jpg"));
                        news.add(new News("Астрономы выяснили, может ли Солнце поглотить Землю...",
                                "https://oir.mobi/uploads/posts/2021-03/1616601588_1-p-fon-kosmos-zvezdi-1.jpg"));
                        news.add(new News(
                                "Россия отправила в космос один из самых больших модулей МКС",
                                "https://oir.mobi/uploads/posts/2021-03/1616601588_1-p-fon-kosmos-zvezdi-1.jpg"));
                        RecyclerView recyclerView = view.findViewById(R.id.recycler);
                        NewsAdapter adapter = new NewsAdapter(view.getContext(), news);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        view.findViewById(R.id.bar).setVisibility(View.GONE);
                    }
                });
        return view;

    }
}
