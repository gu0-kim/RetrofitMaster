package com.gu.devel.retrofitmaster.mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.mvp.adapter.data.DownloadItemData;
import com.gu.mvp.glide.GlideRequests;

import butterknife.BindView;

/** Created by devel on 2018/4/30. */
public class DownloadPageAdapter
    extends ProgressGridAdapter<DownloadItemData, DownloadPageAdapter.ViewHolder> {

  public DownloadPageAdapter(Context context, GlideRequests requests) {
    super(context, requests);
  }

  @Override
  public int getLayoutId() {
    return R.layout.download_list_holder_item;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(createItemView(parent));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    DownloadItemData itemData = getPositionData(position);
    getGlideRequests().load(itemData.getUrl()).centerCrop().into(holder.img);
    holder.size.setText(itemData.getSize());
    holder.detail.setText(itemData.getDetail());
    holder.name.setText(itemData.getFileName());
  }

  class ViewHolder extends ProgressGridAdapter.ViewHolder {

    @BindView(R.id.detail)
    TextView detail;

    @BindView(R.id.fileName)
    TextView name;

    ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
