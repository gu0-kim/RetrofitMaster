package com.gu.mvp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/** Created by devel on 2018/5/6. */
public class ControlTouchableLinearLayout extends LinearLayout {
  private boolean mIntercept;

  public ControlTouchableLinearLayout(@NonNull Context context) {
    super(context);
  }

  public ControlTouchableLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ControlTouchableLinearLayout(
      @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return mIntercept || super.onInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return true;
  }

  public void setViewClickable(boolean clickable) {
    mIntercept = !clickable;
  }
}
