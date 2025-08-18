package com.ch.kotlin.context

/**
 * Kotlin 형변환 테스트
 *
 * ⭐️ 코틀린은 암시적 형변환 허용 안함(타입 안정성, 예측 가능성, 명확성 부여)
 * ```java
 * var age : Long = 10L
 * var ageInt : Int = age  // ❌ 컴파일 오류!
 * ```
 */
class Conversion {
    private var age:Int
    private var ageString: String

    constructor(age : Int ){
        this.age = age
        this.ageString = age.toString()
        var ageLong : Long = age.toLong()
    }

    fun getAge():Int = age
    fun getAgeLong():Long = getAge().toLong()
    fun getAgeFloat():Float = getAge().toFloat()
    fun getAgeDouble():Double = getAge().toDouble()
    fun getAgeLongToInt():Int = getAgeLong().toInt()

    fun getAgeString():String = ageString
    fun getAgeStringToInt():Int =getAgeString().toInt()
}