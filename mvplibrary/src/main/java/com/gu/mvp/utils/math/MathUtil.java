package com.gu.mvp.utils.math;

import java.math.BigDecimal;

/** Created by devel on 2018/5/1. */
public class MathUtil {
  /**
   * byte to MB
   *
   * @param size 数据
   * @param num 保留位数
   * @return 格式化数据
   */
  public static String b2Mb(long size, int num) {
    return b2Mb(size, num, "");
  }

  /**
   * byte to MB
   *
   * @param size 数据
   * @param num 保留的位数
   * @return 格式化数据
   */
  private static float b2Mb_float(long size, int num) {
    return new BigDecimal(size / 1000f / 1000f).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
  }

  /**
   * byte to MB
   *
   * @param size 数据
   * @param num 保留位数
   * @param unit 显示的单位
   * @return 格式化数据
   */
  public static String b2Mb(long size, int num, String unit) {
    return String.valueOf(b2Mb_float(size, num)) + unit;
  }
}
