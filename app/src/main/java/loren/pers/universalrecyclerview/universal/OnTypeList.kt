package loren.pers.universalrecyclerview.universal

/**
 * Copyright © 2018/6/20 by loren
 */
interface OnTypeList<T> {
    fun getTitle(): String
    fun getBody(): MutableList<T>
}