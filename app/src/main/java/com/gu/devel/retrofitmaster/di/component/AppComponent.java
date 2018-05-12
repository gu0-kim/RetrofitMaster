package com.gu.devel.retrofitmaster.di.component;

import com.gu.devel.retrofitmaster.MainActivity;
import com.gu.devel.retrofitmaster.app.config.UrlLoader;
import com.gu.devel.retrofitmaster.di.module.AppModule;
import com.gu.devel.retrofitmaster.mvp.model.ConfigServerModel;
import com.gu.devel.retrofitmaster.mvp.model.DownloadModel;
import com.gu.devel.retrofitmaster.mvp.model.JsonViewModel;
import com.gu.devel.retrofitmaster.mvp.model.UploadViewModel;
import com.gu.devel.retrofitmaster.mvp.presenter.ConfigPresenter;
import com.gu.devel.retrofitmaster.mvp.presenter.DownloadPresenter;
import com.gu.devel.retrofitmaster.mvp.presenter.JsonViewPresenter;
import com.gu.devel.retrofitmaster.mvp.presenter.UploadImagePresenter;
import com.gu.devel.retrofitmaster.mvp.view.ConfigServerView;
import com.gu.devel.retrofitmaster.mvp.view.DownloadView;
import com.gu.devel.retrofitmaster.mvp.view.JsonView;
import com.gu.devel.retrofitmaster.mvp.view.UploadView;

import javax.inject.Singleton;

import dagger.Component;

/** Created by devel on 2018/4/8. */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
  void inject(JsonView view);

  void inject(JsonViewPresenter presenter);

  void inject(JsonViewModel model);

  void inject(UploadViewModel model);

  void inject(UploadImagePresenter presenter);

  void inject(MainActivity activity);

  void inject(DownloadPresenter presenter);

  void inject(DownloadView view);

  void inject(DownloadModel model);
  //
  void inject(UploadView view);

  void inject(ConfigPresenter presenter);

  void inject(ConfigServerView view);

  void inject(UrlLoader loader);

  void inject(ConfigServerModel model);
}
