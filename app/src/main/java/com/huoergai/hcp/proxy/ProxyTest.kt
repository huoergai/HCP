package com.huoergai.hcp.proxy

import com.huoergai.hcp.proxy.static_proxy.CookProxy
import com.huoergai.hcp.proxy.static_proxy.TraditionalCook
import java.lang.reflect.Proxy

/**
 * 代理demo
 */
class ProxyTest {
    fun main(args: Array<String>) {
        // 静态代理
        val traditionalCook = TraditionalCook()
        val cookProxy = CookProxy(traditionalCook)
        println(cookProxy.cook())

        // 动态代理
        val proxyClass = Proxy.getProxyClass(ClassLoader.getSystemClassLoader(), ICook::class.java)
        proxyClass.getConstructor()
        Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(),
            Array(1) { ICook::class.java }) { proxy, method, args -> "" }
    }
}