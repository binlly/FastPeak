package com.binlly.gankee.repo

import android.util.Log
import com.binlly.gankee.repo.mock.MOCK
import com.binlly.gankee.service.Services
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class DynamicProxy(private val target: Any, private val mockTarget: Any): InvocationHandler {

    private val TAG = DynamicProxy::class.java.simpleName

    @Throws(Throwable::class)
    override fun invoke(
            proxy: Any, method: Method, args: Array<Any>
    ): Any {
        before()
        val mock = method.getAnnotation(MOCK::class.java)
        val result = if (mock != null) {
            Log.d(TAG, "mock.value = " + mock.value)
            if (Services.remoteConfig().isMock(mock.value)) {
                method.invoke(mockTarget, *args)
            } else {
                method.invoke(target, *args)
            }
        } else {
            method.invoke(target, *args)
        }
        after()
        return result
    }

    fun <T> getProxy(): T {
        return Proxy.newProxyInstance(target.javaClass.classLoader, target.javaClass.interfaces, this) as T
    }

    private fun before() {
        // do nothing
    }

    private fun after() {
        // do nothing
    }
}