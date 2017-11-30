package com.binlly.gankee.business.test.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import com.binlly.gankee.Build
import com.binlly.gankee.R
import com.binlly.gankee.base.adapter.BaseDelegate
import com.binlly.gankee.business.test.model.TestModel
import com.binlly.gankee.service.Services
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by binlly on 2017/5/13.
 */

class EnvDelegate(context: Context): BaseDelegate<TestModel>(context) {

    private var dialog: AlertDialog? = null

    override val layoutResId: Int
        get() = R.layout.test_item_switch_env

    override fun childConvert(holder: BaseViewHolder, item: TestModel) {
        val text = holder.getView<TextView>(R.id.env_name)
        val icon = holder.getView<ImageView>(R.id.env_selected)

        text.text = item.env.value
        icon.isSelected = item.env.isSelected
        if (item.env.isSelected) {
            text.setTextColor(Color.parseColor("#e2001e"))
        } else {
            text.setTextColor(Color.parseColor("#222222"))
        }

        holder.itemView.setOnClickListener { showDialog(item.env) }
    }

    private fun showDialog(env: TestModel.EnvModel) {
        if (dialog == null) {
            val pre = "切换到"
            val suf = "需要杀掉进程重启"
            val msgBuilder = SpannableStringBuilder(pre + env.value + suf)
            msgBuilder.setSpan(ForegroundColorSpan(Color.parseColor("#e2001e")), pre.length,
                    pre.length + env.value.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            val builder = AlertDialog.Builder(context)
            builder.setMessage(msgBuilder.toString())
            builder.setPositiveButton("重启") { _, _ ->
                Build.env = env.key
                Services.app.restartApp(context)
                dialog?.dismiss()
            }
            builder.setNegativeButton("取消") { _, _ -> dialog?.dismiss() }
            dialog = builder.create()
        }
        dialog?.show()
    }
}
