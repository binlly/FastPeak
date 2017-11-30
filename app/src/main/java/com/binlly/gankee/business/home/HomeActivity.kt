package com.binlly.gankee.business.home

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.binlly.gankee.R
import com.binlly.gankee.base.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by yy on 2017/11/15.
 */
class HomeActivity: BaseActivity() {

    override fun getContentView(): Int = R.layout.activity_home

    override fun initView(savedInstanceState: Bundle?) {

        tab_layout.removeAllTabs()
        val adapter = HomePagerAdapter(supportFragmentManager)
        for (tab in adapter.tabs()) {
            val newTab = tab_layout.newTab()
            newTab.text = tab
            tab_layout.addTab(newTab)
        }
        tab_layout.addOnTabSelectedListener(object:
                TabLayout.TabLayoutOnPageChangeListener(tab_layout),
                TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                d("onTabReselected ${tab.text.toString()}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                d("onTabUnselected ${tab.text.toString()}")
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                d("onTabSelected ${tab.text.toString()}")
                pager.setCurrentItem(tab.position, false)
            }

        })

        pager.adapter = adapter
        pager.offscreenPageLimit = 2
        pager.currentItem = 0
    }

    override fun isNeedToolbar(): Boolean = false

    override fun customTitle(): String = "首页"
}