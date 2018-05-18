package com.gu.mvp.utils.dimen;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
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

  /**
   * 判断当前sdk版本是否大于指定版本
   *
   * @param version 指定版本
   * @return 是否大于
   */
  public static boolean currentSdkUpVersion(final int version) {
    return Build.VERSION.SDK_INT >= version;
  }

  /**
   * 设置toolbar padding和height，设置该方法可去除android:fitsSystemWindows="true"
   *
   * @param context 上下文
   * @param toolbar 当前toolbar
   */
  public static void setToolbarFitsSystemWindows(Context context, Toolbar toolbar) {
    ViewGroup.LayoutParams params = toolbar.getLayoutParams();
    int paddingTop = DimenUtils.getStatusBarHeight(context);
    if (paddingTop == 0) {
      try {
        throw new Exception("can't get status bar height ! Error !");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    params.height = paddingTop + toolbar.getHeight();
    toolbar.setPadding(0, paddingTop, 0, 0);
    toolbar.setLayoutParams(params);
  }
}
