package com.gu.devel.retrofitmaster.mvp.presenter;

import com.gu.devel.retrofitmaster.api.json.ImageInfoData;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.adapter.data.DownloadItemData;
import com.gu.devel.retrofitmaster.mvp.model.DownloadModel;
import com.gu.devel.retrofitmaster.mvp.view.DownloadView;
import com.gu.mvp.presenter.IPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/** Created by devel on 2018/5/1. */
public class DownloadPresenter extends IPresenter<DownloadView> {
  @Inject DownloadModel mModel;

  public DownloadPresenter() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  public void loadLocal() {
    addTask(
        Observable.just("loadlist")
            .observeOn(Schedulers.io())
            .flatMap(
                new Function<String, ObservableSource<List<ImageInfoData>>>() {
                  @Override
                  public ObservableSource<List<ImageInfoData>> apply(String args) throws Exception {
                    return mModel.loadServerImageList(view.getContext());
                  }
                })
            .filter(
                new Predicate<List<ImageInfoData>>() {
                  @Override
                  public boolean test(List<ImageInfoData> list) throws Exception {
                    return list != null;
                  }
                })
            .map(
                new Function<List<ImageInfoData>, List<DownloadItemData>>() {
                  @Override
                  public List<DownloadItemData> apply(List<ImageInfoData> list) throws Exception {
                    List<DownloadItemData> res =
                        mModel.updateDownloaded(
                            view.getContext(), mModel.convertData(view.getContext(), list));
                    list.clear();
                    return res;
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<List<DownloadItemData>>() {
                  @Override
                  public void accept(List<DownloadItemData> list) throws Exception {
                    view.finishLoadLocal(true, list);
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    view.finishLoadLocal(false, null);
                    throwable.printStackTrace();
                  }
                }));
  }

  public void startDownload(String url, final String saveName) {
    addTask(
        Observable.just(url)
            .observeOn(Schedulers.io())
            .map(
                new Function<String, String>() {
                  @Override
                  public String apply(String url) throws Exception {
                    return mModel.downloadWithProgress(view.getContext(), url, saveName);
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<String>() {
                  @Override
                  public void accept(String filePath) throws Exception {
                    view.finishDownload(true, filePath);
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    view.finishDownload(false, null);
                  }
                }));
  }
}
