package com.ch.kotlin.context

import kotlin.test.Test
import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.assertThat;
import kotlin.test.BeforeTest

@DisplayName("Kotlin 변수 테스트")
class VariableTest {
    private lateinit var variable: Variable

    @BeforeTest
    fun beforeEachSetUp() {
        variable = Variable("Lee")
    }

    @Test
    @DisplayName("변수 출력 기능 테스트")
    fun varPrint() {
        variable.print()
    }

    @Test
    @DisplayName("var 변수 값 변경 테스트")
    fun setVar(){
        variable.setName("Kim")
        assertThat(variable.getName()).isEqualTo("Kim")
    }

    @Test
    @DisplayName("var 변수 값 조회 테스트")
    fun getVar(){
        assertThat(variable.getName()).isEqualTo("Lee")
    }

    @Test
    @DisplayName("nullable 변수 null 값 확인 테스트")
    fun getNullVar(){
        assertThat(variable.getNullAbleVar()).isNull()
    }

    @Test
    @DisplayName("val 불변 변수 값 조회 테스트")
    fun getVal(){
        val name = variable.getNonEditName()
        assertThat(name).isEqualTo("nonEditName")
    }

}