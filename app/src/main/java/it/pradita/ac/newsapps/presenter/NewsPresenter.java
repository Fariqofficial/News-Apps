package it.pradita.ac.newsapps.presenter;

import it.pradita.ac.newsapps.model.News;
import it.pradita.ac.newsapps.utils.Utils;
import it.pradita.ac.newsapps.view.NewsView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenter {

    private NewsView view;

    public NewsPresenter(NewsView view) {
        this.view = view;
    }

    public void getArticle(String category) {
        view.showLoading();

        Call<News> call = Utils.getApi().getNews("us", "c8ae958290e446f9b763d2b133646492", category);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.setArticle(response.body().getArticle());
                } else {
                    view.onError(response.message());
                }
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                view.hideLoading();
                view.onError(t.getLocalizedMessage());
            }
        });
    }

    public void searchArticle(String keyword) {
        view.showLoading();
        String languange = Utils.getLanguage();

        Call<News> search = Utils.getApi().getNewsSearch(keyword, languange, "PublishedAt","c8ae958290e446f9b763d2b133646492");
        search.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.setArticle(response.body().getArticle());
                } else {
                    view.onError(response.message());
                }
                view.hideLoading();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                view.hideLoading();
                view.onError(t.getLocalizedMessage());
            }
        });
    }
}
