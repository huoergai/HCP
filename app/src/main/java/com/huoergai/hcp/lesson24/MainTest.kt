package com.huoergai.hcp.lesson24

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    // threadTest()
    // runnableTest()
    // threadFactoryTest()
    // executorTest()
    // callableTest()

    // SyncDemo1().runTest()
    // SyncDemo2().runTest()
    SyncDemo3().runTest()
}

fun threadTest() {
    val t = Thread(Runnable {
        println("Thread test")
    })
    t.start()
}

fun runnableTest() {
    val r = Runnable { println("runnable test") }
    val t = Thread(r)
    t.start()

}

fun threadFactoryTest() {
    val tf: ThreadFactory = object : ThreadFactory {
        var count = AtomicInteger(0)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "Thread-" + count.incrementAndGet())
        }
    }

    val r = Runnable {
        println(Thread.currentThread().name + " start")
    }

    val t1 = tf.newThread(r)
    val t2 = tf.newThread(r)
    t1.start()
    t2.start()


}

fun executorTest() {
    val bq = LinkedBlockingQueue<Runnable>(100)
    val pools = ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS, bq)
    val r = Runnable {
        println(Thread.currentThread().name + " start")
        Thread.sleep(2000)
    }

    pools.execute(r)
    pools.execute(r)
}

fun callableTest() {
    val bq = LinkedBlockingQueue<Runnable>(100)
    val pools = ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS, bq)

    val cb = Callable<String> {
        Thread.sleep(2000)
        "callable done"
    }

    val future = pools.submit(cb)
    val result = future.get()
    println("callable result = $result")
}

