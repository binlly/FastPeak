package com.binlly.gankee.base.mvp

/**
 * Created by yy on 2017/11/16.
 */
interface SimpleListView<in T>: BaseView {

    fun refresh(list: List<T>)

    fun loadMore(list: List<T>)

    fun loadEnd()

    fun loadComplete()

    fun loadFail()

    fun setPageSuccess()

    fun setPageError()

    fun setPageEmpty()
}