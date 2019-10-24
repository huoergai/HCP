package com.huoergai.hcp.proxy.static_proxy

import com.huoergai.hcp.proxy.ICook

class TraditionalCook : ICook {
    override fun cook(): String {
        println("doing cook stuff")
        return "spicy dishes"
    }
}