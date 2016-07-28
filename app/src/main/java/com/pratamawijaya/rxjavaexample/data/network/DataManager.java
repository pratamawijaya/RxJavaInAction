package com.pratamawijaya.rxjavaexample.data.network;

import com.pratamawijaya.rxjavaexample.models.pojo.Post;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by pratama on 7/28/16.
 */
public class DataManager {
  private PratamaService service;

  public DataManager(PratamaService service) {
    this.service = service;
  }

  public Observable<List<Post>> getRecentPosts(final int page) {
    return service.getRecentPost(page).flatMap(postResponse -> Observable.just(postResponse.posts));
  }

  public Observable<List<Post>> searchPost(final String query) {
    Timber.d("searchPost() :  %s", query);
    return service.searchPost(query).flatMap(postResponse -> Observable.just(postResponse.posts));
  }
}
