package com.binlly.gankee.base.rx

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by binlly
 *
 *
 * date: 2016/4/29 15:58
 * desc: Don't break the chain: use RxJava's compose() operator
 */
object SchedulersCompat {

    fun <T> applyComputationSchedulers(): ObservableTransformer<T, T> {
        return subscribeOnMain(Schedulers.computation())
    }

    fun <T> applyIoSchedulers(): ObservableTransformer<T, T> {
        return subscribeOnMain(Schedulers.io())
    }

    fun <T> applyNewSchedulers(): ObservableTransformer<T, T> {
        return subscribeOnMain(Schedulers.newThread())
    }

    fun <T> applyTrampolineSchedulers(): ObservableTransformer<T, T> {
        return subscribeOnMain(Schedulers.trampoline())
    }

    private fun <T> subscribeOnMain(scheduler: Scheduler): ObservableTransformer<T, T> {
        return ObservableTransformer { it.subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread()) }
    }
}
