package com.gu.devel.retrofitmaster.mvp;

/** Created by devel on 2018/4/9. */
public class Result {
  private boolean suc;
  private String msg;

  public Result(boolean suc, String msg) {
    this.suc = suc;
    this.msg = msg;
  }

  public boolean isSuc() {
    return suc;
  }

  public void setSuc(boolean suc) {
    this.suc = suc;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
