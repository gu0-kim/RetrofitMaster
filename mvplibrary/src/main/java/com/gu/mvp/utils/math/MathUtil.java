package com.gu.mvp.utils.math;

import java.math.BigDecimal;

/** Created by devel on 2018/5/1. */
public class MathUtil {
  public static String b2Mb(long size, int num) {
    return b2Mb(size, num, "");
  }

  private static float b2Mb_float(long size, int num) {
    return new BigDecimal(size / 1000f / 1000f).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
  }

  public static String b2Mb(long size, int num, String unit) {
    return String.valueOf(b2Mb_float(size, num)) + unit;
  }
}
