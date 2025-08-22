package com.ch.kotlin.context

class HigherFunction(private val age : Int) {

    private val plusOperator : (Int, Int) -> Int = {a, b -> a + b}
    private val plusCustomOperator : (Int, Int) -> Int = {a, b -> a + b * 2}
    private val printAction: (Int) -> Unit = {println("현재 나이에 $it 만큼 추가합니다.")}

    fun getAge():Int = age;
    fun getAgePlus(customFlag:Boolean, plusValue:Int) : Int{
        printAction(plusValue)
        if(customFlag){
            return plusAge(plusOperator, getAge(), plusValue)
        }
        return plusAge(plusCustomOperator, getAge(), plusValue)
    }
    fun plusAge(plusFunction: (Int, Int) -> Int, age : Int, plugValue : Int) : Int{
        return plusFunction(age, plugValue)
    }

}