package com.gu.mvp.bus;

import java.io.Serializable;

/** Created by devel on 2018/4/11. */
public class Message implements Serializable {
  private Object data;
  private int typeCode;

  public Message(int type, Object data) {
    this.typeCode = type;
    this.data = data;
  }

  public Object getData() {
    return data;
  }

  public int typeCode() {
    return typeCode;
  }
}
