package com.binlly.gankee.base.mvp

/**
 * Created by yy on 2017/8/23.
 */

interface BaseView {

    fun showLoading()

    fun showLoading(info: String?)

    fun hideLoading()

    //    fun setPageSuccess()
    //
    //    fun setPageError()
    //
    //    fun setPageEmpty()
}
