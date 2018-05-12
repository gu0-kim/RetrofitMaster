package com.gu.mvp.okhttp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/** 上传进度监听 Created by devel on 2018/4/16. */
public class ProgressRequestBody extends RequestBody {
  private RequestBody requestBody;
  private BufferedSink bufferedSink;
  private ProgressCallback callback;

  public ProgressRequestBody(RequestBody requestBody, ProgressCallback callback) {
    this.requestBody = requestBody;
    this.callback = callback;
  }

  @Nullable
  @Override
  public MediaType contentType() {
    return requestBody.contentType();
  }

  @Override
  public long contentLength() throws IOException {
    return requestBody.contentLength();
  }

  @Override
  public void writeTo(@NonNull BufferedSink sink) throws IOException {
    if (bufferedSink == null) {
      bufferedSink = Okio.buffer(sink(sink));
    }
    // 写入
    requestBody.writeTo(bufferedSink);
    // 刷新
    bufferedSink.flush();
    bufferedSink.close();
  }

  private Sink sink(BufferedSink sink) {

    return new ForwardingSink(sink) {
      long bytesWritten = 0L;
      long contentLength = 0L;
      int last, progress;

      @Override
      public void write(@NonNull Buffer source, long byteCount) throws IOException {
        super.write(source, byteCount);
        if (contentLength == 0) {
          contentLength = contentLength();
        }
        if (contentLength == 0) return;
        bytesWritten += byteCount;
        progress = (int) (100.0d * bytesWritten / contentLength);
        if (last != progress) {
          last = progress;
          callback.onProgressChange(progress);
        }
        if (bytesWritten == contentLength) callback = null;
      }
    };
  }
}
