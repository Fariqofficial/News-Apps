package it.pradita.ac.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.pradita.ac.newsapps.adapter.NewsAdapter;
import it.pradita.ac.newsapps.model.Article;
import it.pradita.ac.newsapps.presenter.NewsPresenter;
import it.pradita.ac.newsapps.view.NewsView;

public class MainActivity extends AppCompatActivity implements NewsView, View.OnClickListener {

    public static final String EXTRA_DETAIL = "detail";
    public static final String EXTRA_POSITION = "position";

    @BindView(R.id.rvDataNews)
    RecyclerView rvNews;

    @BindView(R.id.cvBusiness)
    CardView cardBusiness;

    @BindView(R.id.cvEntertainment)
    CardView cardEntertainment;

    @BindView(R.id.cvHealth)
    CardView cardHealth;

    @BindView(R.id.cvSport)
    CardView cardSport;

    @BindView(R.id.progressBar)
    ProgressBar loading;

    private NewsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initsetOnClickListener();
        presenter = new NewsPresenter(this);
        presenter.getArticle(null);

    }

    private void initsetOnClickListener() {
        cardBusiness.setOnClickListener(this);
        cardEntertainment.setOnClickListener(this);
        cardHealth.setOnClickListener(this);
        cardSport.setOnClickListener(this);
    }

    @Override
    public void setArticle(List<Article> articleList) {

        NewsAdapter adapter = new NewsAdapter(articleList, this);
        rvNews.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(layoutManager);
        rvNews.setNestedScrollingEnabled(true);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickCallback(data -> {
            showNewsDetail(data);
        });
    }

    private void showNewsDetail(Article data) {
        Intent intent = new Intent(this, DetailNewsActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cvBusiness:
                presenter.getArticle("business");
                break;
            case R.id.cvEntertainment:
                presenter.getArticle("entertainment");
                break;
            case R.id.cvHealth:
                presenter.getArticle("health");
                break;
            case R.id.cvSport:
                presenter.getArticle("sport");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search news...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                presenter.searchArticle(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}