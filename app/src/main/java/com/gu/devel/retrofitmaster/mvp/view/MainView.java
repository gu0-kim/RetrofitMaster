package com.gu.devel.retrofitmaster.mvp.view;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gu.devel.retrofitmaster.MainActivity;
import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.mvp.presenter.MainPresenter;
import com.gu.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/** Created by devel on 2018/4/23. */
public class MainView extends IView<MainPresenter> {

  private Unbinder mUnBinder;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @SuppressWarnings("unchecked")
  public static MainView newInstance() {
    return new MainView();
  }

  @Override
  public boolean containsToolBar() {
    return true;
  }

  @Override
  public void inject() {}

  @Override
  public int getLayoutId() {
    return R.layout.main_view;
  }

  @Override
  public void initView(View parent) {
    mUnBinder = ButterKnife.bind(this, parent);
    ((MainActivity) mActivity).setSupportActionBar(toolbar);
    toolbar.setTitle("Retrofit测试");
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mActivity.finish();
          }
        });
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.main_toolbar_menu_, menu);
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
  public void destroyView() {
    toolbar.setNavigationOnClickListener(null);
    mUnBinder.unbind();
    mUnBinder = null;
  }

  @OnClick(R.id.jsonBtn)
  public void jsonBtnClick() {
    JsonView fragment = JsonView.newInstance();
    ((MainActivity) mActivity)
        .startFragment(fragment, ViewType.JSON_VIEW.tagName(), "startJsonView");
  }

  @OnClick(R.id.uploadBtn)
  public void uploadBtnClick() {
    UploadView fragment = UploadView.newInstance();
    ((MainActivity) mActivity)
        .startFragment(fragment, ViewType.UPLOAD_IMAGES_VIEW.tagName(), "startUploadImagesView");
  }

  @OnClick(R.id.downloadBtn)
  public void downloadBtnClick() {
    DownloadView fragment = DownloadView.newInstance();
    ((MainActivity) mActivity)
        .startFragment(
            fragment, ViewType.DOWNLOAD_IMAGES_VIEW.tagName(), "startDownloadImagesView");
  }
}
