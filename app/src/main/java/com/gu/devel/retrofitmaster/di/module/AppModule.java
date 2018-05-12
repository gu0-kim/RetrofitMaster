package com.gu.devel.retrofitmaster.di.module;

import com.gu.devel.retrofitmaster.app.config.UrlLoader;
import com.gu.devel.retrofitmaster.app.config.PropertyLoader;
import com.gu.devel.retrofitmaster.app.config.SharePreferenceController;
import com.gu.devel.retrofitmaster.mvp.model.ConfigServerModel;
import com.gu.devel.retrofitmaster.mvp.model.DownloadModel;
import com.gu.devel.retrofitmaster.mvp.model.JsonViewModel;
import com.gu.devel.retrofitmaster.mvp.model.UploadViewModel;
import com.gu.devel.retrofitmaster.mvp.presenter.ConfigPresenter;
import com.gu.devel.retrofitmaster.mvp.presenter.DownloadPresenter;
import com.gu.devel.retrofitmaster.mvp.presenter.JsonViewPresenter;
import com.gu.devel.retrofitmaster.mvp.presenter.UploadImagePresenter;
import com.gu.mvp.bus.RxBus;
import com.gu.mvp.utils.file.FileUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/** Created by devel on 2018/4/8. */
@Module
public class AppModule {

  public static final long TIMEOUT_CONNECT = 1000;
  public static final long TIMEOUT_READ = 8000;
  public static final long TIMEOUT_WRITE = 6000;

  @Singleton
  @Provides
  OkHttpClient getClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
        .build();
  }

  @Provides
  @Singleton
  RxBus getBus() {
    return new RxBus();
  }

  @Provides
  @Singleton
  PropertyLoader getPropertyLoader() {
    return new PropertyLoader();
  }

  @Provides
  @Singleton
  SharePreferenceController getSharePreferenceController() {
    return new SharePreferenceController();
  }

  @Provides
  @Singleton
  UrlLoader getConfigLoader() {
    return new UrlLoader();
  }

  @Provides
  @Singleton
  FileUtil getFileUtil() {
    return new FileUtil();
  }

  @Provides
  JsonViewPresenter getIndexPresenter() {
    return new JsonViewPresenter();
  }

  @Provides
  UploadImagePresenter getUploadImagePresenter() {
    return new UploadImagePresenter();
  }

  @Provides
  JsonViewModel getIndexModule() {
    return new JsonViewModel();
  }

  @Provides
  UploadViewModel getUploadViewModel() {
    return new UploadViewModel();
  }

  @Provides
  DownloadModel getDownloadModel() {
    return new DownloadModel();
  }

  @Provides
  DownloadPresenter getDownloadPresenter() {
    return new DownloadPresenter();
  }

  @Provides
  ConfigServerModel getConfigServerModel() {
    return new ConfigServerModel();
  }

  @Provides
  ConfigPresenter getConfigPresenter() {
    return new ConfigPresenter();
  }
}
