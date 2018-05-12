package com.gu.mvp.app;

import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/*<T> dagger single component*/
public class BaseApp extends Application {

  private RefWatcher mRefWatcher;

  public RefWatcher getRefWatcher() {
    return mRefWatcher;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    //    enabledStrictMode();
    mRefWatcher = LeakCanary.install(this);
  }

  private static void enabledStrictMode() {
    StrictMode.setThreadPolicy(
        new StrictMode.ThreadPolicy.Builder() //
            .detectAll() //
            .penaltyLog() //
            .penaltyDeath() //
            .build());
  }
}
