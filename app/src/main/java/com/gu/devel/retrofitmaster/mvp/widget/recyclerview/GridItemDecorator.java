package com.gu.devel.retrofitmaster.mvp.widget.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/** Created by devel on 2018/4/27. */
public class GridItemDecorator extends RecyclerView.ItemDecoration {
  private static final int MARGIN = 10;

  public GridItemDecorator() {
    super();
  }

  @Override
  public void getItemOffsets(
      Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    outRect.top = MARGIN;
    outRect.bottom = MARGIN;
    outRect.left = MARGIN;
    outRect.right = MARGIN;
  }
}
