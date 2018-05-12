package com.gu.devel.retrofitmaster.mvp.toolbar.provider;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gu.devel.retrofitmaster.R;

/** Created by devel on 2018/4/28. */
public abstract class AnimActionProvider extends ActionProvider {

  private TextView mAnimTv;
  private View parentView;
  private ProgressBar pb;

  private OnClickListener onClickListener;

  public AnimActionProvider(Context context) {
    super(context);
  }

  public abstract String getTitle();

  @Override
  public View onCreateActionView() {
    int size =
        getContext()
            .getResources()
            .getDimensionPixelSize(
                android.support.design.R.dimen.abc_action_bar_default_height_material);
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(size, size);
    parentView =
        LayoutInflater.from(getContext()).inflate(R.layout.menu_anim_provider, null, false);
    parentView.setLayoutParams(layoutParams);
    mAnimTv = parentView.findViewById(R.id.animTv);
    mAnimTv.setText(getTitle());
    pb = parentView.findViewById(R.id.pb);
    parentView.setOnClickListener(onViewClickListener);
    return parentView;
  }

  // 点击处理。
  private View.OnClickListener onViewClickListener =
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (onClickListener != null && getClickable()) onClickListener.onClick();
        }
      };

  // 外部设置监听。
  public void setOnClickListener(OnClickListener onClickListener) {
    this.onClickListener = onClickListener;
  }

  public interface OnClickListener {
    void onClick();
  }

  public void doAnim() {
    if (mAnimTv.getVisibility() == View.VISIBLE) {
      ObjectAnimator scaleY = ObjectAnimator.ofFloat(mAnimTv, "scaleY", 0.5f, 1.5f, 1f);
      ObjectAnimator scaleX = ObjectAnimator.ofFloat(mAnimTv, "scaleX", 0.5f, 1.5f, 1f);
      AnimatorSet set = new AnimatorSet();
      set.playTogether(scaleX, scaleY);
      set.setDuration(400);
      set.start();
    }
  }

  public void hide() {
    parentView.setVisibility(View.GONE);
  }

  public void showPb() {
    parentView.setVisibility(View.VISIBLE);
    mAnimTv.setVisibility(View.GONE);
    pb.setVisibility(View.VISIBLE);
  }

  public void showTv() {
    parentView.setVisibility(View.VISIBLE);
    pb.setVisibility(View.GONE);
    mAnimTv.setVisibility(View.VISIBLE);
  }

  private boolean getClickable() {
    return parentView.getVisibility() == View.VISIBLE && mAnimTv.getVisibility() == View.VISIBLE;
  }

  public void setText(String text) {
    mAnimTv.setText(text);
  }

  public void clear() {
    mAnimTv = null;
    parentView = null;
    onClickListener = null;
  }
}
