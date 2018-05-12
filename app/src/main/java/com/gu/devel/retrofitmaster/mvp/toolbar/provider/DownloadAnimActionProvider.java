package com.gu.devel.retrofitmaster.mvp.toolbar.provider;

import android.content.Context;

/** Created by devel on 2018/5/9. */
public class DownloadAnimActionProvider extends AnimActionProvider {
  @Override
  public String getTitle() {
    return "下载";
  }

  public DownloadAnimActionProvider(Context context) {
    super(context);
  }
}
