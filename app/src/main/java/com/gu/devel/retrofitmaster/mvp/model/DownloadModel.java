package com.gu.devel.retrofitmaster.mvp.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gu.devel.retrofitmaster.api.WebApiService;
import com.gu.devel.retrofitmaster.api.json.ImageInfoData;
import com.gu.devel.retrofitmaster.app.config.UrlLoader;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.di.module.AppModule;
import com.gu.devel.retrofitmaster.mvp.adapter.data.DownloadItemData;
import com.gu.devel.retrofitmaster.mvp.message.DownloadMessage;
import com.gu.mvp.bus.RxBus;
import com.gu.mvp.okhttp.ProgressCallback;
import com.gu.mvp.okhttp.ProgressResponseBody;
import com.gu.mvp.utils.file.FileUtil;
import com.gu.mvp.utils.math.MathUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/** Created by devel on 2018/5/1. */
public class DownloadModel {

  @Inject UrlLoader mLoader;
  @Inject RxBus mRxBus;
  @Inject FileUtil mFileUtil;
  @Inject OkHttpClient mDefaultClient;
  private OkHttpClient mProgressClient;

  private static final String SAVED_DIR_IN_CACHE_PATH = "images/download"; // 在缓存目录中的下载文件相对保存路径

  public DownloadModel() {
    ComponentController.getInstance().getComponent().inject(this);
    mProgressClient = getDownloadProgressClient();
  }

  public Observable<List<ImageInfoData>> loadServerImageList(Context context) throws Exception {
    return getRetrofit(context).create(WebApiService.class).getServerImageInfoList();
  }

  public List<DownloadItemData> convertData(Context context, List<ImageInfoData> list)
      throws Exception {
    return convertServerList(context, list);
  }

  /**
   * 更新“已下载”状态
   *
   * @param context
   * @param list
   * @return
   */
  public List<DownloadItemData> updateDownloaded(Context context, List<DownloadItemData> list) {
    for (DownloadItemData data : list) {
      String path = mFileUtil.cacheFileExist(context, SAVED_DIR_IN_CACHE_PATH, data.getFileName());
      data.setDone(path != null);
      data.setFilePath(path);
    }
    Log.e("TAG", "updateDownloaded: size=" + list.size());
    return list;
  }

  /**
   * 下载文件附带进度更新
   *
   * @param context context
   * @param url 文件下载地址
   * @param saveName 保存的文件名
   * @return 保存的具体路径
   */
  public String downloadWithProgress(Context context, String url, String saveName)
      throws Exception {
    Request request = new Request.Builder().url(url).build(); // 创建Request 对象
    ResponseBody body = null;
    String filePath = null;
    try {
      Response response = mProgressClient.newCall(request).execute();
      if (response.isSuccessful()) {
        body = response.body();
        if (body != null) {
          File file =
              mFileUtil.save2CacheFile(
                  context, SAVED_DIR_IN_CACHE_PATH, saveName, body.byteStream());
          if (file != null) filePath = file.getPath();
        }
      }
      return filePath;
    } finally {
      if (body != null) body.close();
    }
  }

  private List<DownloadItemData> convertServerList(Context context, List<ImageInfoData> list)
      throws Exception {
    List<DownloadItemData> res = new ArrayList<>();
    String relativePath = mLoader.getImageRelativeUrl(context);
    for (ImageInfoData data : list) {
      String url = relativePath + "/" + data.getName();
      Log.e("TAG", "convertServerList: url=" + url);
      String size = MathUtil.b2Mb(data.getSize(), 1, "MB");
      DownloadItemData resItem = new DownloadItemData(url, data.getName(), data.getDetail(), size);
      res.add(resItem);
    }
    Log.e("TAG", "convertServerList: size=" + res.size());
    return res;
  }

  /** 获取带进度的OkHttpClient */
  private OkHttpClient getDownloadProgressClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(AppModule.TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        .readTimeout(AppModule.TIMEOUT_READ, TimeUnit.MILLISECONDS)
        .writeTimeout(AppModule.TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        .addNetworkInterceptor(
            new Interceptor() {
              @Override
              public Response intercept(@NonNull Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response
                    .newBuilder()
                    .body(
                        new ProgressResponseBody(
                            response.body(),
                            new ProgressCallback() {
                              @Override
                              public void onProgressChange(int progress) {
                                mRxBus.send(new DownloadMessage(progress));
                              }
                            }))
                    .build();
              }
            })
        .build();
  }

  private Retrofit getRetrofit(Context context) throws Exception {
    return new Retrofit.Builder()
        .client(mDefaultClient)
        .baseUrl(mLoader.getUrl(context))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}
