package com.ch.kotlin.context

/**
 * if else = Java if-else
 *
 * when = Java switch 유사
 *
 * is  = Java instanceof
 */
class Conditional(private var age : Int) {

    fun getAge():Int = age

    /**
     * if-else 조건문 사용
     */
    fun validationAge() : Boolean{
        if(age >= 0){
            return true;
        }
        return false;
    }

    /**
     * when 조건문 사용
     */
    fun getAgeType() : String{
        return when(age){
            in(0..9)-> "어린이"
            in(10..19) -> "학생"
            else -> "성인"
        }
    }

}