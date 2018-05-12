package com.gu.devel.retrofitmaster.mvp.view;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gu.devel.retrofitmaster.MainActivity;
import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.presenter.JsonViewPresenter;
import com.gu.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/** Created by devel on 2018/4/8. */
public class JsonView extends IView<JsonViewPresenter> {

  Unbinder mUnBinder;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.jsonRes)
  TextView resTv;

  @BindView(R.id.httpRes)
  TextView httpRes;

  @BindView(R.id.nameEd)
  EditText nameEd;

  @BindView(R.id.codeEd)
  EditText codeEd;

  @BindView(R.id.getBtn)
  Button getBtn;

  @BindView(R.id.clearBtn)
  Button clearBtn;

  @BindView(R.id.postBtn)
  Button postBtn;

  @SuppressWarnings("unchecked")
  public static JsonView newInstance() {
    return new JsonView();
  }

  @Override
  public void inject() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  @Override
  public int getLayoutId() {
    return R.layout.fragment_layout;
  }

  @Override
  public void initView(View parent) {
    mUnBinder = ButterKnife.bind(this, parent);
    ((MainActivity) mActivity).setSupportActionBar(toolbar);
    toolbar.setTitle("Json");
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (getFragmentManager() != null) getFragmentManager().popBackStack();
          }
        });
    initBtnClick();
    initEditText();
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
  public boolean containsToolBar() {
    return true;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  private void initBtnClick() {
    presenter.setSingleClick(
        getBtn,
        new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            clearResultTextView();
            presenter.requestGet();
          }
        });
    presenter.setSingleClick(
        postBtn,
        new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            clearResultTextView();
            presenter.requestPost(getEditText(nameEd), getEditText(codeEd));
          }
        });
    presenter.setSingleClick(
        clearBtn,
        new Consumer<Object>() {
          @Override
          public void accept(Object o) throws Exception {
            resetRes();
          }
        });
  }

  /** 监听editText变化，两个输入都合法的话，使能postBtn */
  public void initEditText() {
    presenter.listenTextValid(nameEd, codeEd);
  }

  @Override
  public void destroyView() {
    mUnBinder.unbind();
    mUnBinder = null;
  }

  public boolean validInput(EditText ed) {
    return !TextUtils.isEmpty(ed.getText());
  }

  public void setPostBtnValid(boolean valid) {
    postBtn.setEnabled(valid);
  }

  private String getEditText(EditText ed) {
    return ed.getText().toString();
  }

  public void showParseResult(String res) {
    resTv.setText(res);
  }

  public void showHttpResult(String res) {
    httpRes.setText(res);
  }

  public void clearResultTextView() {
    httpRes.setText(null);
    resTv.setText(null);
  }

  public void clearEditText() {
    nameEd.setText(null);
    codeEd.setText(null);
    nameEd.clearFocus();
    codeEd.clearFocus();
  }

  public void resetRes() {
    clearResultTextView();
    clearEditText();
    if (getActivity() != null) showToast(getActivity().getApplicationContext(), "已重置");
  }
}
