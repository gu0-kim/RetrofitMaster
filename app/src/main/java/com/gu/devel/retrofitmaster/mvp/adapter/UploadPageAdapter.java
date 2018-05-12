package com.gu.devel.retrofitmaster.mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.mvp.adapter.data.ProgressItemData;
import com.gu.mvp.glide.GlideRequests;

import butterknife.BindView;

/** Created by devel on 2018/4/24. GridAdapter */
public class UploadPageAdapter
    extends ProgressGridAdapter<ProgressItemData, UploadPageAdapter.ViewHolder> {

  public UploadPageAdapter(Context context, GlideRequests glideRequests) {
    super(context, glideRequests);
  }

  @Override
  public int getLayoutId() {
    return R.layout.griditem;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(createItemView(parent));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    getGlideRequests().load(getPositionData(position).getFilePath()).centerCrop().into(holder.img);
    holder.size.setText(getPositionData(position).getSize());
    holder.fileName.setText(getPositionData(position).getFileName());
  }

  class ViewHolder extends ProgressGridAdapter.ViewHolder {
    @BindView(R.id.fileName)
    TextView fileName;

    ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
