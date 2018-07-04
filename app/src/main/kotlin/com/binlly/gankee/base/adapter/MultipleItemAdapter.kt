package com.fangxin.assessment.base.adapter

import android.os.Bundle
import android.support.v4.util.SparseArrayCompat
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by binlly on 2017/4/6.
 */

open class MultipleItemAdapter<T: MultiItemEntity>(
        data: List<T>?
): BaseMultiItemQuickAdapter<T, BaseViewHolder>(data) {

    private var handler: ((action: Int, position: Int, item: T?, args: Bundle?) -> Unit)? = null

    private val delegates = SparseArrayCompat<ItemViewDelegate<T, out BaseViewHolder>>()

    init {
        init()
    }

    private fun init() {
        setLoadMoreView(LoadMoreView())
    }

    override fun convert(holder: BaseViewHolder, item: T) {
        if (delegates.size() > 0) {
            convert(holder, item, holder.itemViewType)
        }
    }

    fun <V: BaseViewHolder> addItemViewDelegate(
            viewType: Int, itemViewDelegate: ItemViewDelegate<T, V>
    ) {
        addItemType(viewType, itemViewDelegate.getItemViewLayoutId())
        if (delegates.get(viewType) != null) {
            throw IllegalArgumentException("An ItemViewDelegate is already registered for the viewType = $viewType. Already registered ItemViewDelegate is " + delegates.get(
                    viewType))
        }
        delegates.put(viewType, itemViewDelegate)
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var holder: BaseViewHolder? = null
        if (delegates.size() > 0) {
            val delegate = delegates.get(viewType) ?: throw IllegalArgumentException("No ItemViewDelegateManager added that matches in data source")

            val view = mLayoutInflater.inflate(delegate.getItemViewLayoutId(), parent, false)
            holder = delegate.onCreateDefViewHolder(view)
        }
        return if (holder == null) super.onCreateDefViewHolder(parent, viewType) else holder
    }

    private fun <V: BaseViewHolder> convert(holder: V, item: T, itemViewType: Int) {
        val delegate = delegates.get(itemViewType) as? ItemViewDelegate<T, V> ?: throw
        IllegalArgumentException("No ItemViewDelegateManager added that matches in data source")

        delegate.convert(holder, item)
    }

    fun setMessageHandler(handler: ((action: Int, position: Int, item: T?, args: Bundle?) -> Unit)?) {
        this.handler = handler
    }

    fun sendEmptyMessage(action: Int) {
        sendMessage(action, -1, null, null)
    }

    @JvmOverloads
    fun sendMessage(action: Int, position: Int, item: T?, args: Bundle? = null) {
        handler?.invoke(action, position, item, args)
    }
}
