package it.pradita.ac.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.pradita.ac.newsapps.model.Article;
import it.pradita.ac.newsapps.utils.Utils;

public class DetailNewsActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView titleTv;
    @BindView(R.id.tvTime)
    TextView timeTv;
    @BindView(R.id.imgNews)
    ImageView newsImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        ButterKnife.bind(this);
        setUpNewsDetail();
     }

    private void setUpNewsDetail() {
        Article data = (Article) getIntent().getExtras().get("data");
        String urlWebview = data.getUrl();
        titleTv.setText(data.getTitle());
        timeTv.setText("" + Utils.DateTimeFormat(data.getPublishedAt()));
        String strImage = data.getUrlToImage();

        Glide.with(this).load(strImage).into(newsImg);

        initWebView(urlWebview);
    }

    private void initWebView(String urlWebview) {
        WebView webView = findViewById(R.id.webViewDetail);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlWebview);
    }
}