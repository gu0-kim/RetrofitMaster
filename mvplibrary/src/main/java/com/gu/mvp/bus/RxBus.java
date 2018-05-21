package com.gu.mvp.bus;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/** Created by devel on 2018/4/11. */
public class RxBus implements Consumer<Throwable> {
  private Subject<Message> mSubject;
  private Map<Consumer<Message>, Disposable> contains;

  public RxBus() {
    Log.e("TAG", "RxBus: ----create a rxbus!----");
    mSubject = PublishSubject.create();
    contains = new HashMap<>();
  }

  public void send(Message msg) {
    mSubject.onNext(msg);
  }

  /*注册回调，监听所有消息，需要过滤消息*/
  public synchronized void register(Consumer<Message> onNext) {
    Disposable disposable =
        mSubject.observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, this);
    if (!contains.containsKey(onNext)) contains.put(onNext, disposable);
  }

  /*注册回调，只监听指定类型消息*/
  public synchronized <T extends Message> void register(
      Consumer<Message> onNext, Class<T> receiveType) {
    Disposable disposable =
        mSubject
            .observeOn(AndroidSchedulers.mainThread())
            .ofType(receiveType)
            .subscribe(onNext, this);
    if (!contains.containsKey(onNext)) contains.put(onNext, disposable);
  }

  public synchronized void unRegister(Consumer<Message> onNext) {
    Disposable disposable = contains.remove(onNext);
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }

  public void destroyBus() {
    mSubject.onComplete();
    mSubject = null;
    contains.clear();
    contains = null;
  }

  @Override
  public void accept(Throwable throwable) throws Exception {
    throwable.printStackTrace();
  }
}
