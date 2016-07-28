package com.pratamawijaya.rxjavaexample.presenter;

import com.pratamawijaya.rxjavaexample.base.BasePresenter;
import com.pratamawijaya.rxjavaexample.data.network.DataManager;
import com.pratamawijaya.rxjavaexample.ui.home.IHomeView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by pratama on 7/28/16.
 */
public class HomePresenter extends BasePresenter<IHomeView> {
  private DataManager dataManager;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  public HomePresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override public void attachView(IHomeView mvpView) {
    super.attachView(mvpView);
  }

  @Override public void detachView() {
    super.detachView();
    if (compositeSubscription != null) {
      compositeSubscription.unsubscribe();
    }
  }

  public void getRecentPosts(final int page) {
    checkViewAttached();
    compositeSubscription.add(dataManager.getRecentPosts(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(posts -> {
          getMvpView().hideLoading();
          if (posts != null) {
            getMvpView().setData(posts);
          }
        }, throwable -> {
          getMvpView().hideLoading();
          Timber.e("getRecentPosts() :  %s", throwable.getLocalizedMessage());
        }, () -> {
          Timber.d("getRecentPosts() : completed ");
        }));
  }
}
