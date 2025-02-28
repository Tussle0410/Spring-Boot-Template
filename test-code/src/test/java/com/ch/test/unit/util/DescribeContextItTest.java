package com.ch.test.unit.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DescribeContextItTest {
  @Nested
  @DisplayName("intArrayIndexOf 메서드의")
  class Describe_intArrayIndexOf {

    private final int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    int subject(int num) {
      return intArrayIndexOf(array, num);
    }

    @Nested
    @DisplayName("만약, 배열에 존재하는 정수가 주어졌을 때")
    class Context_with_exist_number {

      private final int givenNumber = 3;
      private final int expectedIndex = 3;

      @Test
      @DisplayName("정수가 존재하는 배열의 인덱스를 반환한다. ")
      void it_return_index() {
        int result = subject(givenNumber);
        assertThat(result).isEqualTo(expectedIndex);
      }

    }


    @Nested
    @DisplayName("만약, 배열에 존재하지 않는 정수가 주어졌을 때")
    class Context_with_not_exist_number {

      private final int givenmber = 11;
      private final int expectedIndex = -1;

      @Test
      @DisplayName("-1을 반환합니다.")
      void it_return_minus_one() {
        int result = subject(givenmber);
        assertThat(result).isEqualTo(expectedIndex);
      }
    }
  }

  int intArrayIndexOf(int[] arr, int val) {
    int size = arr.length;
    for (int index = 0; index < size; index++) {
      if (arr[index] == val) {
        return index;
      }
    }
    return -1;
  }
}
