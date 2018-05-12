package com.gu.devel.retrofitmaster.mvp.view;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.presenter.ConfigPresenter;
import com.gu.mvp.view.IDialogView;
import com.gu.mvp.widget.ControlTouchableFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/** Created by devel on 2018/5/7. */
public class ConfigServerView extends IDialogView<ConfigPresenter> {
  Unbinder mUnBinder;

  @BindView(R.id.loading_pb)
  ProgressBar loading_pb;

  @BindView(R.id.serverEd)
  EditText serverEd;

  @BindView(R.id.saveConfig)
  Button saveConfigBtn;

  @BindView(R.id.config_toolbar)
  Toolbar mToolbar;

  @BindView(R.id.touch_control_view)
  ControlTouchableFrameLayout touch_control_view;

  public static ConfigServerView newInstance() {
    return new ConfigServerView();
  }

  @Override
  public boolean containsToolBar() {
    return true;
  }

  @Override
  public void inject() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  @Override
  public int getLayoutId() {
    return R.layout.config_layout;
  }

  @Override
  public void initView(View parent) {
    mUnBinder = ButterKnife.bind(this, parent);
    mToolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            dismiss();
          }
        });
    load();
  }

  @Override
  public void destroyView() {
    mToolbar.setNavigationOnClickListener(null);
    mUnBinder.unbind();
    mUnBinder = null;
  }

  @OnClick(R.id.saveConfig)
  public void onSaveConfigBtnClick() {
    closeKeyboard();
    presenter.save();
  }

  public void load() {
    presenter.load();
  }

  public void showLoading() {
    touch_control_view.setViewClickable(false);
    saveConfigBtn.setEnabled(false);
    loading_pb.setVisibility(View.VISIBLE);
  }

  public void invisibleLoading() {
    touch_control_view.setViewClickable(true);
    saveConfigBtn.setEnabled(true);
    loading_pb.setVisibility(View.INVISIBLE);
  }

  public void setEditText(String text) {
    serverEd.setText(text);
  }

  public String getEditText() {
    return serverEd.getText().toString();
  }

  public void closeKeyboard() {
    if (serverEd != null) {
      InputMethodManager inputMethodManager =
          (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
      if (inputMethodManager != null)
        inputMethodManager.hideSoftInputFromWindow(serverEd.getWindowToken(), 0);
    }
  }
}
