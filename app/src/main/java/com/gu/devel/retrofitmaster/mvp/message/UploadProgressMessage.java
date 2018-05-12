package com.gu.devel.retrofitmaster.mvp.message;

import com.gu.mvp.bus.Message;

/** Created by devel on 2018/4/16. */
public class UploadProgressMessage extends Message {

  public UploadProgressMessage(Object data) {
    super(MessageType.TYPE_UPLOAD, data);
  }
}
