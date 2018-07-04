package com.binlly.gankee.base.adapter

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.fangxin.assessment.base.adapter.ItemViewDelegate

/**
 * Created by binlly on 2017/5/13.
 */

abstract class BaseDelegate<in Model>(
        val context: Context): ItemViewDelegate<Model, BaseViewHolder> {

    override fun onCreateDefViewHolder(view: View): BaseViewHolder? {
        return null //用默认的BaseViewHolder
    }

    override fun getItemViewLayoutId(): Int {
        return layoutResId
    }

    override fun convert(holder: BaseViewHolder, model: Model) {
        childConvert(holder, model)
    }

    abstract val layoutResId: Int

    abstract fun childConvert(holder: BaseViewHolder, item: Model)
}
