package ru.same.starway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<News> news;

    public NewsAdapter(Context context, List<News> news) {
        this.inflater = LayoutInflater.from(context);
        this.news = news;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position > news.size()) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View itemLayoutView;

        //загружаем разметку в зависимости от типа и возвращаем
        //нужный холдер
        switch (viewType) {
            case 2:
                itemLayoutView = inflater.inflate(R.layout.card,
                        parent, false);
                return new NewsHolder(itemLayoutView);
            case 0:
                itemLayoutView = inflater.inflate(R.layout.text,
                        parent, false);
                return new FirstTextHolder(itemLayoutView);
            default:
                itemLayoutView = inflater.inflate(R.layout.second_text,
                        parent, false);
                return new SecondTextHolder(itemLayoutView);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position == 0) {
            FirstTextHolder textHolder = (FirstTextHolder) holder;
            textHolder.title.setText(R.string.news);
        } else if (position > news.size()) {
            SecondTextHolder textHolder = (SecondTextHolder) holder;
            textHolder.title.setText(R.string.news_end);
        } else {
            NewsHolder newsHolder = (NewsHolder) holder;
            News n = news.get(position - 1);
            newsHolder.title.setText(n.getText());
            newsHolder.image.loadUrl(n.imageUrl);
        }

    }

    @Override
    public int getItemCount() {
        return news.size() + 2;
    }


    public static class NewsHolder extends RecyclerView.ViewHolder {
        final WebView image;
        final TextView title;

        NewsHolder(View view) {
            super(view);
            title = view.findViewById(R.id.textOfNews);
            image = view.findViewById(R.id.imageOfNews);
        }


    }

    public static class FirstTextHolder extends RecyclerView.ViewHolder {
        final TextView title;

        public FirstTextHolder(View view) {
            super(view);
            title = view.findViewById(R.id.someText);
        }
    }

    public static class SecondTextHolder extends RecyclerView.ViewHolder {
        final TextView title;

        public SecondTextHolder(View view) {
            super(view);
            title = view.findViewById(R.id.someText);
        }
    }
}
