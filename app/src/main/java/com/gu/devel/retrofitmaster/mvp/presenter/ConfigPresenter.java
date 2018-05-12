package com.gu.devel.retrofitmaster.mvp.presenter;

import android.util.Log;

import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.model.ConfigServerModel;
import com.gu.devel.retrofitmaster.mvp.view.ConfigServerView;
import com.gu.mvp.presenter.IPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/** Created by devel on 2018/5/7. */
public class ConfigPresenter extends IPresenter<ConfigServerView> {
  @Inject ConfigServerModel mModel;

  public ConfigPresenter() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  public void load() {
    addTask(
        Observable.just("load")
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(
                new Consumer<String>() {
                  @Override
                  public void accept(String arg) throws Exception {
                    view.showLoading();
                  }
                })
            .observeOn(Schedulers.io())
            .map(
                new Function<String, String>() {
                  @Override
                  public String apply(String arg) throws Exception {
                    return mModel.getServerUrl(view.getContext());
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<String>() {
                  @Override
                  public void accept(String url) throws Exception {
                    Log.e("tag", "accept: ---------" + url);
                    view.setEditText(url);
                    view.invisibleLoading();
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    view.invisibleLoading();
                  }
                }));
  }

  public void save() {
    addTask(
        Observable.just("save")
            .observeOn(AndroidSchedulers.mainThread())
            .map(
                new Function<String, String>() {
                  @Override
                  public String apply(String arg) throws Exception {
                    view.showLoading();
                    return view.getEditText();
                  }
                })
            .observeOn(Schedulers.io())
            .map(
                new Function<String, Boolean>() {
                  @Override
                  public Boolean apply(String url) throws Exception {
                    if (url == null) {
                      throw new Exception("内容不能为空");
                    } else return mModel.saveServerUrl(view.getContext(), url);
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<Boolean>() {
                  @Override
                  public void accept(Boolean res) throws Exception {
                    view.invisibleLoading();
                    if (view.getActivity() != null)
                      view.showToast(view.getActivity().getApplicationContext(), "保存成功");
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    view.invisibleLoading();
                    if (view.getActivity() != null)
                      view.showToast(
                          view.getActivity().getApplicationContext(),
                          "保存失败：" + throwable.getMessage());
                  }
                }));
  }
}
