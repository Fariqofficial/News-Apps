package it.pradita.ac.newsapps.view;

import android.view.View;

import java.util.List;

import it.pradita.ac.newsapps.model.Article;

public interface NewsView {

    void setArticle(List<Article> articleList);

    void onError(String message);

    void showLoading();

    void hideLoading();
}
