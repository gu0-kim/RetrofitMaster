package com.gu.mvp.utils.leaks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

/** Created by developergu on 2018/1/30. */
public class CleanLeakUtils {
  private static final String TAG = CleanLeakUtils.class.getSimpleName();

  public static void fixInputMethodManagerLeak(Context destContext) {
    if (destContext == null) {
      return;
    }

    InputMethodManager inputMethodManager =
        (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (inputMethodManager == null) {
      return;
    }

    String[] viewArray = new String[] {"mCurRootView", "mServedView", "mNextServedView"};
    Field filed;
    Object filedObject;

    for (String view : viewArray) {
      try {
        filed = inputMethodManager.getClass().getDeclaredField(view);
        if (!filed.isAccessible()) {
          filed.setAccessible(true);
        }
        filedObject = filed.get(inputMethodManager);
        if (filedObject != null && filedObject instanceof View) {
          View fileView = (View) filedObject;
          if (fileView.getContext() == destContext) {
            filed.set(inputMethodManager, null);
            Log.e("CleanLeakUtils", "CleanLeakUtils----fixInputMethodManagerLeak: find set null");
          } else {
            break;
          }
        }
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }
  }

  public static void fixEditTextLeak(EditText editText) {
    try {
      Field field = editText.getClass().getDeclaredField("mContext");
      if (!field.isAccessible()) {
        field.setAccessible(true);
      }
      field.set(editText, null);
      Log.e(TAG, "fixEditTextLeak: success release mContext!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
