package com.binlly.gankee.business.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.binlly.gankee.base.mvp.BaseFragment
import com.binlly.gankee.business.girl.GirlFragment

/**
 * Created by yy on 2017/11/15.
 */
class HomePagerAdapter(fm: FragmentManager):
        com.binlly.gankee.base.CacheFragmentStatePagerAdapter(fm) {

    private val tabs = arrayOf("首页", "妹子")
    private val fragments = arrayOf<BaseFragment>(HomeFragment(), GirlFragment())

    override fun createItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = tabs[position]

    fun tabs(): Array<String> = tabs
}