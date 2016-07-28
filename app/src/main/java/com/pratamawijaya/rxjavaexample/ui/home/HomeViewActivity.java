package com.pratamawijaya.rxjavaexample.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.pratamawijaya.rxjavaexample.R;
import com.pratamawijaya.rxjavaexample.data.network.DataManager;
import com.pratamawijaya.rxjavaexample.data.network.PratamaRestClient;
import com.pratamawijaya.rxjavaexample.models.pojo.Post;
import com.pratamawijaya.rxjavaexample.presenter.HomePresenter;
import java.util.List;
import timber.log.Timber;

public class HomeViewActivity extends AppCompatActivity implements IHomeView {

  private static final int START_PAGE = 1;
  private HomePresenter presenter;
  private DataManager dataManager;
  private PratamaRestClient restClient;

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

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
  }

  @Override public void showLoading() {
    Timber.d("showLoading()");
  }

  @Override public void hideLoading() {
    Timber.d("hideLoading() :");
  }

  @Override public void setData(List<Post> posts) {
    for (Post data : posts) {
      Timber.d("setData() :  %s", data.title);
    }
  }
}
