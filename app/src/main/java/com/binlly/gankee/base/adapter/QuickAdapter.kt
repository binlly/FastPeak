package com.fangxin.assessment.base.adapter

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by binlly on 2017/4/22.
 */

abstract class QuickAdapter<T, K: BaseViewHolder>: BaseQuickAdapter<T, K> {
    private var handler: ((action: Int, position: Int, item: T?, args: Bundle?, obj: Any?) -> Unit)? = null

    constructor(layoutResId: Int, data: List<T>): super(layoutResId, data) {
        init()
    }

    constructor(data: List<T>): super(data) {
        init()
    }

    constructor(layoutResId: Int): super(layoutResId) {
        init()
    }

    private fun init() {
        setLoadMoreView(LoadMoreView())
    }

    fun setMessageHandler(
            handler: ((action: Int, position: Int, item: T?, args: Bundle?, obj: Any?) -> Unit)?
    ) {
        this.handler = handler
    }

    fun sendEmptyMessage(action: Int) {
        sendMessage(action, -1, null, null, null)
    }

    @JvmOverloads
    fun sendMessage(action: Int, position: Int, item: T?, args: Bundle? = null, obj: Any?) {
        handler?.invoke(action, position, item, args, obj)
    }
}
