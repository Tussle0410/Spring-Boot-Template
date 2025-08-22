package com.ch.kotlin.context

import kotlin.test.Test
import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.BeforeTest

@DisplayName("Kotlin 조건문 테스트")
class ConditionalTest {
    private lateinit var conditional: Conditional

    @BeforeTest
    fun beforeEachSetUp() {
        conditional = Conditional(25)
    }

    @Test
    @DisplayName("if-else 조건문 - 유효한 나이 테스트")
    fun validationAge_ValidAge() {
        assertThat(conditional.validationAge()).isTrue()
    }

    @Test
    @DisplayName("if-else 조건문 - 음수 나이 테스트")
    fun validationAge_NegativeAge() {
        val negativeAgeConditional = Conditional(-5)
        assertThat(negativeAgeConditional.validationAge()).isFalse()
    }

    @Test
    @DisplayName("if-else 조건문 - 0세 경계값 테스트")
    fun validationAge_ZeroAge() {
        val zeroAgeConditional = Conditional(0)
        assertThat(zeroAgeConditional.validationAge()).isTrue()
    }

    @Test
    @DisplayName("when 조건문 - 어린이 연령대 테스트")
    fun getAgeType_Child() {
        val childConditional = Conditional(7)
        assertThat(childConditional.getAgeType()).isEqualTo("어린이")
    }

    @Test
    @DisplayName("when 조건문 - 학생 연령대 테스트")
    fun getAgeType_Student() {
        val studentConditional = Conditional(15)
        assertThat(studentConditional.getAgeType()).isEqualTo("학생")
    }

    @Test
    @DisplayName("when 조건문 - 성인 연령대 테스트")
    fun getAgeType_Adult() {
        assertThat(conditional.getAgeType()).isEqualTo("성인")
    }

    @Test
    @DisplayName("when 조건문 - 경계값 테스트 (9세)")
    fun getAgeType_ChildBoundary() {
        val boundaryConditional = Conditional(9)
        assertThat(boundaryConditional.getAgeType()).isEqualTo("어린이")
    }

    @Test
    @DisplayName("when 조건문 - 경계값 테스트 (10세)")
    fun getAgeType_StudentBoundary() {
        val boundaryConditional = Conditional(10)
        assertThat(boundaryConditional.getAgeType()).isEqualTo("학생")
    }

    @Test
    @DisplayName("when 조건문 - 경계값 테스트 (19세)")
    fun getAgeType_StudentUpperBoundary() {
        val boundaryConditional = Conditional(19)
        assertThat(boundaryConditional.getAgeType()).isEqualTo("학생")
    }

    @Test
    @DisplayName("when 조건문 - 경계값 테스트 (20세)")
    fun getAgeType_AdultBoundary() {
        val boundaryConditional = Conditional(20)
        assertThat(boundaryConditional.getAgeType()).isEqualTo("성인")
    }
}