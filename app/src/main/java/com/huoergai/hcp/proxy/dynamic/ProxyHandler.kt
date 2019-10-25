package com.huoergai.hcp.proxy.dynamic

import com.huoergai.hcp.proxy.ICook
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ProxyHandler(private val cook: ICook) : InvocationHandler {


    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any? {
        println("before cook")
        val result = method.invoke(cook, args)
        println("after cook")
        return result
    }
}