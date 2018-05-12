package com.gu.devel.retrofitmaster.mvp.presenter;

import android.util.Log;

import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.Result;
import com.gu.devel.retrofitmaster.mvp.adapter.data.ProgressItemData;
import com.gu.devel.retrofitmaster.mvp.model.UploadViewModel;
import com.gu.devel.retrofitmaster.mvp.view.UploadView;
import com.gu.mvp.presenter.IPresenter;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/** Created by devel on 2018/4/23. */
public class UploadImagePresenter extends IPresenter<UploadView> {
  @Inject UploadViewModel mModel;

  public UploadImagePresenter() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  public void loadLocal() {
    addTask(
        // 扫描asset文件夹，得到names
        Observable.just(mModel.parseAssetsImage(view.getContext()))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            // 过滤空
            .filter(
                new Predicate<String[]>() {
                  @Override
                  public boolean test(String[] names) throws Exception {
                    return names != null;
                  }
                })
            // 不存在的文件复制到cache文件夹，返回paths
            .flatMap(
                new Function<String[], ObservableSource<List<String>>>() {
                  @Override
                  public ObservableSource<List<String>> apply(String[] names) throws Exception {
                    return Observable.just(mModel.ifNotExistCopy(view.getContext(), names));
                  }
                })
            // paths-->List<ProgressItemData>
            .flatMap(
                new Function<List<String>, ObservableSource<List<ProgressItemData>>>() {
                  @Override
                  public ObservableSource<List<ProgressItemData>> apply(List<String> paths)
                      throws Exception {
                    return Observable.just(mModel.getAdapterDataList(paths));
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<List<ProgressItemData>>() {
                  @Override
                  public void accept(List<ProgressItemData> list) throws Exception {
                    view.finishLoadLocal(true, list);
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    view.finishLoadLocal(false, null);
                  }
                }));
  }

  public void startUpload(String path) {
    addTask(
        Observable.just(new File(path))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .filter(
                new Predicate<File>() {
                  @Override
                  public boolean test(File file) throws Exception {
                    return file.exists();
                  }
                })
            .flatMap(
                new Function<File, ObservableSource<Result>>() {
                  @Override
                  public ObservableSource<Result> apply(File file) throws Exception {
                    return Observable.just(mModel.doUploadByOKHttp(view.getContext(), file));
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<Result>() {
                  @Override
                  public void accept(Result result) throws Exception {
                    view.finishUpload(result.isSuc());
                    Log.e("tag", "----res= " + result.isSuc());
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    view.finishUpload(false);
                  }
                }));
  }
}
