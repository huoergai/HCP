package com.huoergai.hcp.lesson24

class SyncDemo3 : ITest {
    private var x = 0
    private var y = 0
    private var name = ""

    private val monitor1 = Any()
    private val monitor2 = Any()

    private fun count(n: Int) {
        synchronized(monitor1) {
            x = n
            y = n
        }
    }

    private fun minus(delta: Int) {
        synchronized(monitor1) {
            x -= delta
            y -= delta
        }
    }

    private fun setName(n: String) {
        synchronized(monitor2) {
            name = n
        }
    }

    override fun runTest() {

    }
}