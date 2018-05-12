package com.gu.devel.retrofitmaster.app.config;

import android.content.Context;
import android.content.SharedPreferences;

/** Created by devel on 2018/5/7. */
public class SharePreferenceController {
  private static final String KEY_URL = "url";
  private static final String NAME = "config";

  public String getUrl(Context context) {
    SharedPreferences preferences = getPreferencesByName(context, NAME);
    return preferences.getString(KEY_URL, null);
  }

  public boolean saveUrl(Context context, String url) {
    SharedPreferences preferences = getPreferencesByName(context, NAME);
    return preferences.edit().putString(KEY_URL, url).commit();
  }

  private SharedPreferences getPreferencesByName(Context context, String name) {
    return context.getSharedPreferences(name, Context.MODE_PRIVATE);
  }
}
