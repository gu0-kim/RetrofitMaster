package com.gu.mvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gu.mvp.R;

/** Created by developgergu on 2017/12/18. */
public class SquareFrameLayout extends FrameLayout {
  // 可配置size：按高度、按宽度
  private static final int DEPEND_WIDTH = 0;
  private static final int DEPEND_HEIGHT = 1;
  private int mDepend = DEPEND_WIDTH; // default
  private int squareSize;

  public SquareFrameLayout(Context context) {
    super(context);
    init(context, null);
  }

  public SquareFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public SquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs) {
    if (attrs != null) {
      TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Square);
      if (ta != null) {
        mDepend = ta.getInt(R.styleable.Square_dependSide, mDepend);
        ta.recycle();
      }
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (squareSize == 0) {
      squareSize =
          mDepend == DEPEND_WIDTH
              ? MeasureSpec.getSize(widthMeasureSpec)
              : MeasureSpec.getSize(heightMeasureSpec);
      ViewGroup.LayoutParams params = getLayoutParams();
      params.height = squareSize;
      params.width = squareSize;
      setLayoutParams(params);
      setMeasuredDimension(squareSize, squareSize);
      measureChildren(
          MeasureSpec.makeMeasureSpec(squareSize, MeasureSpec.EXACTLY),
          MeasureSpec.makeMeasureSpec(squareSize, MeasureSpec.EXACTLY));
      Log.e("TAG", "onMeasure:SquareFrameLayout--" + this + ",size--" + squareSize);
    }
  }
}
