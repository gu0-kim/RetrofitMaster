package com.gu.devel.retrofitmaster.mvp.model;

import android.content.Context;
import android.util.Log;

import com.gu.devel.retrofitmaster.api.WebApiService;
import com.gu.devel.retrofitmaster.app.config.UrlLoader;
import com.gu.devel.retrofitmaster.di.component.ComponentController;
import com.gu.devel.retrofitmaster.mvp.Result;
import com.gu.devel.retrofitmaster.mvp.adapter.data.ProgressItemData;
import com.gu.devel.retrofitmaster.mvp.message.UploadProgressMessage;
import com.gu.mvp.bus.RxBus;
import com.gu.mvp.okhttp.ProgressCallback;
import com.gu.mvp.okhttp.ProgressRequestBody;
import com.gu.mvp.utils.file.FileUtil;
import com.gu.mvp.utils.math.MathUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/** Created by devel on 2018/4/23. */
public class UploadViewModel {
  private static final String TAG = UploadViewModel.class.getSimpleName();
  @Inject FileUtil mFileUtil;
  @Inject RxBus mRxBus;
  @Inject OkHttpClient mClient;
  @Inject UrlLoader mLoader;
  private static final String ASSET_PATH = "uploadfiles/";
  private static final String UPLOAD_CACHE_PATH = "images/upload";

  public UploadViewModel() {
    ComponentController.getInstance().getComponent().inject(this);
  }

  public List<ProgressItemData> getAdapterDataList(List<String> paths) {
    List<ProgressItemData> data = new ArrayList<>();
    for (String path : paths) {
      String name = mFileUtil.getNameFromPath(path);
      String size = MathUtil.b2Mb(mFileUtil.getFileSize(path), 1, "MB");
      data.add(new ProgressItemData(path, name, size, false));
    }
    return data;
  }

  public String[] parseAssetsImage(Context context) {
    try {
      String ss[] = context.getResources().getAssets().list("uploadfiles");
      for (String s1 : ss) Log.e("tag", "getAssets().list--" + s1);
      return ss;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<String> ifNotExistCopy(Context context, String[] names) throws IOException {
    List<String> paths = new ArrayList<>();
    for (String name : names) {
      String existPath = mFileUtil.cacheFileExist(context, UPLOAD_CACHE_PATH, name);
      if (existPath != null) {
        paths.add(existPath);
        continue;
      }
      InputStream s = context.getResources().getAssets().open(ASSET_PATH + name);
      File file = mFileUtil.save2CacheFile(context, UPLOAD_CACHE_PATH, name, s);
      paths.add(file.getAbsolutePath());
      Log.e(TAG, "ifNotExistCopy: COPY ONE ,NAME IS " + name);
    }
    return paths;
  }

  public Result doUploadByOKHttp(Context context, File file) throws Exception {
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM); // 表单类型
    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    builder.addFormDataPart("imgfile", file.getName(), imageBody); // imgfile 后台接收图片流的参数名
    final MultipartBody body = builder.build();
    Request request =
        new Request.Builder()
            .url(mLoader.getUploadUrl(context))
            .post(
                new ProgressRequestBody(
                    body,
                    new ProgressCallback() {
                      @Override
                      public void onProgressChange(int progress) {
                        mRxBus.send(new UploadProgressMessage(progress));
                      }
                    }))
            .build();
    okhttp3.Response response = mClient.newCall(request).execute();
    if (response.isSuccessful()) {
      return new Result(true, "上传成功");
    }
    return new Result(false, "上传失败");
  }

  public Observable<Result> doUploadByRetrofit(Retrofit retrofit, File file) {
    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM); // 表单类型
    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    builder.addFormDataPart("imgfile", file.getName(), imageBody); // imgfile 后台接收图片流的参数名
    List<MultipartBody.Part> parts = builder.build().parts();
    return retrofit.create(WebApiService.class).upload(parts);
  }
}
