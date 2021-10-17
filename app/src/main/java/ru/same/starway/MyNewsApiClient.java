package ru.same.starway;

import android.util.Log;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

public class MyNewsApiClient {
    private static final String KEY_NEWS = "629b9575dc264e838c17256e27de39ab";
    private NewsApiClient newsApiClient;

    public MyNewsApiClient(){
        newsApiClient = new NewsApiClient(KEY_NEWS);

    }
    public void getNewsList(String city, NewsApiClient.ArticlesResponseCallback callback){
        newsApiClient.getEverything(
                new EverythingRequest.Builder().q(city).language("ru").build(),
                callback);
    }
}
