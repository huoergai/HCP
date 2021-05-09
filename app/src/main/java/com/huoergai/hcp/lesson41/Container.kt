package com.huoergai.hcp.lesson41

class Container<E> {
    val datas: MutableList<E> = mutableListOf()

    fun add(e: E) {
        datas.add(e)
    }

    fun get(index: Int): E {
        return datas[index]
    }
}