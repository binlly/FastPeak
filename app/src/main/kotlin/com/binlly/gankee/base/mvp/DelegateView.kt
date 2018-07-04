package com.binlly.gankee.base.mvp

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.binlly.gankee.R

/**
 * Created by yy on 2017/11/15.
 * 统一拦截处理setContentView()
 */
class DelegateView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val root: View
    private val toolbar: Toolbar
    private val content: FrameLayout

    init {
        orientation = LinearLayout.VERTICAL
        root = LayoutInflater.from(context).inflate(R.layout.layout_root_delegate, this)
        toolbar = root.findViewById(R.id.toolbar)
        content = root.findViewById(R.id.content)
    }

    fun addContent(layoutId: Int) {
        val contentView = LayoutInflater.from(context).inflate(layoutId, null)
        content.addView(contentView, FrameLayout.LayoutParams(-1, -1))
    }

    fun getToolbar(): Toolbar = toolbar

    fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    fun showToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    fun getContentView(): View = content
}