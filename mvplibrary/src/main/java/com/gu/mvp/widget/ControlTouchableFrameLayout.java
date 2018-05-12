package com.gu.mvp.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/** Created by devel on 2018/5/6. */
public class ControlTouchableFrameLayout extends FrameLayout {
  private boolean mIntercept;

  public ControlTouchableFrameLayout(@NonNull Context context) {
    super(context);
  }

  public ControlTouchableFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ControlTouchableFrameLayout(
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
