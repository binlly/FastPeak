package com.binlly.gankee.base.widget

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.binlly.gankee.R

/**
 * Created by yy on 2017/8/24.
 */

class LoadingProgressDialog(private val mContext: Context): Dialog(mContext,
        R.style.LoadingDialog) {
    private lateinit var tv_info: TextView

    init {
        initView()
    }

    private fun initView() {
        val view = View.inflate(mContext, R.layout.dialog_loading_progress, null)
        tv_info = view.findViewById<View>(R.id.progress_info) as TextView
        val params = LinearLayout.LayoutParams(-2, -2)
        setContentView(view, params)
    }

    fun setTextInfo(info: String?) {
        if (TextUtils.isEmpty(info)) tv_info.visibility = View.GONE
        else {
            tv_info.visibility = View.VISIBLE
            tv_info.text = info
        }
    }
}
