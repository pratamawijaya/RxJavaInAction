package com.pratamawijaya.rxjavaexample.models.pojo;

/**
 * Created by pratama on 7/18/16.
 */
public class City {
  public int id;
  public String name;

  public City(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override public int hashCode() {
    return super.hashCode();
  }
}
