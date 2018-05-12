package com.gu.devel.retrofitmaster.mvp.view;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.gu.devel.retrofitmaster.MainActivity;
import com.gu.devel.retrofitmaster.R;
import com.gu.devel.retrofitmaster.mvp.adapter.data.ProgressItemData;
import com.gu.devel.retrofitmaster.mvp.toolbar.provider.AnimActionProvider;
import com.gu.devel.retrofitmaster.mvp.widget.recyclerview.GridItemDecorator;
import com.gu.mvp.bus.Message;
import com.gu.mvp.bus.RxBus;
import com.gu.mvp.glide.GlideApp;
import com.gu.mvp.glide.GlideRequests;
import com.gu.mvp.presenter.IPresenter;
import com.gu.mvp.view.IView;
import com.gu.mvp.view.adapter.IBaseAdapter;
import com.gu.mvp.widget.ControlTouchableFrameLayout;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/** Created by devel on 2018/4/23. */
public abstract class BaseImageListView<D extends ProgressItemData, T extends IPresenter>
    extends IView<T> implements IBaseAdapter.ItemClickListener, Consumer<Message> {
  private Unbinder mUnBinder;
  protected IBaseAdapter<D, ?> mAdapter;
  private D mCheckItem;
  private int mMessageType;
  private int mCheckPos;
  protected AnimActionProvider mActionProvider;

  @Inject RxBus mRxBus;

  @BindView(R.id.rv)
  RecyclerView rv;

  @BindView(R.id.loading)
  FrameLayout loading;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.control_touch_view)
  ControlTouchableFrameLayout control_touch_view;

  public abstract int getReceiveMessageType();

  public abstract String getViewTitle();

  public abstract @MenuRes int getMenuLayoutId();

  public abstract boolean onMenuItemClicked(MenuItem menuItem);

  public abstract int getLineNum();

  public abstract void loadLocal();

  public abstract IBaseAdapter initAdapter(Context context, GlideRequests glideRequests);

  public void busRegister() {
    mRxBus.register(this);
  }

  @Override
  public void initView(View parent) {
    mUnBinder = ButterKnife.bind(this, parent);
    busRegister();
    mMessageType = getReceiveMessageType();
    ((MainActivity) mActivity).setSupportActionBar(toolbar);
    toolbar.setTitle(getViewTitle());
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getFragmentManager().popBackStack();
          }
        });
    visibleLoading(true);
    //    ((MainActivity) mActivity).visibleToolbarUploadBtn(false);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), getLineNum());
    //    gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
    rv.setLayoutManager(gridLayoutManager);
    rv.addItemDecoration(new GridItemDecorator());
    GlideRequests glideRequests = GlideApp.with(this);
    mAdapter = initAdapter(getContext(), glideRequests);
    mAdapter.setItemClickListener(this);
    rv.setAdapter(mAdapter);
    loadLocal();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(getMenuLayoutId(), menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return onMenuItemClicked(item);
  }

  /** loading页面可见性 */
  public void visibleLoading(Boolean visible) {
    loading.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @Override
  public void destroyView() {
    mAdapter.clearAll();
    mAdapter = null;
    mUnBinder.unbind();
    mUnBinder = null;
    mRxBus.unRegister(this);
    mRxBus = null;
    mActionProvider.clear();
    mActionProvider = null;
  }

  /** 更改选中状态 */
  public boolean updateCheckState(int pos) {
    List<D> list = mAdapter.getList();
    boolean res = false;
    for (int i = 0; i < list.size(); i++) {
      ProgressItemData data = list.get(i);
      if (pos == i) {
        res = !data.isChecked();
        data.setChecked(res);
      } else {
        data.setChecked(false);
      }
    }
    mAdapter.notifyDataSetChanged();
    return res;
  }

  /**
   * 存在选中的图片
   *
   * @return 是否有选中的图片
   */
  public boolean findCheckItem() {
    mCheckItem = null;
    mCheckPos = -1;
    List<D> list = mAdapter.getList();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).isChecked()) {
        mCheckItem = list.get(i);
        mCheckPos = i;
        return true;
      }
    }
    return false;
  }

  private boolean isCheckState() {
    List<D> list = mAdapter.getList();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).isChecked()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onItemClick(int pos) {
    if (mAdapter.getPositionData(pos).isDone()) {
      if (getFragmentManager() != null) {
        getFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
            .add(
                R.id.content_layout,
                ImageShowView.newInstance(mAdapter.getPositionData(pos).getFilePath()))
            .addToBackStack("")
            .commit();
      }
      return;
    }
    boolean isChecked = updateCheckState(pos);
    if (isChecked) {
      showBtn();
      //      ((MainActivity) mActivity).changeToolbarBtnText(getBtnText());
      doUploadBtnAnim();
    } else {
      hideAction();
    }
  }

  @Override
  public void onItemLongClick(int pos) {
    // do nothing
  }

  /** 接受RxBus消息，更新进度 */
  @Override
  public void accept(Message message) throws Exception {
    int progress = (Integer) message.getData();
    if (message.typeCode() == mMessageType) {
      onProgressChange(progress);
    }
  }

  /** 更改进度 */
  private void onProgressChange(int progress) {
    mCheckItem.setDoing(true);
    mCheckItem.setProgress(progress);
    mCheckItem.setPbText(String.format(Locale.getDefault(), "%d%%", progress));
    mAdapter.notifyItemChanged(mCheckPos);
  }

  //    pbtv.setText(String.format(Locale.getDefault(), "%d%%", progress));

  /** 本地扫描结束 */
  public void finishLoadLocal(boolean res, List<D> list) {
    visibleLoading(false);
    if (res) {
      mAdapter.add(list);
      mAdapter.notifyDataSetChanged();
      list.clear();
    } else {
      if (getActivity() != null) showToast(getActivity().getApplicationContext(), "访问服务器失败");
    }
  }
  /** 与服务器传输结束 */
  public void finishDoServerWork(boolean res) {
    mCheckItem.setDoing(false);
    mCheckItem.setDone(res);
    mCheckItem.setChecked(false);
    mAdapter.notifyItemChanged(mCheckPos);
    mCheckItem = null;
    mCheckPos = -1;
    hideAction();
  }

  public D getCheckedItem() {
    return mCheckItem;
  }

  /** 显示action按钮 */
  public void hideAction() {
    mActionProvider.hide();
    setClickable(true);
  }

  /** action切换为文字 */
  public void showBtn() {
    mActionProvider.showTv();
    setClickable(true);
  }

  /** action切换为pb */
  public void showLoading() {
    mActionProvider.showPb();
    setClickable(false);
  }
  /** 执行“上传”按钮动画 */
  public void doUploadBtnAnim() {
    mActionProvider.doAnim();
  }

  private void setClickable(boolean clickable) {
    control_touch_view.setViewClickable(clickable);
  }
}
