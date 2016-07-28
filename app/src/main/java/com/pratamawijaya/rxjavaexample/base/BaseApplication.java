package com.pratamawijaya.rxjavaexample.base;

import android.app.Application;
import com.pratamawijaya.rxjavaexample.BuildConfig;
import timber.log.Timber;

/**
 * Created by pratama on 7/18/16.
 */
public class BaseApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    if(BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
  }
}
