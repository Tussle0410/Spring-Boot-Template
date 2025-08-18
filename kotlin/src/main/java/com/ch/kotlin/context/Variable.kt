package com.ch.kotlin.context

/**
 * var : Mutable <br>
 *
 * val : Immutable(Read Only, Java final)<br>
 *
 * ? : nullable
 */
class Variable(
    private var name : String,
    private var nullAbleVar : String?
) {
    private val nonEditName:String = "nonEditName"

    constructor(name:String) : this(name, null)

    fun print() {
        println(getName())
        print(getNullAbleVar())
    }

    fun getName():String = name
    fun getNullAbleVar():String? = nullAbleVar
    fun getNonEditName():String = nonEditName

    fun setName(name: String){
        this.name = name;
    }
}