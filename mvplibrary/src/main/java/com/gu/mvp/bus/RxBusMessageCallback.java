package com.gu.mvp.bus;

import io.reactivex.functions.Consumer;

/** if you want to receive rxbus message ,implements this interface */
public interface RxBusMessageCallback extends Consumer<Message> {}
