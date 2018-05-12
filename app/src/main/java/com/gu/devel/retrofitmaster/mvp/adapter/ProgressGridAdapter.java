package com.gu.devel.retrofitmaster.mvp.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.mvp.adapter.data.ProgressItemData;
import com.gu.mvp.glide.GlideRequests;
import com.gu.mvp.view.adapter.IBaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Created by devel on 2018/4/24. GridAdapter */
public abstract class ProgressGridAdapter<
        D extends ProgressItemData, VH extends ProgressGridAdapter.ViewHolder>
    extends IBaseAdapter<D, VH> {
  private LayoutInflater mLayoutInflater;
  private GlideRequests glideRequests;

  public ProgressGridAdapter(Context context, GlideRequests glideRequests) {
    mLayoutInflater = LayoutInflater.from(context);
    this.glideRequests = glideRequests;
  }

  public GlideRequests getGlideRequests() {
    return glideRequests;
  }

  public abstract @LayoutRes int getLayoutId();

  protected View createItemView(ViewGroup parent) {
    return mLayoutInflater.inflate(getLayoutId(), parent, false);
  }

  @Override
  public void onBindViewHolder(@NonNull VH holder, int position) {
    D itemData = getPositionData(position);
    holder.itemView.setClickable(canClickable(itemData));
    holder.mask.setVisibility(itemData.isChecked() ? View.VISIBLE : View.GONE);
    holder.mask.setProgress(100 - itemData.getProgress());
    holder.pbtv.setVisibility(needShowPbText(itemData) ? View.VISIBLE : View.GONE);
    holder.pbtv.setText(itemData.getPbText());
    holder.cb.setVisibility(needShowPbText(itemData) ? View.GONE : View.VISIBLE);
    holder.cb.setChecked(itemData.isChecked());
    holder.done.setVisibility(itemData.isDone() ? View.VISIBLE : View.GONE);
  }

  /**
   * 需要显示数字进度：当正在上传或者已经上传成功--显示
   *
   * @param itemData
   * @return
   */
  private boolean needShowPbText(ProgressItemData itemData) {
    return itemData.isDoing() || itemData.isDone();
  }

  /**
   * item可点击判断：当没在上传中而且没有上传过 --可点击
   *
   * @param itemData
   * @return
   */
  private boolean canClickable(ProgressItemData itemData) {
    return !itemData.isDoing();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img)
    ImageView img;

    @BindView(R.id.cb)
    CheckBox cb;

    @BindView(R.id.mask)
    ProgressBar mask;

    @BindView(R.id.done)
    ImageView done;

    @BindView(R.id.pbtv)
    TextView pbtv;

    @BindView(R.id.size)
    TextView size;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      cb.setClickable(false);
      itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mItemClickListener.onItemClick(getAdapterPosition());
            }
          });
    }
  }
}
