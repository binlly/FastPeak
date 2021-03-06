package com.binlly.gankee.business.debug.adapter

import android.content.Context
import android.widget.TextView
import com.binlly.gankee.R
import com.binlly.gankee.base.adapter.BaseDelegate
import com.binlly.gankee.business.debug.model.DebugModel
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by binlly on 2017/5/13.
 */

class RouterDelegate(context: Context): BaseDelegate<DebugModel>(context) {

    override val layoutResId: Int
        get() = R.layout.test_item_key_value

    override fun childConvert(holder: BaseViewHolder, item: DebugModel) {
        val key = holder.getView<TextView>(R.id.key)
        val value = holder.getView<TextView>(R.id.value)

        key.text = item.router.key
        value.text = item.router.value
        value.setTextColor(item.valueColor)
    }
}