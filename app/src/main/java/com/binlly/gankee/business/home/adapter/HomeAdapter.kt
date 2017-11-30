package com.binlly.gankee.business.home.adapter

import com.binlly.gankee.business.home.FeedAll
import com.fangxin.assessment.base.adapter.MultipleItemAdapter

/**
 * Created by yy on 2017/11/16.
 */

class HomeAdapter: MultipleItemAdapter<FeedAll>(null) {

    companion object {
        val TYPE_GIRL = 1
        val TYPE_OTHER = 2

        val ACTION_TO_WEB = 1
    }

    init {
        addItemViewDelegate(TYPE_GIRL, GirlDelegate())
        addItemViewDelegate(TYPE_OTHER, OtherDelegate(this))
    }
}