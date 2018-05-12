package com.gu.devel.retrofitmaster.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.gu.devel.retrofitmaster.app.config.UrlLoader;
import com.gu.devel.retrofitmaster.di.component.ComponentController;

import javax.inject.Inject;

/** Created by devel on 2018/5/7. */
public class ConfigServerModel {
  @Inject UrlLoader mLoader;

  public ConfigServerModel() {
    ComponentController.getInstance().getComponent().inject(this);
  }
  /**
   * 读取property文件
   *
   * @return url
   */
  public String getServerUrl(Context context) throws Exception {
    return mLoader.getUrl(context);
  }

  /**
   * 保存更改
   *
   * @param context 上下文
   * @param value 新url
   * @return 操作是否成功
   */
  public boolean saveServerUrl(Context context, String value) {
    return !TextUtils.isEmpty(value) && mLoader.saveUrl(context, value);
  }
}
