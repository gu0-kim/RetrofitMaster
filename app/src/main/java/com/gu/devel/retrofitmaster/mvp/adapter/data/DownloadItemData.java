package com.gu.devel.retrofitmaster.mvp.adapter.data;

import android.text.TextUtils;

/** Created by devel on 2018/4/30. */
public class DownloadItemData extends ProgressItemData {
  private String url; // http://xxx/images/1.jpg
  private String detail; // 1200x1920

  public DownloadItemData(String url, String fileName, String detail, String size) {
    this.url = url;
    this.detail = detail;
    this.size = size;
    this.fileName = fileName;
  }

  /**
   * after scan cache file ,loadLocal itemData
   *
   * @param filePath The filepath
   */
  public void loadFilePath(String filePath) {
    if (!TextUtils.isEmpty(filePath)) {
      setFilePath(filePath);
      setDone(true);
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
