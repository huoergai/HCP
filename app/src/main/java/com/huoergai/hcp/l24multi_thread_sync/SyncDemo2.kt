package com.huoergai.hcp.l24multi_thread_sync

class SyncDemo2 : ITest {
    @Volatile
    private var x = 0

    @Synchronized
    private fun count() {
        x++
    }

    override fun runTest() {
        Thread {
            for (i in 0 until 100000) {
                count()
            }
            println("1 final count = $x")
        }.start()

        Thread {
            for (i in 0 until 100000) {
                count()
            }
            println("2 final count = $x")
        }.start()
    }
}