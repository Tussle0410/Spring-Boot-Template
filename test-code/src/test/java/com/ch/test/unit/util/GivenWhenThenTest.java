package com.ch.test.unit.util;

import static com.ch.test.util.ArrayUtils.intArrayIndexOf;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Util 클래스")
class GivenWhenThenTest {
  @Test
  @DisplayName("intArrayIndexOf 메서드에서 배열의 존재하는 값일 떄 인덱스를 반환한다.")
  void When_Exist_Number_Expect_Index(){
    //given
    int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int givenNumber = 3;

    //when
    int result = intArrayIndexOf(array, givenNumber);

    //then
    assertThat(result).isEqualTo(3);
  }

  @Test
  @DisplayName("intArrayIndexOf 메서드에서 배열의 존재하지 않는 값일 떄 -1을 반환한다.")
  void When_Not_Exist_Number_Expect_Minus_One(){
    //given
    int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int givenNumber = 11;

    //when
    int result = intArrayIndexOf(array, givenNumber);

    //then
    assertThat(result).isEqualTo(-1);
  }

}
