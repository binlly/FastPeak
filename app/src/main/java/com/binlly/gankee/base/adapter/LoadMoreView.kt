package com.fangxin.assessment.base.adapter


import com.binlly.gankee.R
import com.chad.library.adapter.base.loadmore.LoadMoreView

/**
 * Created by binlly on 2017/5/17.
 */

class LoadMoreView: LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.load_more_view
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }
}