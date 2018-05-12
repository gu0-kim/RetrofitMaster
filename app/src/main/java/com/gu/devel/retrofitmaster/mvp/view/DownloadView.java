package com.gu.devel.retrofitmaster.mvp.view;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.adapter.DownloadPageAdapter;
import com.gu.devel.retrofitmaster.mvp.adapter.data.DownloadItemData;
import com.gu.devel.retrofitmaster.mvp.message.MessageType;
import com.gu.devel.retrofitmaster.mvp.presenter.DownloadPresenter;
import com.gu.devel.retrofitmaster.mvp.toolbar.provider.AnimActionProvider;
import com.gu.mvp.glide.GlideRequests;
import com.gu.mvp.view.adapter.IBaseAdapter;

/** Created by devel on 2018/5/1. */
public class DownloadView extends BaseImageListView<DownloadItemData, DownloadPresenter> {
  private static final String TITLE = "选择图片下载";

  @SuppressWarnings("unchecked")
  public static DownloadView newInstance() {
    return new DownloadView();
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
  public int getReceiveMessageType() {
    return MessageType.TYPE_DOWNLOAD;
  }

  @Override
  public String getViewTitle() {
    return TITLE;
  }

  @Override
  public int getMenuLayoutId() {
    return R.menu.download_toolbar_menu;
  }

  @Override
  public boolean onMenuItemClicked(MenuItem item) {
    if (item.getItemId() == R.id.config_menu_item) {
      ConfigServerView view = ConfigServerView.newInstance();
      view.show(getFragmentManager(), "");
    }
    return true;
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
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    MenuItem menuItem = menu.findItem(R.id.download_menu_item);
    mActionProvider = (AnimActionProvider) MenuItemCompat.getActionProvider(menuItem);
    mActionProvider.setOnClickListener(
        new AnimActionProvider.OnClickListener() {
          @Override
          public void onClick() {
            startDownload();
          }
        });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.config_menu_item) {
      ConfigServerView view = ConfigServerView.newInstance();
      view.show(getFragmentManager(), "");
    }
    return true;
  }

  @Override
  public IBaseAdapter initAdapter(Context context, GlideRequests glideRequests) {
    return new DownloadPageAdapter(context, glideRequests);
  }

  /** 开始下载任务 */
  public void startDownload() {
    if (findCheckItem()) {
      showLoading();
      String url = getCheckedItem().getUrl();
      String fileName = url.substring(url.lastIndexOf("/"));
      presenter.startDownload(url, fileName);
    }
  }

  /**
   * 结束下载
   *
   * @param res 是否成功
   * @param filePath 保存文件的路径
   */
  public void finishDownload(boolean res, String filePath) {
    if (res) {
      getCheckedItem().setFilePath(filePath);
    }
    finishDoServerWork(res);
    if (getActivity() != null)
      showToast(getActivity().getApplicationContext(), res ? "下载成功" : "下载失败");
  }

  @Override
  public boolean containsToolBar() {
    return true;
  }
}
