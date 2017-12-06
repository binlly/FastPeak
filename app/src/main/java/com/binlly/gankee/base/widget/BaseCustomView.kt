package com.binlly.gankee.base.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout


/**
 * Created by yy on 2017/11/16
 */
abstract class BaseCustomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): RelativeLayout(context, attrs, defStyleAttr) {

    protected abstract val styleable: IntArray

    protected abstract val layout: Int

    init {
        val a = context.obtainStyledAttributes(attrs, styleable)
        initAttributes(a)
        initView(context)
        a.recycle()
    }

    private fun initView(context: Context) {
        val view = View.inflate(getContext(), layout, this)
        initData(context)
    }

    protected abstract fun initAttributes(a: TypedArray)

    protected abstract fun initData(context: Context)
}
