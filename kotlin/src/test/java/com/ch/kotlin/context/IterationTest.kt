package com.ch.kotlin.context

import kotlin.test.Test
import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.BeforeTest

@DisplayName("Kotlin 반복문 테스트")
class IterationTest {
    private lateinit var iteration: Iteration

    @BeforeTest
    fun beforeEachSetUp() {
        iteration = Iteration()
    }

    @Test
    @DisplayName("for 반복문 - 1부터 10까지 합계 테스트")
    fun intForOfPlus() {
        val result = iteration.intForOfPlus(10)
        assertThat(result).isEqualTo(55) // 1+2+3+...+10 = 55
    }

    @Test
    @DisplayName("for 반복문 - 1부터 5까지 합계 테스트")
    fun intForOfPlus_SmallNumber() {
        val result = iteration.intForOfPlus(5)
        assertThat(result).isEqualTo(15) // 1+2+3+4+5 = 15
    }

    @Test
    @DisplayName("for 반복문 - downTo 10부터 5까지 합계 테스트")
    fun intForOfDownTo() {
        val result = iteration.intForOfDownTo(10, 5)
        assertThat(result).isEqualTo(45) // 10+9+8+7+6+5 = 45
    }

    @Test
    @DisplayName("for 반복문 - downTo 잘못된 범위 테스트")
    fun intForOfDownTo_InvalidRange() {
        val result = iteration.intForOfDownTo(5, 10)
        assertThat(result).isEqualTo(-1) // num < downToValue인 경우
    }

    @Test
    @DisplayName("for 반복문 - step 2로 1부터 10까지 합계 테스트")
    fun intForOfStep() {
        val result = iteration.intForOfStep(10, 2)
        assertThat(result).isEqualTo(25) // 1+3+5+7+9 = 25
    }

    @Test
    @DisplayName("for 반복문 - step 3으로 1부터 10까지 합계 테스트")
    fun intForOfStep_Step3() {
        val result = iteration.intForOfStep(10, 3)
        assertThat(result).isEqualTo(22) // 1+4+7+10 = 22
    }

    @Test
    @DisplayName("for 반복문 - char 'a'부터 'z'까지 문자열 테스트")
    fun charForOfAToZ() {
        val result = iteration.charForOfAToZ()
        assertThat(result).isEqualTo("abcdefghijklmnopqrstuvwxyz")
        assertThat(result).hasSize(26)
    }

    @Test
    @DisplayName("while 반복문 - 0부터 9까지 합계 테스트")
    fun whileFor() {
        val result = iteration.whileFor()
        assertThat(result).isEqualTo(45) // 0+1+2+...+9 = 45
    }

    @Test
    @DisplayName("for 반복문 - 경계값 테스트 (1)")
    fun intForOfPlus_BoundaryValue() {
        val result = iteration.intForOfPlus(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    @DisplayName("for 반복문 - downTo 동일한 값 테스트")
    fun intForOfDownTo_SameValue() {
        val result = iteration.intForOfDownTo(5, 5)
        assertThat(result).isEqualTo(5)
    }

    @Test
    @DisplayName("for 반복문 - step 1로 테스트")
    fun intForOfStep_Step1() {
        val result = iteration.intForOfStep(5, 1)
        assertThat(result).isEqualTo(15) // 1+2+3+4+5 = 15
    }
}