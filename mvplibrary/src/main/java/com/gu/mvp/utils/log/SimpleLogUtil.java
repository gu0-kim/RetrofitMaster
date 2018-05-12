package com.gu.mvp.utils.log;

import android.util.Log;

/** Created by devel on 2018/5/10. */
public class SimpleLogUtil {
  public static void log(Object o, String logStr) {
    String tag = o.getClass().getSimpleName();
    Log.e(tag, "-----" + tag + " :" + logStr + "-----");
  }
}
