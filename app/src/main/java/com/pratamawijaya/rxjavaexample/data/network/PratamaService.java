package com.pratamawijaya.rxjavaexample.data.network;

import com.pratamawijaya.rxjavaexample.models.response.PostResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pratama on 7/19/16.
 */
public interface PratamaService {
  @GET("get_recent_posts/") Observable<PostResponse> getRecentPost(@Query("page") int page);

  @GET("get_search_results/") Observable<PostResponse> searchPost(@Query("search") String search);
}
