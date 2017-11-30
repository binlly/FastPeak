package com.binlly.gankee.business.test

import android.os.Bundle
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseActivity

/**
 * 工程模式页面
 * Created by wsl on 17/5/12.
 */

class TestActivity: BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_test
    }

    override fun initView(savedInstanceState: Bundle?) {
        val testFragment = TestFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.contentContainer, testFragment, "test_for_tool_mode")
        ft.commitAllowingStateLoss()
    }

    override fun isNeedToolbar(): Boolean {
        return true
    }

    override fun customTitle(): String {
        return getString(R.string.test_title)
    }
}
