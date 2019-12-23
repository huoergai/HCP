package com.huoergai.hcp.l24multi_thread_sync

class SyncDemo1 : ITest {
    private var x = 0
    private var y = 0

    @Synchronized
    private fun count(n: Int) {
        x = n
        y = n
        if (x != y) {
            println("error: x=$x, y = $y")
        }
    }

    override fun runTest() {
        Thread {
            for (i in 0..100_000) {
                count(i)
            }
        }.start()

        Thread {
            for (i in 0..100_000) {
                count(i)
            }
        }.start()
    }
}