package com.pratamawijaya.rxjavaexample.base;

/**
 * Created by pratama on 7/28/16.
 */
public interface Presenter<V extends MvpView> {

  void attachView(V mvpView);

  void detachView();
}
