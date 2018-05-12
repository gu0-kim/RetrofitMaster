package com.gu.devel.retrofitmaster.mvp.toolbar.provider;

import android.content.Context;

/** Created by devel on 2018/5/9. */
public class UploadAnimActionProvider extends AnimActionProvider {
  @Override
  public String getTitle() {
    return "上传";
  }

  public UploadAnimActionProvider(Context context) {
    super(context);
  }
}
