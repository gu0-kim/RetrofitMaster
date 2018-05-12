package com.gu.mvp.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/** body带下载进度监听 Created by devel on 2018/5/1. */
public class ProgressResponseBody extends ResponseBody {
  public static final String TAG = ProgressResponseBody.class.getSimpleName();
  private ResponseBody responseBody;
  private ProgressCallback callback;
  private BufferedSource bufferedSource;

  public ProgressResponseBody(ResponseBody body, ProgressCallback callback) {
    responseBody = body;
    this.callback = callback;
  }

  @Override
  public MediaType contentType() {
    return responseBody.contentType();
  }

  @Override
  public long contentLength() {
    return responseBody.contentLength();
  }

  @Override
  public BufferedSource source() {
    if (bufferedSource == null) {
      bufferedSource = Okio.buffer(mySource(responseBody.source()));
    }
    return bufferedSource;
  }

  private Source mySource(Source source) {
    return new ForwardingSource(source) {
      long totalBytesRead = 0L;
      long contentLength = 0L;
      int last, progress;

      @Override
      public long read(Buffer sink, long byteCount) throws IOException {
        if (contentLength == 0L) {
          contentLength = contentLength();
        }
        if (contentLength == 0L) return 0;
        long bytesRead = super.read(sink, byteCount);

        totalBytesRead += bytesRead != -1 ? bytesRead : 0;
        float p = 100.0f * totalBytesRead / contentLength;
        progress = (int) (100.0d * totalBytesRead / contentLength);
        if (progress != last) {
          callback.onProgressChange(progress);
          last = progress;
        }
        if (bytesRead == -1) {
          callback = null;
        }
        return bytesRead;
      }
    };
  }
}
