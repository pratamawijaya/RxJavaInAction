package com.pratamawijaya.rxjavaexample.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.pratamawijaya.rxjavaexample.R;
import com.pratamawijaya.rxjavaexample.data.network.DataManager;
import com.pratamawijaya.rxjavaexample.data.network.PratamaRestClient;
import com.pratamawijaya.rxjavaexample.models.pojo.Post;
import com.pratamawijaya.rxjavaexample.presenter.HomePresenter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class HomeViewActivity extends AppCompatActivity implements IHomeView {

  private static final int START_PAGE = 1;
  private HomePresenter presenter;
  private DataManager dataManager;
  private PratamaRestClient restClient;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_view);
    ButterKnife.bind(this);

    restClient = new PratamaRestClient();
    dataManager = new DataManager(restClient.getService());
    presenter = new HomePresenter(dataManager);

    presenter.attachView(this);

    presenter.getRecentPosts(START_PAGE);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

    setupSearchView(searchView);
    return true;
  }

  private void setupSearchView(SearchView searchView) {
    compositeSubscription.add(RxSearchView.queryTextChanges(searchView)
        .filter(text -> !TextUtils.isEmpty(text))
        .debounce(250, TimeUnit.MILLISECONDS)
        .flatMap(query -> dataManager.searchPost(query.toString()))
        .subscribe(posts -> {
          if (posts != null) {
            setData(posts);
          }
        }, throwable -> {
          Timber.e("setupSearchView() :  %s", throwable.getLocalizedMessage());
        }));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
    if (compositeSubscription != null) {
      compositeSubscription.unsubscribe();
    }
  }

  @Override public void showLoading() {
    Timber.d("showLoading()");
  }

  @Override public void hideLoading() {
    Timber.d("hideLoading() :");
  }

  @Override public void setData(List<Post> posts) {
    // TODO: 7/28/16 do something here, pass to adapter
    for (Post data : posts) {
      Timber.d("setData() :  %s", data.title);
    }
  }
}
