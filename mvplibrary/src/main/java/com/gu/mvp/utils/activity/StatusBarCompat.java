package com.gu.mvp.utils.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/** Created by devel on 2018/5/9. */
public class StatusBarCompat {
  private static final String STATUS_BAR_TAG = "STATUS_BAR_TAG";
  private static final int INVALID_VAL = -1;
  private static final int COLOR_DEFAULT = Color.parseColor("#20000000");

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public static void compat(Activity activity, int statusColor) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      if (statusColor != INVALID_VAL) {
        activity.getWindow().setStatusBarColor(statusColor);
      }
      return;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      // 设置status bar透明
      //      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
      localLayoutParams.flags =
          (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
      int color = COLOR_DEFAULT;
      ViewGroup contentView = activity.findViewById(android.R.id.content);
      if (statusColor != INVALID_VAL) {
        color = statusColor;
      }
      View statusBarView = contentView.findViewWithTag(STATUS_BAR_TAG);
      if (statusBarView == null) {
        statusBarView = new View(activity);
        statusBarView.setTag(STATUS_BAR_TAG);
        Log.e("tag", "compat: statusColor=" + statusColor);
        ViewGroup.LayoutParams lp =
            new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        contentView.addView(statusBarView, lp);
      }
      statusBarView.setBackgroundColor(color);
    }
  }

  public static void compat(Activity activity) {
    compat(activity, INVALID_VAL);
  }

  public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }
}
