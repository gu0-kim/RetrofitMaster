package com.gu.devel.retrofitmaster.mvp.presenter;

import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.ResponseData;
import com.gu.devel.retrofitmaster.mvp.model.JsonViewModel;
import com.gu.devel.retrofitmaster.mvp.view.JsonView;
import com.gu.mvp.presenter.IPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/** Created by devel on 2018/4/8. */
public class JsonViewPresenter extends IPresenter<JsonView> {

  @Inject JsonViewModel model;

  public JsonViewPresenter() {
    //    super();
    ComponentController.getInstance().getComponent().inject(this);
  }

  /** 执行http的Get方法 */
  public void requestGet() {
    addTask(
        Observable.just("doGet")
            .observeOn(Schedulers.io())
            .flatMap(
                new Function<String, ObservableSource<ResponseBody>>() {
                  @Override
                  public ObservableSource<ResponseBody> apply(String s) throws Exception {
                    return model.doGet(view.getContext());
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<ResponseBody>() {
                  @Override
                  public void accept(ResponseBody responseBody) throws Exception {
                    String res = responseBody.string();
                    ResponseData data = new Gson().fromJson(res, ResponseData.class);
                    view.showParseResult(data.getResult());
                    view.showHttpResult(res);
                    if (view.getActivity() != null)
                      view.showToast(view.getActivity().getApplicationContext(), "成功");
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    view.showHttpResult("访问异常");
                  }
                }));
  }

  /** 执行http的Post方法 */
  public void requestPost(final String name, final String code) {
    addTask(
        Observable.just("doPost")
            .observeOn(Schedulers.io())
            .flatMap(
                new Function<String, ObservableSource<ResponseBody>>() {
                  @Override
                  public ObservableSource<ResponseBody> apply(String arg) throws Exception {
                    return model.doPost(view.getContext(), name, code);
                  }
                })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<ResponseBody>() {
                  @Override
                  public void accept(ResponseBody responseBody) throws Exception {
                    String res = responseBody.string();
                    ResponseData data = new Gson().fromJson(res, ResponseData.class);
                    view.showParseResult(data.getResult());
                    view.showHttpResult(res);
                    if (view.getActivity() != null)
                      view.showToast(view.getActivity().getApplicationContext(), "成功");
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    view.showHttpResult("发生异常");
                  }
                }));
  }

  @Override
  public void releaseView() {
    super.releaseView();
  }

  /**
   * 避免双击操作
   *
   * @param btn click view
   * @param onClick callback
   */
  public void setSingleClick(View btn, Consumer<Object> onClick) {
    addTask(
        RxView.clicks(btn)
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onClick));
  }

  /**
   * 监听输入
   *
   * @param nameEd name字段editText
   * @param codeEd code字段editText
   */
  public void listenTextValid(final EditText nameEd, final EditText codeEd) {
    Observable<CharSequence> observableName = RxTextView.textChanges(nameEd);
    Observable<CharSequence> observableCode = RxTextView.textChanges(codeEd);
    addTask(
        Observable.combineLatest(
                observableName,
                observableCode,
                new BiFunction<CharSequence, CharSequence, Boolean>() {
                  @Override
                  public Boolean apply(CharSequence charSequence, CharSequence charSequence2)
                      throws Exception {
                    return view.validInput(nameEd) && view.validInput(codeEd);
                  }
                })
            .subscribe(
                new Consumer<Boolean>() {
                  @Override
                  public void accept(Boolean valid) throws Exception {
                    view.setPostBtnValid(valid);
                  }
                }));
  }
}
