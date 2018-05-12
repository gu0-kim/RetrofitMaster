package com.gu.devel.retrofitmaster.mvp.view;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.adapter.UploadPageAdapter;
import com.gu.devel.retrofitmaster.mvp.adapter.data.ProgressItemData;
import com.gu.devel.retrofitmaster.mvp.message.MessageType;
import com.gu.devel.retrofitmaster.mvp.presenter.UploadImagePresenter;
import com.gu.devel.retrofitmaster.mvp.toolbar.provider.AnimActionProvider;
import com.gu.mvp.glide.GlideRequests;
import com.gu.mvp.view.adapter.IBaseAdapter;

/** Created by devel on 2018/5/1. */
public class UploadView extends BaseImageListView<ProgressItemData, UploadImagePresenter> {
  private static final String TITLE = "选择本地图片上传";

  @SuppressWarnings("unchecked")
  public static UploadView newInstance() {
    return new UploadView();
  }

  @Override
  public void inject() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  @Override
  public int getLayoutId() {
    return R.layout.image_list_view;
  }

  @Override
  public int getMenuLayoutId() {
    return R.menu.upload_toolbar_menu;
  }

  @Override
  public int getReceiveMessageType() {
    return MessageType.TYPE_UPLOAD;
  }

  @Override
  public String getViewTitle() {
    return TITLE;
  }

  @Override
  public int getLineNum() {
    return 2;
  }

  @Override
  public void loadLocal() {
    presenter.loadLocal();
  }

  @Override
  public IBaseAdapter initAdapter(Context context, GlideRequests glideRequests) {
    return new UploadPageAdapter(context, glideRequests);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    MenuItem menuItem = menu.findItem(R.id.upload_menu_item);
    mActionProvider = (AnimActionProvider) MenuItemCompat.getActionProvider(menuItem);
    mActionProvider.setOnClickListener(
        new AnimActionProvider.OnClickListener() {
          @Override
          public void onClick() {
            startUpload();
          }
        });
  }

  @Override
  public boolean onMenuItemClicked(MenuItem item) {
    if (item.getItemId() == R.id.config_menu_item) {
      ConfigServerView view = ConfigServerView.newInstance();
      view.show(getFragmentManager(), "");
    }
    return true;
  }

  /** 开始上传任务 */
  public void startUpload() {
    if (findCheckItem()) {
      showLoading();
      presenter.startUpload(getCheckedItem().getFilePath());
    }
  }

  public void finishUpload(boolean res) {
    finishDoServerWork(res);
    if (getActivity() != null)
      showToast(getActivity().getApplicationContext(), res ? "上传成功" : "上传失败");
  }

  @Override
  public boolean containsToolBar() {
    return true;
  }
}
