package com.huoergai.hcp.proxy.static_proxy

import com.huoergai.hcp.proxy.ICook

class CookProxy(private val realCook: ICook) : ICook {

    override fun cook(): String {
        println("proxy cooking")
        return "dishes" + realCook.cook()
    }

}