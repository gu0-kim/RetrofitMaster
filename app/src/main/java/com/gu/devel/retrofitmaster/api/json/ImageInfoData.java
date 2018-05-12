package com.gu.devel.retrofitmaster.api.json;

import java.io.Serializable;

public class ImageInfoData implements Serializable {
  private String name; // 1.jpg
  private long size; // b
  private String detail; // 1920x1080

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }
}
