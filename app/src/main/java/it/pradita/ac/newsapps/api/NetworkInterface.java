package it.pradita.ac.newsapps.api;

import it.pradita.ac.newsapps.model.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {

    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country,
                       @Query("apiKey") String apiKey,
                       @Query("category") String category);

    @GET("everything")
    Call<News> getNewsSearch(@Query("q") String keyword,
                            @Query("language") String language,
                            @Query("sortBy") String sortBy,
                            @Query("apiKey") String apiKey);

}
