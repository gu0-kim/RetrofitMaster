package com.gu.mvp.utils.dimen;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DimenUtils {
  public enum Size {
    WIDTH,
    HEIGHT
  }

  public static int getScreenSize(Context context, Size size) {
    DisplayMetrics dm = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    if (windowManager == null) {
      try {
        throw new Exception("windowManager == null");
      } catch (Exception e) {
        e.printStackTrace();
      }
      return 0;
    }
    windowManager.getDefaultDisplay().getMetrics(dm);
    return size == Size.WIDTH ? dm.widthPixels : dm.heightPixels;
  }

  /**
   * get status bar height
   *
   * @param context
   * @return height
   */
  public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }
}
