package com.binlly.gankee.business.test.adapter

import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.binlly.gankee.R
import com.binlly.gankee.base.adapter.BaseDelegate
import com.binlly.gankee.business.test.TestFragment
import com.binlly.gankee.business.test.model.TestModel
import com.binlly.gankee.ext.ToastUtils
import com.binlly.gankee.repo.RemoteRepo
import com.binlly.gankee.service.Services
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by binlly on 2017/5/13.
 */

class MockDelegate(context: Context, val fragment: TestFragment): BaseDelegate<TestModel>(context) {

    override val layoutResId: Int
        get() = R.layout.test_item_key_editor

    override fun childConvert(holder: BaseViewHolder, item: TestModel) {
        val key = holder.getView<TextView>(R.id.key)
        val value = holder.getView<EditText>(R.id.value)
        val reset = holder.getView<Button>(R.id.button_reset)
        val apply = holder.getView<Button>(R.id.button_apply)
        val router = holder.getView<Button>(R.id.button_router)

        key.text = item.mock.key
        value.setText(item.mock.value)
        value.setTextColor(item.valueColor)

        reset.setOnClickListener {
            value.setText(item.mock.value)
        }

        apply.setOnClickListener {
            val host = value.text
            if (host.isNotEmpty() && (host.startsWith("https://") || host.startsWith("http://"))) {
                item.mock.value = host.toString()
                RemoteRepo.mockHost = host.toString()
                // RetrofitManager.reCreateMockRetrofit(host.toString())
                ToastUtils.showToast("设置成功，即将重启")
                Services.postDelayed(2000) {
                    Services.app.restartApp(context)
                }
            } else ToastUtils.showToast("地址不合法，请重新设置")
        }

        router.setOnClickListener {
            Services.remoteConfig().pullMockConfig {
                fragment.refresh()
            }
        }
    }
}