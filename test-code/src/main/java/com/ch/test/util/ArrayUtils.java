package com.ch.test.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArrayUtils {
  private ArrayUtils() {
    String exceptionMessage = "Don't Create Utility class";
    log.error("{}", exceptionMessage);
    throw new IllegalStateException(exceptionMessage);
  }

  public static int intArrayIndexOf(int[] arr, int val) {
    int size = arr.length;
    for (int index = 0; index < size; index++) {
      if (arr[index] == val) {
        return index;
      }
    }
    return -1;
  }
}
