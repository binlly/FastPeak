package com.binlly.gankee.business.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.binlly.gankee.base.CacheFragmentStatePagerAdapter
import com.binlly.gankee.business.girl.GirlFragment
import com.binlly.gankee.business.mine.MineFragment

/**
 * Created by yy on 2017/11/15.
 */
class HomePagerAdapter(fm: FragmentManager): CacheFragmentStatePagerAdapter(fm) {

    private val tabs = arrayOf("首页", "妹子", "我的")
    private val fragments = arrayOf(HomeFragment(), GirlFragment(), MineFragment())

    override fun createItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = tabs[position]

    fun tabs(): Array<String> = tabs
}