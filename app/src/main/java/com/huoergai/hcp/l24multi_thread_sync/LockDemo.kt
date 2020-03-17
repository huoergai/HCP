package com.huoergai.hcp.l24multi_thread_sync

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * read write lock demo
 */
class LockDemo : ITest {
    private var x = 0
    private val lock = ReentrantReadWriteLock()
    private val readLock = lock.readLock()
    private val writeLock = lock.writeLock()

    private fun count() {
        writeLock.lock()
        try {
            x++
        } finally {
            writeLock.unlock()
        }
    }

    private fun print() {
        readLock.lock()
        try {
            println("x = $x")
        } finally {
            readLock.unlock()
        }
    }

    override fun runTest() {

    }
}