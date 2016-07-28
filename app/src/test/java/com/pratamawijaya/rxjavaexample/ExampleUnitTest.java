package com.pratamawijaya.rxjavaexample;

import com.pratamawijaya.rxjavaexample.models.pojo.City;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
  }

  @Test public void testObservable() throws Exception {

    List<City> cities = new ArrayList<>();
    cities.add(new City(1, "Jogja"));
    cities.add(new City(2, "Sleman"));

    Observable<List<City>> obsListCity = Observable.just(cities);

    TestSubscriber<List<City>> testSubscriber = new TestSubscriber<>();
    obsListCity.subscribe(testSubscriber);

    testSubscriber.assertNoErrors();

    //List<City> cities1 = testSubscriber.getOnNextEvents();
  }
}