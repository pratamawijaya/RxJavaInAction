package com.pratamawijaya.rxjavaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.pratamawijaya.rxjavaexample.models.pojo.City;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class BasicFunctionActivity extends AppCompatActivity {

  private static final String TAG = "BasicFunctionActivity";

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
    //  @Override public void call(Subscriber<? super String> subscriber) {
    //    try {
    //      subscriber.onNext("Hello World");
    //      subscriber.onCompleted();
    //    } catch (Exception e) {
    //      subscriber.onError(e);
    //    }
    //  }
    //});

    //Subscriber<String> mySubscriber = new Subscriber<String>() {
    //  @Override public void onCompleted() {
    //    Log.d(TAG, "onCompleted: Completed");
    //  }
    //
    //  @Override public void onError(Throwable e) {
    //    Log.e(TAG, "onError: Error " + e.getLocalizedMessage());
    //  }
    //
    //  @Override public void onNext(String s) {
    //    Log.d(TAG, "onNext: " + s);
    //  }
    //};

    //myObservable.subscribe(mySubscriber);
    // result : Helo World

    //Observable.just("Hello World").subscribe(new Subscriber<String>() {
    //  @Override public void onCompleted() {
    //    Log.d(TAG, "onCompleted: Completed");
    //  }
    //
    //  @Override public void onError(Throwable e) {
    //    Log.e(TAG, "onError: " + e.getLocalizedMessage());
    //  }
    //
    //  @Override public void onNext(String s) {
    //    Log.d(TAG, "onNext: " + s);
    //  }
    //});
    //
    //Observable.just("Hello World")
    //    .subscribe(
    //        s -> Log.d(TAG, "onNext: " + s),
    //        throwable -> Log.e(TAG, "onError: " + throwable.getLocalizedMessage()),
    //        () -> Log.d(TAG, "onCompleted: Completed"));

    // case 1
    //Observable.just(1, 2, 3, 4, 5)
    //    .map(data -> data*2)
    //    .subscribe(new Subscriber<Integer>() {
    //      @Override public void onCompleted() {
    //        Log.d(TAG,"Completed");
    //      }
    //
    //      @Override public void onError(Throwable e) {
    //        Log.e(TAG, "onError: "+e.getLocalizedMessage());
    //      }
    //
    //      @Override public void onNext(Integer integer) {
    //        Timber.d("onNext() :  %s", integer);
    //      }
    //});

    // case 2, filtering data
    //Observable.just(1,2,3,4,5)
    //    .filter(new Func1<Integer, Boolean>() {
    //      @Override public Boolean call(Integer integer) {
    //        return integer%2==0;
    //      }
    //    })
    //    .subscribe(new Subscriber<Integer>() {
    //      @Override public void onCompleted() {
    //        Log.d(TAG,"Completed");
    //      }
    //
    //      @Override public void onError(Throwable e) {
    //        Log.e(TAG, "onError: "+e.getLocalizedMessage());
    //      }
    //
    //      @Override public void onNext(Integer integer) {
    //        Timber.d("onNext() mod2 :  %s", integer);
    //      }
    //    });

    // case 3, chaining function
    //Observable.just(1,2,3,4,5)
    //    .filter(new Func1<Integer, Boolean>() {
    //      @Override public Boolean call(Integer integer) {
    //        return integer%2==0;
    //      }
    //    })
    //    .map(new Func1<Integer, Integer>() {
    //      @Override public Integer call(Integer integer) {
    //        return integer * 2;
    //      }
    //    })
    //    .subscribe(new Subscriber<Integer>() {
    //      @Override public void onCompleted() {
    //        Log.d(TAG,"Completed");
    //      }
    //
    //      @Override public void onError(Throwable e) {
    //        Log.e(TAG, "onError: "+e.getLocalizedMessage());
    //      }
    //
    //      @Override public void onNext(Integer integer) {
    //        Timber.d("onNext() result :  %s", integer);
    //      }
    //    });

    // case 4
    //Observable.just(1, 2, 3, 4, 5)
    //    .filter(integer -> integer % 2 == 0)
    //    .map(integer -> integer * 2)
    //    .subscribe(result -> {
    //      Log.d(TAG, "onCreate: result :" + result);
    //    }, throwable -> {
    //      Log.e(TAG, "onCreate: " + throwable.getLocalizedMessage());
    //    }, () -> {
    //      Log.i(TAG, "onCreate: finished");
    //    });

    //Observable.just(doNetworkOrLongOperation())
    //    .subscribeOn(Schedulers.io())
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe(s -> {
    //      Log.d(TAG, "onCreate: " + s);
    //    }, throwable -> {
    //      Log.e(TAG, "onCreate: " + throwable.getLocalizedMessage());
    //    }, () -> {
    //      Log.i(TAG, "onCreate: completed");
    //    });

    //Button btnOk = (Button) findViewById(R.id.btn_ok);
    //
    //List<Integer> listInteger = new ArrayList<>();
    //listInteger.add(1);
    //listInteger.add(2);
    //listInteger.add(3);
    //listInteger.add(4);
    //listInteger.add(5);
    //listInteger.add(6);
    //listInteger.add(6);
    //listInteger.add(6);
    //listInteger.add(6);
    //listInteger.add(3);
    //listInteger.add(2);
    //listInteger.add(1);
    //
    //getCities()
    //    .flatMap(cities -> Observable.from(cities))
    //    .distinct(city1 -> city1.id)
    //    .subscribe(city -> {
    //  Timber.d("onCreate() :  %s", city.name);
    //});

    compositeSubscription.add(getSomeString().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(s -> {
          Log.d(TAG, "onCreate: " + s);
        }, throwable -> {
          Log.e(TAG, "onCreate: " + throwable.getLocalizedMessage());
        }, () -> {
          Log.i(TAG, "onCreate: completed");
        }));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.unsubscribe();
  }

  //Observable.from(cities)
  //    .distinct(citi -> citi.id)
  //    .observeOn(AndroidSchedulers.mainThread())
  //    .subscribeOn(Schedulers.io())
  //    .subscribe(city -> {
  //      Timber.d("onnext() :  %s", city.name);
  //    }, throwable -> {
  //      Timber.e("onCreate() :  %s", throwable.getLocalizedMessage());
  //    }, () -> {
  //      Timber.d("onCreate() :  completed");
  //    });

  //Observable.from(cities)
  //    .distinct(city -> city.id)
  //    .observeOn(AndroidSchedulers.mainThread())
  //    .subscribeOn(Schedulers.io())
  //    .subscribe(new Subscriber<City>() {
  //      @Override public void onCompleted() {
  //
  //      }
  //
  //      @Override public void onError(Throwable e) {
  //
  //      }
  //
  //      @Override public void onNext(City city) {
  //        Timber.d("onNext() :  %s", city.name);
  //      }
  //    });

  //Observable.from(listInteger)
  //    .distinct()
  //    .map(number -> number * 1000)
  //    .filter(integer -> integer % 2 == 0)
  //    .subscribeOn(Schedulers.io())
  //    .observeOn(AndroidSchedulers.mainThread())
  //    .subscribe(new Subscriber<Integer>() {
  //      @Override public void onCompleted() {
  //        Timber.d("onCompleted() :  ");
  //      }
  //
  //      @Override public void onError(Throwable e) {
  //
  //      }
  //
  //      @Override public void onNext(Integer integer) {
  //        Timber.d("onNext() :  %s", integer);
  //      }
  //    });

  //Observable.just(listInteger)
  //    .subscribeOn(Schedulers.io())
  //    .observeOn(AndroidSchedulers.mainThread())
  //    .subscribe(new Subscriber<List<Integer>>() {
  //      @Override public void onCompleted() {
  //
  //      }
  //
  //      @Override public void onError(Throwable e) {
  //
  //      }
  //
  //      @Override public void onNext(List<Integer> integers) {
  //
  //      }
  //    });

  private Observable<String> getSomeString() {
    return Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        try {
          OkHttpClient client = new OkHttpClient();
          Request request = new Request.Builder().url("SOME URL").build();
          Response response = client.newCall(request).execute();
          if (response != null) {
            final String result = response.body().string();
            subscriber.onNext(result);
            subscriber.onCompleted();
          }
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  public void doSomethingWithCity(List<City> cities) {

    // filter by id
    Observable.from(cities).distinct(c -> c.id).subscribe(city -> {
      Timber.d("doSomethingWithCity() : filter  %s", city.name);
    });

    // take last 2
    Observable.from(cities).takeLast(2).subscribe(city -> {
      Timber.d("doSomethingWithCity() : take last  %s", city.name);
    });
  }

  public String doNetworkOrLongOperation() {
    return "Network";
  }

  public Observable<List<City>> getCities() {
    List<City> cities = new ArrayList<>();
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Pati"));
    cities.add(new City(3, "Klaten"));
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Pati"));
    cities.add(new City(3, "Klaten"));
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Pati"));
    cities.add(new City(3, "Klaten"));
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Pati"));
    cities.add(new City(3, "Klaten"));
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Pati"));
    cities.add(new City(3, "Klaten"));
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Pati"));
    cities.add(new City(3, "Klaten"));
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Pati"));
    cities.add(new City(3, "Klaten"));

    //Observable.just(cities)
    //    .concatMap(data -> Observable.from(data))
    //    .subscribe();

    return Observable.just(cities);
  }
}
