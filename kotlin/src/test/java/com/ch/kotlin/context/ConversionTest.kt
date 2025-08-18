@file:Suppress("USELESS_IS_CHECK")

package com.ch.kotlin.context
import kotlin.test.Test
import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.BeforeTest


@DisplayName("Kotlin 형변환 테스트")
class ConversionTest {
    private lateinit var conversion: Conversion

    @BeforeTest
    fun beforeEachSetUp() {
        conversion = Conversion(25)
    }

    @Test
    @DisplayName("기본 Int 타입 값 조회 테스트")
    fun getAge() {
        assertThat(conversion.getAge()).isEqualTo(25)
    }

    @Test
    @DisplayName("Int를 Long으로 형변환 테스트")
    fun getAgeLong() {
        val ageLong = conversion.getAgeLong()
        assertThat(ageLong).isEqualTo(25L)
        assertThat(ageLong is Long).isTrue()
    }

    @Test
    @DisplayName("Int를 Float으로 형변환 테스트")
    fun getAgeFloat() {
        val ageFloat = conversion.getAgeFloat()
        assertThat(ageFloat).isEqualTo(25.0f)
        assertThat(ageFloat is Float).isTrue()
    }

    @Test
    @DisplayName("Int를 Double로 형변환 테스트")
    fun getAgeDouble() {
        val ageDouble = conversion.getAgeDouble()
        assertThat(ageDouble).isEqualTo(25.0)
        assertThat(ageDouble is Double).isTrue()
    }

    @Test
    @DisplayName("Long을 다시 Int로 형변환 테스트")
    fun getAgeLongToInt() {
        val ageLongToInt = conversion.getAgeLongToInt()
        assertThat(ageLongToInt).isEqualTo(25)
        assertThat(ageLongToInt is Int).isTrue()
    }

    @Test
    @DisplayName("Int를 String으로 형변환 테스트")
    fun getAgeString() {
        val ageString = conversion.getAgeString()
        assertThat(ageString).isEqualTo("25")
        assertThat(ageString is String).isTrue()
    }

    @Test
    @DisplayName("String을 다시 Int로 형변환 테스트")
    fun getAgeStringToInt() {
        val ageStringToInt = conversion.getAgeStringToInt()
        assertThat(ageStringToInt).isEqualTo(25)
        assertThat(ageStringToInt is Int).isTrue()
    }

    @Test
    @DisplayName("형변환 체이닝 테스트")
    fun conversionChainingTest() {
        // Int -> Long -> Int 변환 체이닝
        val originalAge = conversion.getAge()
        val longAge = conversion.getAgeLong()
        val backToInt = conversion.getAgeLongToInt()

        assertThat(originalAge).isEqualTo(backToInt)
        assertThat(longAge).isEqualTo(25L)
    }

    @Test
    @DisplayName("타입 안전성 검증 테스트")
    fun typeSafetyValidationTest() {
        assertThat(conversion.getAge() is Int).isTrue()
        assertThat(conversion.getAgeLong() is Long).isTrue()
        assertThat(conversion.getAgeFloat() is Float).isTrue()
        assertThat(conversion.getAgeDouble() is Double).isTrue()
        assertThat(conversion.getAgeString() is String).isTrue()
        assertThat(conversion.getAgeStringToInt() is Int).isTrue()
    }
}