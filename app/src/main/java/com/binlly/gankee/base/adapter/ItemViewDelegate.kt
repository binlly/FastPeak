package com.fangxin.assessment.base.adapter

import android.view.View

import com.chad.library.adapter.base.BaseViewHolder

/**
 * @param <T>
 */
interface ItemViewDelegate<in T, V: BaseViewHolder> {

    fun getItemViewLayoutId(): Int

    // boolean isForViewType(T item);
    fun onCreateDefViewHolder(view: View): V?

    fun convert(holder: V, t: T)
}