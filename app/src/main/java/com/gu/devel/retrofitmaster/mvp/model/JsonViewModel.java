package com.gu.devel.retrofitmaster.mvp.model;

import android.content.Context;

import com.gu.devel.retrofitmaster.api.WebApiService;
import com.gu.devel.retrofitmaster.app.config.UrlLoader;
import com.gu.devel.retrofitmaster.di.component.ComponentController;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/** Created by devel on 2018/4/8. */
public class JsonViewModel {
  @Inject OkHttpClient mClient;
  @Inject UrlLoader mLoader;

  public JsonViewModel() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  public Observable<ResponseBody> doGet(Context context) throws Exception {
    return getRetrofit(context).create(WebApiService.class).getDataByGet();
  }

  public Observable<ResponseBody> doPost(Context context, String name, String code)
      throws Exception {
    return getRetrofit(context).create(WebApiService.class).getDataByPost(name, code);
  }

  private Retrofit getRetrofit(Context context) throws Exception {
    return new Retrofit.Builder()
        .client(mClient)
        .baseUrl(mLoader.getUrl(context))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}
