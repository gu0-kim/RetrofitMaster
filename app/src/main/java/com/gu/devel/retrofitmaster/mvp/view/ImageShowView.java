package com.gu.devel.retrofitmaster.mvp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.gu.devel.retrofitmaster.R;
import com.gu.mvp.glide.GlideApp;
import com.gu.mvp.utils.activity.StatusBarCompat;
import com.gu.mvp.utils.scroll.ScrollUtils;
import com.gu.mvp.view.IView;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/** Created by devel on 2018/5/4. */
public class ImageShowView extends IView {

  private Unbinder mUnBinder;
  private static final String ARG_NAME = "filePath";

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.iv_photo)
  PhotoView iv_photo;

  private Disposable navClick;

  public static ImageShowView newInstance(String pathArg) {
    ImageShowView fragment = new ImageShowView();
    Bundle data = new Bundle();
    data.putString(ARG_NAME, pathArg);
    fragment.setArguments(data);
    return fragment;
  }

  @Override
  public void inject() {
    // do nothing!
  }

  @Override
  public int getLayoutId() {
    return R.layout.photoview_layout;
  }

  @Override
  public void initView(View parent) {
    mUnBinder = ButterKnife.bind(this, parent);
    String filePath = getStringByKey(ARG_NAME);
    GlideApp.with(this).load(filePath).fitCenter().into(iv_photo);
    setToolbarColor(1f, Color.BLACK);
    StatusBarCompat.compat(mActivity, Color.BLACK);
    navClick =
        RxToolbar.navigationClicks(toolbar)
            .subscribe(
                new Consumer<Object>() {
                  @Override
                  public void accept(Object o) throws Exception {
                    if (getFragmentManager() != null) getFragmentManager().popBackStack();
                  }
                });
  }

  @Override
  public void destroyView() {
    navClick.dispose();
    navClick = null;
    mUnBinder.unbind();
    mUnBinder = null;
    if (getContext() != null)
      StatusBarCompat.compat(
          mActivity, ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
  }

  @Override
  public boolean containsToolBar() {
    return true;
  }

  public void setToolbarColor(float alpha, int rgb) {
    toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, rgb));
  }
}
