package com.gu.devel.retrofitmaster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.gu.devel.retrofitmaster.app.RetrofitApp;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.view.MainView;
import com.gu.devel.retrofitmaster.mvp.view.ViewType;
import com.gu.mvp.bus.RxBus;
import com.gu.mvp.utils.activity.StatusBarCompat;
import com.gu.mvp.utils.leaks.CleanLeakUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  @Inject RxBus mRxBus;

  @BindView(R.id.content_layout)
  FrameLayout content_layout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ComponentController.getInstance().getComponent().inject(this);
    ButterKnife.bind(this);
    MainView fragment =
        (MainView) getSupportFragmentManager().findFragmentById(R.id.content_layout);
    if (fragment == null) {
      fragment = MainView.newInstance();
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.content_layout, fragment, ViewType.MAIN_VIEW.tagName())
          .commit();
    }
    StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    CleanLeakUtils.fixInputMethodManagerLeak(this);
    // 释放总线
    mRxBus.destroyBus();
    mRxBus = null;
    Log.e("TAG", "MainActivity onDestroy()");
    ComponentController.getInstance().release();
    ((RetrofitApp) getApplication()).getRefWatcher().watch(this);
  }

  public void startFragment(Fragment fragment, String fragmentTag, String backStackTag) {
    if (getSupportFragmentManager() != null)
      getSupportFragmentManager()
          .beginTransaction()
          .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
          .replace(R.id.content_layout, fragment, fragmentTag)
          .addToBackStack(backStackTag)
          .commit();
  }
}
