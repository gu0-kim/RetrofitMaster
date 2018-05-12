package com.gu.devel.retrofitmaster.mvp.view;

/** Created by devel on 2018/4/27. */
public enum ViewType {
  MAIN_VIEW("MainView"),
  JSON_VIEW("JsonView"),
  UPLOAD_IMAGES_VIEW("UploadImagesView"),
  DOWNLOAD_IMAGES_VIEW("DownloadView");
  private String tagName;

  ViewType(String tagName) {
    this.tagName = tagName;
  }

  public String tagName() {
    return this.tagName;
  }
}
