package com.binlly.gankee.base.mvp

/**
 * Created by yy on 2017/8/23.
 * @param P Presenter
 */

interface BaseMvpView<out P>: BaseView {
    fun P(): P
}