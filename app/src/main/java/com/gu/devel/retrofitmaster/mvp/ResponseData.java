package com.gu.devel.retrofitmaster.mvp;

import android.util.Log;

public class ResponseData {
  private String name;
  private String code;
  private String time;

  public ResponseData(String name, String code, String time) {
    super();
    this.name = name;
    this.code = code;
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getResult() {
    return "name=" + name + "\n\ncode=" + code + "\n\ntime=" + time;
  }

  public void print() {
    Log.e("tag", "print:data=" + this + ",\n" + getResult());
  }
}
