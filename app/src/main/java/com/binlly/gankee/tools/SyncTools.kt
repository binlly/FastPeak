package com.binlly.gankee.tools

import java.util.concurrent.locks.Lock

/**
 * Created by yy on 2017/8/23.
 */

fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}