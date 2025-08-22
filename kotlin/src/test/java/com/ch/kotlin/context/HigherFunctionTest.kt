package com.ch.kotlin.context

import kotlin.test.Test
import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.BeforeTest

@DisplayName("Kotlin 고차함수 테스트")
class HigherFunctionTest {
    private lateinit var higherFunction: HigherFunction

    @BeforeTest
    fun beforeEachSetUp() {
        higherFunction = HigherFunction(25)
    }

    @Test
    @DisplayName("나이 값 조회 테스트")
    fun getAge() {
        assertThat(higherFunction.getAge()).isEqualTo(25)
    }

    @Test
    @DisplayName("기본 더하기 연산자 테스트 (customFlag = true)")
    fun getAgePlus_BasicOperator() {
        val result = higherFunction.getAgePlus(true, 5)
        assertThat(result).isEqualTo(30) // 25 + 5 = 30
    }

    @Test
    @DisplayName("커스텀 더하기 연산자 테스트 (customFlag = false)")
    fun getAgePlus_CustomOperator() {
        val result = higherFunction.getAgePlus(false, 5)
        assertThat(result).isEqualTo(35) // 25 + (5 * 2) = 35
    }

    @Test
    @DisplayName("기본 더하기 연산자 - 다른 값 테스트")
    fun getAgePlus_BasicOperator_DifferentValue() {
        val result = higherFunction.getAgePlus(true, 10)
        assertThat(result).isEqualTo(35) // 25 + 10 = 35
    }

    @Test
    @DisplayName("커스텀 더하기 연산자 - 다른 값 테스트")
    fun getAgePlus_CustomOperator_DifferentValue() {
        val result = higherFunction.getAgePlus(false, 10)
        assertThat(result).isEqualTo(45) // 25 + (10 * 2) = 45
    }

    @Test
    @DisplayName("고차함수 직접 호출 - 기본 더하기 테스트")
    fun plusAge_DirectCall_BasicOperator() {
        val plusOperator: (Int, Int) -> Int = { a, b -> a + b }
        val result = higherFunction.plusAge(plusOperator, 20, 5)
        assertThat(result).isEqualTo(25) // 20 + 5 = 25
    }

    @Test
    @DisplayName("고차함수 직접 호출 - 커스텀 연산자 테스트")
    fun plusAge_DirectCall_CustomOperator() {
        val customOperator: (Int, Int) -> Int = { a, b -> a + b * 2 }
        val result = higherFunction.plusAge(customOperator, 20, 5)
        assertThat(result).isEqualTo(30) // 20 + (5 * 2) = 30
    }

    @Test
    @DisplayName("고차함수 직접 호출 - 곱하기 연산자 테스트")
    fun plusAge_DirectCall_MultiplyOperator() {
        val multiplyOperator: (Int, Int) -> Int = { a, b -> a * b }
        val result = higherFunction.plusAge(multiplyOperator, 5, 4)
        assertThat(result).isEqualTo(20) // 5 * 4 = 20
    }

    @Test
    @DisplayName("경계값 테스트 - 0 추가")
    fun getAgePlus_ZeroValue() {
        val basicResult = higherFunction.getAgePlus(true, 0)
        val customResult = higherFunction.getAgePlus(false, 0)

        assertThat(basicResult).isEqualTo(25) // 25 + 0 = 25
        assertThat(customResult).isEqualTo(25) // 25 + (0 * 2) = 25
    }

    @Test
    @DisplayName("음수 값 테스트")
    fun getAgePlus_NegativeValue() {
        val basicResult = higherFunction.getAgePlus(true, -5)
        val customResult = higherFunction.getAgePlus(false, -5)

        assertThat(basicResult).isEqualTo(20) // 25 + (-5) = 20
        assertThat(customResult).isEqualTo(15) // 25 + (-5 * 2) = 15
    }
}