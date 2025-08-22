package com.ch.kotlin.context

/**
 * for, while 반복문
 */
class Iteration {

    /**
     * for 반복문 int 형식 순차 반복
     *
     * 1 ~ num 순차의 합
     */
    fun intForOfPlus(num : Int) : Int {
        var sum = 0;

        for (i in 1..num) {
            sum += i;
        }
        return sum;
    }

    /**
     * for 반복문 int 형식 순차 반복
     *
     * num ~ downToValue 순차의 합
     */
    fun intForOfDownTo(num : Int, downToValue : Int) : Int {
        if(num < downToValue){
            return -1
        }
        var sum = 0;
        for (i in num downTo downToValue) {
            sum += i;
        }
        return sum;
    }

    /**
     * for 반복문 int 형식 순차 반복
     *
     * 1 ~ num step stepValue 순차의 합
     */
    fun intForOfStep(num : Int, stepValue : Int):Int{
        var sum = 0;
        for(i in 1..num step stepValue){
            sum += i
        }
        return sum
    }

    /**
     * for 반복문 char 형식 반복
     *
     * 'a' ~ 'z' 순차
     */
    fun charForOfAToZ():String{
        var result = ""
        for(i in 'a'..'z'){
            result += i
        }
        return result
    }

    /**
     * while 반복문 Int 형식 반복
     *
     * 1 ~ 10 순차의 합
     */
    fun whileFor() : Int{
        var sum = 0
        var i = 0
        while(i < 10){
            sum += i
            i++
        }
        return sum
    }

}