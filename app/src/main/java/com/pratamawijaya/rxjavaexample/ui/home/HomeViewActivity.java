package com.pratamawijaya.rxjavaexample.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.pratamawijaya.rxjavaexample.R;
import com.pratamawijaya.rxjavaexample.data.network.DataManager;
import com.pratamawijaya.rxjavaexample.data.network.PratamaRestClient;
import com.pratamawijaya.rxjavaexample.models.pojo.Post;
import com.pratamawijaya.rxjavaexample.presenter.HomePresenter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class HomeViewActivity extends AppCompatActivity implements IHomeView {

  private static final int START_PAGE = 1;

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private ProgressDialog progressDialog;

  private HomeAdapter adapter;
  private List<Post> posts;
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
    posts = new ArrayList<>();
    adapter = new HomeAdapter(this, posts);
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Loading...");

    setupRecyclerView();

    presenter.attachView(this);

    presenter.getRecentPosts(START_PAGE);
  }

  private void setupRecyclerView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

    setupSearchView(searchView);
    return true;
  }

  private void setupSearchView(SearchView searchView) {
    compositeSubscription.add(RxSearchView.queryTextChanges(searchView)
        .debounce(450, TimeUnit.MILLISECONDS)
        .filter(text -> !TextUtils.isEmpty(text))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(posts -> {
          Timber.d("setupSearchView() :  %s", posts.toString());
          handleResultSearch(posts.toString());
        }));
  }

  private void handleResultSearch(String search) {
    presenter.searchPost(search);
    Toast.makeText(HomeViewActivity.this, "" + search, Toast.LENGTH_SHORT).show();
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
    progressDialog.show();
  }

  @Override public void hideLoading() {
    Timber.d("hideLoading() :");
    progressDialog.dismiss();
  }

  @Override public void setData(List<Post> posts) {
    this.posts.addAll(posts);
    adapter.notifyDataSetChanged();
  }

  @Override public void handleSearch(List<Post> posts) {
    this.posts.clear();
    this.posts.addAll(posts);
    adapter.notifyDataSetChanged();
  }
}
