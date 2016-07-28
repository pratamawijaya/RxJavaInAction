package com.pratamawijaya.rxjavaexample.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratamawijaya.rxjavaexample.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pratama on 7/28/16.
 */
public class PratamaRestClient {

  private PratamaService service;

  public PratamaRestClient() {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.PRATAMA_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    service = retrofit.create(PratamaService.class);
  }

  public PratamaService getService() {
    return service;
  }
}
