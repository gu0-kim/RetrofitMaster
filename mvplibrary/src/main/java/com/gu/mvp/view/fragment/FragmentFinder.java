package com.gu.mvp.view.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/** Created by devel on 2018/4/27. */
public class FragmentFinder {


    /**
     *
     * @param activity
     * @param tClass classType that you need
     * @param layoutId
     * @param <T> return instance that is tClass
     * @return
     */
  @SuppressWarnings("unchecked")
  public static <T extends Fragment> T getCurrentFragmentFromLayoutId(
      AppCompatActivity activity, Class<T> tClass, @IdRes int layoutId) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(layoutId);
        if (fragment != null && fragment.getClass().equals(tClass)) {
            return (T) fragment;
        }
        return null;
    }
}
