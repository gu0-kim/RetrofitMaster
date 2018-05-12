package com.gu.devel.retrofitmaster.app.config;

import android.content.Context;

import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.mvp.utils.log.SimpleLogUtil;

import javax.inject.Inject;

/** Created by devel on 2018/5/7. */
public class UrlLoader {
  private static final String NO_SETTING = "";
  private static final String IMG_REL_PATH = "/server/imgs/download";
  private static final String UPLOAD_REL_URL = "/server/UploadServlet";
  private String mServerUrl = NO_SETTING; // http://192.168.1.113:8080
  @Inject PropertyLoader mPropertyLoader;
  @Inject SharePreferenceController mSharePreferenceController;

  public UrlLoader() {
    SimpleLogUtil.log(this, "Generate a UrlLoader!");
    ComponentController.getInstance().getComponent().inject(this);
  }

  public boolean saveUrl(Context context, String url) {
    if (url == null) return false;
    mServerUrl = url;
    return mSharePreferenceController.saveUrl(context, url);
  }

  public String getUrl(Context context) throws Exception {
    return needLoad() ? loadUrl(context) : mServerUrl;
  }

  public String getImageRelativeUrl(Context context) throws Exception {
    return getUrl(context) + IMG_REL_PATH;
  }

  public String getUploadUrl(Context context) throws Exception {
    return getUrl(context) + UPLOAD_REL_URL;
  }

  private String loadUrl(Context context) throws Exception {
    final String preferenceUrl = mSharePreferenceController.getUrl(context);
    if (preferenceUrl == null) {
      String url = mPropertyLoader.getServerBaseURL(context);
      if (url == null) {
        throw new Exception("读取服务器配置文件异常");
      }
      mSharePreferenceController.saveUrl(context, url);
      mServerUrl = url;
    } else {
      mServerUrl = preferenceUrl;
    }
    return mServerUrl;
  }

  private boolean needLoad() {
    return mServerUrl == null || mServerUrl.equals(NO_SETTING);
  }
}
