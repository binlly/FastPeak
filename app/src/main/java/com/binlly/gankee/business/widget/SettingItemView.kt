package com.binlly.gankee.business.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.binlly.gankee.R
import com.binlly.gankee.base.widget.BaseCustomView
import kotlinx.android.synthetic.main.widget_setting_item.view.*

/**
 * Created by yy on 2017/12/6.
 */
class SettingItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): BaseCustomView(context, attrs, defStyleAttr) {

    override val styleable: IntArray
        get() = R.styleable.SettingItemView

    override val layout: Int
        get() = R.layout.widget_setting_item

    private var drawable: Drawable? = null
    private var name: String? = null
    private var showArrow = false

    override fun initAttributes(a: TypedArray) {
        drawable = a.getDrawable(R.styleable.SettingItemView_settingItemIcon)
        name = a.getString(R.styleable.SettingItemView_settingItemName)
        showArrow = a.getBoolean(R.styleable.SettingItemView_showArrow, false)
    }

    override fun initData(context: Context) {
        setName(name)
        setIcon(drawable)
        if (showArrow) showArrow() else hideArrow()
    }

    fun setIcon(drawable: Drawable?) {
        if (drawable == null) icon.visibility = View.INVISIBLE
        else {
            icon.visibility = View.VISIBLE
            icon.setImageDrawable(drawable)
        }
    }

    fun setName(name: String?) {
        text_name.text = name
    }

    fun showArrow() {
        arrow.visibility = View.VISIBLE
    }

    fun hideArrow() {
        arrow.visibility = View.GONE
    }

    fun onClick(op: () -> Unit) {
        setOnClickListener {
            op.invoke()
        }
    }
}