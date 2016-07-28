package com.pratamawijaya.rxjavaexample.ui.home;

import com.pratamawijaya.rxjavaexample.base.MvpView;
import com.pratamawijaya.rxjavaexample.models.pojo.Post;
import java.util.List;

/**
 * Created by pratama on 7/28/16.
 */
public interface IHomeView extends MvpView {
  void showLoading();

  void hideLoading();

  void setData(List<Post> posts);
}
