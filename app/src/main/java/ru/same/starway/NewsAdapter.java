package ru.same.starway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public int getItemViewType(int position)
    {
        if (position == 0||position>news.size())
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View itemLayoutView;

        //загружаем разметку в зависимости от типа и возвращаем
        //нужный холдер
        switch (viewType)
        {
            case 1:
                itemLayoutView = inflater.inflate(R.layout.card,
                        parent, false);
                return new NewsHolder(itemLayoutView);
            default:
                itemLayoutView = inflater.inflate(R.layout.text,
                        parent, false);
                return new TextHolder(itemLayoutView);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position==0||position>news.size()){
            TextHolder textHolder = (TextHolder) holder;
            if (position==0)
            textHolder.title.setText(R.string.news);
            else textHolder.title.setText(R.string.news_end);
        }else {
            NewsHolder newsHolder = (NewsHolder) holder;
            News n = news.get(position-1);
            newsHolder.title.setText(n.getText());
            newsHolder.image.setImageResource(n.imageId);
        }

    }

    @Override
    public int getItemCount() {
        return news.size()+2;
    }


    public static class NewsHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView title;

        NewsHolder(View view) {
            super(view);
            title = view.findViewById(R.id.textOfNews);
            image = view.findViewById(R.id.imageOfNews);
        }


    }
    public static class TextHolder extends RecyclerView.ViewHolder {
        final TextView title;

        TextHolder(View view) {
            super(view);
            title = view.findViewById(R.id.someText);
        }


    }
}
