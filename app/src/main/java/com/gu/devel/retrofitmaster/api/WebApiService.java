package com.gu.devel.retrofitmaster.api;

import com.gu.devel.retrofitmaster.api.json.ImageInfoData;
import com.gu.devel.retrofitmaster.mvp.Result;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;

/** Created by devel on 2018/4/8. */
public interface WebApiService {
  @GET("server/WebApiServlet")
  Observable<ResponseBody> getDataByGet();

  @FormUrlEncoded
  @POST("server/WebApiServlet")
  Observable<ResponseBody> getDataByPost(@Field("name") String name, @Field("code") String code);

  @Multipart
  @POST("server/UploadServlet")
  Observable<Result> upload(@Part List<MultipartBody.Part> partList);

  @FormUrlEncoded
  @Streaming
  @POST("server/DownloadServlet")
  Observable<Response<ResponseBody>> downloadFile(@Field("fileName") String name);

  @GET("server/FileListServlet")
  Observable<List<ImageInfoData>> getServerImageInfoList();
}
