package com.huoergai.hcp.l24multi_thread_sync

class SingleMan private constructor() {
    companion object {
        @Volatile
        private lateinit var instance: SingleMan
        private val m = Any()

        fun getInstance(): SingleMan {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SingleMan()
                    }
                }
            }
            return instance
        }
    }

}