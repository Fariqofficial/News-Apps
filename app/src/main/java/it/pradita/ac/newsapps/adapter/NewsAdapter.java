package it.pradita.ac.newsapps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.pradita.ac.newsapps.R;
import it.pradita.ac.newsapps.model.Article;
import it.pradita.ac.newsapps.utils.Utils;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> articleList;
    private Context context;
    private OnItemClickCallback onItemClickCallback;

    public NewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String imageNews = articleList.get(position).getUrlToImage();

        Glide.with(context)
                .load(imageNews)
                .into(holder.img_news);

        String textAuthor = articleList.get(position).getAuthor();
        holder.tv_author.setText(textAuthor);

        String textTitle = articleList.get(position).getTitle();
        holder.tv_title.setText(textTitle);

        String textDesc = articleList.get(position).getDescription();
        holder.tv_desc.setText(textDesc);

        String textSource = articleList.get(position).getSource().getName();
        holder.tv_source.setText(textSource);

        holder.tv_Time.setText(" \u2022 " + Utils.DateTimeFormat(articleList.get(position).getPublishedAt()));

        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(articleList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAuthor)
        TextView tv_author;

        @BindView(R.id.tvTitle)
        TextView tv_title;

        @BindView(R.id.tvDesc)
        TextView tv_desc;

        @BindView(R.id.tvSource)
        TextView tv_source;

        @BindView(R.id.tvTime)
        TextView tv_Time;

        @BindView(R.id.imgNews)
        ImageView img_news;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Article data);
    }
}
