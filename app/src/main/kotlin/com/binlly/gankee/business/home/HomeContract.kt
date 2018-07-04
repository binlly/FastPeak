package com.binlly.gankee.business.home

import com.binlly.gankee.base.mvp.BaseFragmentPresenter
import com.binlly.gankee.base.mvp.SimpleListView
import com.binlly.gankee.base.rx.RxObserver


interface HomeContract {
    interface View: SimpleListView<FeedAll> {

    }

    interface Presenter: BaseFragmentPresenter {
        fun requestFeedAll(page: Int, observer: RxObserver<List<FeedAll>?>)
        fun refresh()
        fun loadmore()
    }
}
